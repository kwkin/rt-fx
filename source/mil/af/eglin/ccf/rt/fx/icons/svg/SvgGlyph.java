package mil.af.eglin.ccf.rt.fx.icons.svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.SizeConverter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO Add a maintain aspect ratio flag
public class SvgGlyph extends StackPane
{
    private static final String USER_AGENT_STYLESHEET = "svg-icon.css";
    private static final String CSS_CLASS = "rt-svg-icon";

    private DoubleProperty widthHeightRatio = new SimpleDoubleProperty(1);
    private ObjectProperty<Paint> fill = new SimpleObjectProperty<Paint>();
    private StyleableDoubleProperty size = new SimpleStyleableDoubleProperty(
            StyleableProperties.SIZE, this, "size", (double)IconSize.SIZE_32.getIconSize());
    
    public SvgGlyph(SvgFile icon)
    {
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgGlyph(SvgFile icon, IconSize size)
    {
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgGlyph(SvgFile icon, Paint fill)
    {
        this.fill.setValue(fill);
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgGlyph(SvgFile icon, Paint fill, IconSize size)
    {
        this.fill.setValue(fill);
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.getIconInputStream()));
    }


    public SvgGlyph(String svgPath)
    {

        initialize(svgPath);
    }

    public double getSize()
    {
        return size.get();
    }

    public Paint getFill()
    {
        return this.fill.getValue();
    }

    public void setFill(Paint fill)
    {
        this.fill.setValue(fill);
    }
    
    public ObjectProperty<Paint> fillProperty()
    {
        return this.fill;
    }

    public double getWidthHeightRatio()
    {
        return this.widthHeightRatio.getValue();
    }

    public void setWidthHeightRatio(double widthHeightRatio)
    {
        this.widthHeightRatio.setValue(widthHeightRatio);
    }

    public DoubleProperty widthHeightRatioProperty()
    {
        return this.widthHeightRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
    }

    private void initialize(String svgPath)
    {
        getStyleClass().add(CSS_CLASS);

        // TODO add binding and property conversions for background
        if (getFill() != null)
        {
            setBackground(new Background(new BackgroundFill(getFill(), null, null)));
        }
        this.fill.addListener((ov, oldVal, newVal) -> 
        {
            setBackground(new Background(new BackgroundFill(newVal, null, null)));
        });
        backgroundProperty().addListener((ov, oldVal, newVal) -> 
        {
            if (this.fill.getValue() != null)
            {
                setBackground(new Background(new BackgroundFill(getFill(), null, null)));
            }
        });
        shapeProperty().addListener((ov, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                this.widthHeightRatio.setValue(newVal.prefWidth(-1) / newVal.prefHeight(-1));
                if (getSize() != Region.USE_COMPUTED_SIZE)
                {
                    setSizeRatio(getSize());
                }
            }
        });

        if (svgPath != null && !svgPath.isEmpty()) 
        {
            SVGPath shape = new SVGPath();
            shape.setContent(svgPath);
            setShape(shape);
        }
    }

    private void setSizeRatio(double size)
    {
        double width = widthHeightRatio.getValue() * size;
        double height = size / widthHeightRatio.getValue();
        if (width <= size)
        {
            setSize(width, size);
        } 
        else if (height <= size)
        {
            setSize(size, height);
        } 
        else
        {
            setSize(size, size);
        }
    }

    public void setSize(double size) 
    {
        this.size.setValue(size);
    }

    public void setSize(double width, double height) 
    {
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private static String extractSvgPath(InputStream inputStream)
    {
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        String svgPath = builder.toString().replaceFirst(".*d=\"", "").replaceFirst("\".*", "");
        return svgPath;
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<SvgGlyph, Number> SIZE = new CssMetaData<SvgGlyph, Number>("-rt-size",
                SizeConverter.getInstance(), Region.USE_COMPUTED_SIZE)
        {
            @Override
            public boolean isSettable(SvgGlyph control)
            {
                return control.size == null || !control.size.isBound();
            }

            @Override
            public StyleableDoubleProperty getStyleableProperty(SvgGlyph control)
            {
                return control.size;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Pane.getClassCssMetaData());
            Collections.addAll(styleables, SIZE);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}