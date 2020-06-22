package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtColorPickerIconSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ColorPickerIcon extends javafx.scene.control.ColorPicker implements RtStyleableComponent
{

    protected ColorPickerStyle style = ColorPickerStyle.ICON;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "color-picker.css";
    private static final String CSS_CLASS = "rt-color-picker";

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, ColorPickerIcon.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, ColorPickerIcon.this, "disableAnimation", false);

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

    // TODO add feature for changing icon and its size
    public ColorPickerIcon()
    {
        super();
        initialize();
    }

    public ColorPickerIcon(Color color)
    {
        super(color);
        initialize();
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

    private static class StyleableProperties
    {
        private static final CssMetaData<ColorPickerIcon, Paint> OVERLAY_COLOR = new CssMetaData<ColorPickerIcon, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(ColorPickerIcon control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ColorPickerIcon control)
            {
                return control.overlayColor;
            }
        };

        private static final CssMetaData<ColorPickerIcon, Boolean> DISABLE_ANIMATION = new CssMetaData<ColorPickerIcon, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ColorPickerIcon control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(ColorPickerIcon control)
            {
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ColorPicker.getClassCssMetaData());
            styleables.add(OVERLAY_COLOR);
            styleables.add(DISABLE_ANIMATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
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
