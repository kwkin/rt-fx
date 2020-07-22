package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtColorPickerIconSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A color picker allows the user to select a color from a standard color
 * palette or specify a custom color.
 */
public class ColorPickerIcon extends javafx.scene.control.ColorPicker implements RtStyleableComponent
{
    protected ColorPickerStyle style = ColorPickerStyle.ICON;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "color-picker.css";
    private static final String CSS_CLASS = "rt-color-picker";

    private SvgIcon icon = new SvgIcon(SvgFile.EYEDROPPER_VARIANT);

    private static final StyleablePropertyFactory<ColorPickerIcon> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.ColorPicker.getClassCssMetaData());
    
    private static final CssMetaData<ColorPickerIcon, Paint> OVERLAY_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<ColorPickerIcon, Boolean> DISABLE_ANIMATION = 
            FACTORY.createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * The overlay color specifies the background color used when hovering and
     * arming.
     * <p>
     * The color is added on top of the component to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private final SimpleStyleableObjectProperty<Paint> overlayColor = 
            new SimpleStyleableObjectProperty<>(OVERLAY_COLOR, this, "overlayColor");
    
    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private final SimpleStyleableBooleanProperty isAnimationDisabled = 
            new SimpleStyleableBooleanProperty(DISABLE_ANIMATION, this, "isAnimationDisabled");

    /**
     * Indicates if the label showing the name or hex value of the current color
     * is displayed.
     */
    private BooleanProperty isLabelVisible = new SimpleBooleanProperty()
    {
        @Override
        protected void invalidated()
        {
            String style = String.format("-fx-color-label-visible:%s", get());
            setStyle(style);
        }
    };

    /**
     * Creates a {@code ColorPickerIcon} initialized with a white value
     */
    public ColorPickerIcon()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code ColorPickerIcon} initialized with the provided color
     * 
     * @param color the initial color
     */
    public ColorPickerIcon(Color color)
    {
        super(color);
        initialize();
    }
    
    /**
     * Gets the icon used to represent the color picker value
     * 
     * @return the icon used to represent the color picker value
     */
    public final SvgIcon getIcon()
    {
        return this.icon;
    }

    /**
     * Sets the icon used to represent the color picker value
     * 
     * @param icon the icon used to represent the color picker value
     */
    public final void setIcon(SvgIcon icon)
    {
        this.icon = icon;
    }

    public final ObjectProperty<Paint> overlayColorProperty()
    {
        return this.overlayColor;
    }

    public final Paint getOverlayColor()
    {
        return overlayColor.get();
    }

    public final void setOverlayColor(Paint overlayColor)
    {
        this.overlayColor.set(overlayColor);
    }

    public final StyleableBooleanProperty disableAnimationProperty()
    {
        return this.isAnimationDisabled;
    }

    public final Boolean isDisableAnimation()
    {
        return this.isAnimationDisabled.get();
    }

    public final void setDisableAnimation(Boolean disabled)
    {
        this.isAnimationDisabled.set(disabled);
    }

    public BooleanProperty isLabelVisibleProperty()
    {
        return this.isLabelVisible;
    }

    public void setLabelVisiblity(boolean isLabelVisible)
    {
        this.isLabelVisible.set(isLabelVisible);
    }

    public boolean isLabelVisible()
    {
        return this.isLabelVisible.get();
    }

    public ColorPickerStyle getButtonStyle()
    {
        return this.style;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtColorPickerIconSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() 
     {
        return FACTORY.getCssMetaData();
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() 
    {
        return FACTORY.getCssMetaData();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        for (ColorPickerStyle buttonStyle : ColorPickerStyle.values())
        {
            pseudoClassStateChanged(buttonStyle.getPseudoClass(), buttonStyle == this.style);
        }
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    static
    {
        ColorPicker.loadStyleSheet();
    }
}
