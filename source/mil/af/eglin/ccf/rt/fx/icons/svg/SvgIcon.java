package mil.af.eglin.ccf.rt.fx.icons.svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import mil.af.eglin.ccf.rt.fx.control.RtIcon;
import mil.af.eglin.ccf.rt.fx.control.skins.Utils;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO Add a maintain aspect ratio flag
public class SvgIcon extends StackPane implements RtIcon
{
    private static final String USER_AGENT_STYLESHEET = "svg-icon.css";
    private static final String CSS_CLASS = "rt-svg-icon";

    private final StackPane iconBox = new StackPane();
    private final StackPane icon = new StackPane();

    private boolean isFillManaged = true;

    private DoubleProperty widthHeightRatio = new SimpleDoubleProperty(1);
    private ObjectProperty<Paint> fill = new SimpleObjectProperty<Paint>()
    {
        @Override
        protected void invalidated()
        {
            Paint fill = icon.getBackground() == null ? null : icon.getBackground().getFills().get(0).getFill();
            if (get() != null && !get().equals(fill))
            {
                Utils.setBackgroundColor(icon, get());
            }
        }
    };
    private StyleableDoubleProperty size = new SimpleStyleableDoubleProperty(StyleableProperties.SIZE, this, "size",
            (double) IconSize.SIZE_32.getIconSize());

    public SvgIcon(URL icon) throws IOException
    {
        initialize(extractSvgPath(icon.openStream()));
    }

    public SvgIcon(URL icon, IconSize size) throws IOException
    {
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.openStream()));
    }

    public SvgIcon(URL icon, Paint fill) throws IOException
    {
        this.fill.setValue(fill);
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.openStream()));
    }

    public SvgIcon(URL icon, Paint fill, IconSize size) throws IOException
    {
        this.fill.setValue(fill);
        this.size.setValue(size.getIconSize());
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.openStream()));
    }

    public SvgIcon(SvgFile icon)
    {
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgIcon(SvgFile icon, IconSize size)
    {
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgIcon(SvgFile icon, Paint fill)
    {
        this.fill.setValue(fill);
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgIcon(SvgFile icon, Paint fill, IconSize size)
    {
        this.fill.setValue(fill);
        this.size.setValue(size.getIconSize());
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    public SvgIcon(String svgPath)
    {

        initialize(svgPath);
    }

    @Override
    public Node getNode()
    {
        return this;
    }

    @Override
    public double getSize()
    {
        return size.get();
    }

    @Override
    public Paint getFill()
    {
        return this.fill.getValue();
    }

    @Override
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

    public boolean isColorManaged()
    {
        return this.isFillManaged;
    }

    public void setIsColorManaged(boolean isFillManaged)
    {
        this.isFillManaged = isFillManaged;
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
        this.icon.getStyleClass().setAll("icon");

        Paint fill = this.icon.getBackground() == null ? null
                : this.icon.getBackground().getFills().get(0).getFill();
        if (this.fill.get() != null && !this.fill.get().equals(fill))
        {
            Utils.setBackgroundColor(this.icon, this.fill.get());
        }
        this.icon.backgroundProperty().addListener((ov, oldVal, newVal) -> 
        {
            if (this.fill.getValue() != null)
            {
                this.icon.setBackground(new Background(new BackgroundFill(getFill(), null, null)));
            }
        });

        this.icon.shapeProperty().addListener((ov, oldVal, newVal) ->
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

        this.iconBox.getChildren().add(this.icon);
        getChildren().add(this.iconBox);
        if (svgPath != null && !svgPath.isEmpty())
        {
            SVGPath shape = new SVGPath();
            shape.setContent(svgPath);
            this.widthHeightRatio.setValue(shape.prefWidth(-1) / shape.prefHeight(-1));
            this.icon.setShape(shape);
            setSizeRatio(getSize());
        }
    }

    private void setSizeRatio(double size)
    {
        double width = this.widthHeightRatio.getValue() * size;
        double height = size / this.widthHeightRatio.getValue();
        if (width <= size)
        {
            setIconSize(width, size);
        }
        else if (height <= size)
        {
            setIconSize(size, height);
        }
        else
        {
            setIconSize(size, size);
        }
    }

    public void setIconSize(double size)
    {
        this.size.setValue(size);
    }

    public void setIconSize(double width, double height)
    {
        this.icon.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.icon.setPrefSize(width, height);
        this.icon.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        
        double size = Math.max(width, height);
        this.iconBox.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconBox.setPrefSize(size, size);
        this.iconBox.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private static String extractSvgPath(InputStream inputStream)
    {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
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
        private static final CssMetaData<SvgIcon, Number> SIZE = new CssMetaData<SvgIcon, Number>("-rt-size",
                SizeConverter.getInstance(), Region.USE_COMPUTED_SIZE)
        {
            @Override
            public boolean isSettable(SvgIcon control)
            {
                return control.size == null || !control.size.isBound();
            }

            @Override
            public StyleableDoubleProperty getStyleableProperty(SvgIcon control)
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