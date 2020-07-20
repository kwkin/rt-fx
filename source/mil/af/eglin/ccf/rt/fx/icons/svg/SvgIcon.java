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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import mil.af.eglin.ccf.rt.fx.control.Icon;
import mil.af.eglin.ccf.rt.fx.control.skins.Utils;
import mil.af.eglin.ccf.rt.fx.icons.IconScale;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * 
 */
public class SvgIcon extends StackPane implements Icon
{
    private static final String USER_AGENT_STYLESHEET = "svg-icon.css";
    private static final String CSS_CLASS = "rt-svg-icon";

    private final StackPane iconBox = new StackPane();
    private final StackPane icon = new StackPane();

    private double originalWidth = 0;
    private double originalHeight = 0;
    private boolean isFillManaged = true;

    /**
     * The icon scale determines the size of the icon.
     * <p>
     * Scaling options are provided for automatically resizing and preserving
     * aspect ratio based upon the parent's dimensions. Additionally, an option
     * is provided to manually set the icon size.
     */
    private ObjectProperty<IconScale> iconScale = new SimpleObjectProperty<IconScale>(IconScale.NONE)
    {
        @Override
        protected void invalidated()
        {
            applyScale();
        }
    };
    
    /**
     * The width height ratio represents width/height of the original icon.
     */
    private DoubleProperty widthHeightRatio = new SimpleDoubleProperty(1);
    
    /**
     * The fill sets the color of the icon.
     */
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
    
    /**
     * Size is used to set a uniform height and width to an icon with {@code IconScale.NONE}
     */
    private StyleableDoubleProperty size = new SimpleStyleableDoubleProperty(StyleableProperties.SIZE, this, "size",
            (double) IconSize.SIZE_32.getIconSize());

    /**
     * Creates an icon using an SVG file.
     * 
     * @param icon the {@code URL} containing the SVG data
     * @throws IOException if there was an error reading the file
     */
    public SvgIcon(URL icon) throws IOException
    {
        initialize(extractSvgPath(icon.openStream()));
    }

    /**
     * Creates an icon with the specified SVG file and size.
     * 
     * @param icon the {@code URL} containing the SVG data
     * @param size the width and height of the icon
     * @throws IOException if there was an error reading the file
     */
    public SvgIcon(URL icon, IconSize size) throws IOException
    {
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.openStream()));
    }

    /**
     * Creates an icon with the specified SVG file and color.
     * 
     * @param icon the {@code URL} containing the SVG data
     * @param fill the color of the icon
     * @throws IOException if there was an error reading the file
     */
    public SvgIcon(URL icon, Paint fill) throws IOException
    {
        this.fill.setValue(fill);
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.openStream()));
    }

    /**
     * Creates an icon with the specified SVG file, color, and size.
     * 
     * @param icon the {@code URL} containing the SVG data
     * @param fill the color of the icon
     * @param size the width and height of the icon
     * @throws IOException if there was an error reading the file
     */
    public SvgIcon(URL icon, Paint fill, IconSize size) throws IOException
    {
        this.fill.setValue(fill);
        this.size.setValue(size.getIconSize());
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.openStream()));
    }

    /**
     * Creates an icon with the standard rt-fx icon.
     * 
     * @param icon the standard rt-fx icon
     */
    public SvgIcon(SvgFile icon)
    {
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    /**
     * Creates an icon with the standard rt-fx icon and size.
     * 
     * @param icon the standard rt-fx icon
     * @param size the width and height of the icon
     */
    public SvgIcon(SvgFile icon, IconSize size)
    {
        this.size.setValue(size.getIconSize());
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    /**
     * Creates an icon with the standard rt-fx icon and fill.
     * 
     * @param icon the standard rt-fx icon
     * @param fill the color of the icon
     * @param size the width and height of the icon
     */
    public SvgIcon(SvgFile icon, Paint fill)
    {
        this.fill.setValue(fill);
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    /**
     * Creates an icon with the standard rt-fx icon, fill, and size.
     * 
     * @param icon the standard rt-fx icon
     * @param fill the color of the icon
     * @param size the width and height of the icon
     */
    public SvgIcon(SvgFile icon, Paint fill, IconSize size)
    {
        this.fill.setValue(fill);
        this.size.setValue(size.getIconSize());
        setIsColorManaged(false);
        initialize(extractSvgPath(icon.getIconInputStream()));
    }

    /**
     * Creates an icon with the specified svg path.
     * 
     * @param svgPath the svg path
     */
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

    public ObjectProperty<IconScale> iconScaleProperty()
    {
        return this.iconScale;
    }

    public IconScale getIconScale()
    {
        return this.iconScale.get();
    }

    public void setIconScale(IconScale scale)
    {
        this.iconScale.set(scale);
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

        Paint fill = this.icon.getBackground() == null ? null : this.icon.getBackground().getFills().get(0).getFill();
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
                this.originalWidth = newVal.prefWidth(-1);
                this.originalHeight = newVal.prefHeight(-1);
                applyScale();
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

        parentProperty().addListener((ov, oldVal, newVal) ->
        {
            applyScale();
        });
    }

    private void applyScale()
    {
        Region region = (Region) getParent();
        double parentWidth = 0;
        double parentHeight = 0;
        if (region != null)
        {
            parentWidth = region.getPrefWidth() - region.snappedLeftInset() - region.snappedRightInset();
            parentHeight = region.getPrefHeight() - region.snappedBottomInset() - region.snappedTopInset();
        }
        switch (this.iconScale.getValue())
        {
            case FILL:
                setIconSize(parentWidth, parentHeight);
                break;
            case NONE:
                this.widthHeightRatio.setValue(this.originalWidth / this.originalHeight);
                if (getSize() != Region.USE_COMPUTED_SIZE)
                {
                    setSizeRatio(getSize());
                }
                break;
            case UNIFORM_FILL:
                this.widthHeightRatio.setValue(this.originalWidth / this.originalHeight);
                double smallerSize = Math.min(parentWidth, parentHeight);
                if (getSize() != Region.USE_COMPUTED_SIZE)
                {
                    setSizeRatio(smallerSize);
                }
                break;
            default:
                break;
        }
    }

    private void setSizeRatio(double size)
    {
        double width = this.widthHeightRatio.getValue() * size;
        double height = size / this.widthHeightRatio.getValue();
        if (width <= size)
        {
            setIconSizeUniform(width, size);
        }
        else if (height <= size)
        {
            setIconSizeUniform(size, height);
        }
        else
        {
            setIconSizeUniform(size, size);
        }
    }

    public void setIconSize(double size)
    {
        this.size.setValue(size);
    }

    public void setIconSizeUniform(double width, double height)
    {
        this.icon.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.icon.setPrefSize(width, height);
        this.icon.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        double size = Math.max(width, height);
        this.iconBox.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconBox.setPrefSize(size, size);
        this.iconBox.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    public void setIconSize(double width, double height)
    {
        this.icon.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.icon.setPrefSize(width, height);
        this.icon.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        this.iconBox.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconBox.setPrefSize(width, height);
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