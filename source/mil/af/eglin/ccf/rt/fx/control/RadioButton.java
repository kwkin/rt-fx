package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtRadioButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A selection control allowing the user to select options.
 * <p>
 * A radio button is typically skinned as a circle with a dot in the center when
 * selected.
 */
public class RadioButton extends javafx.scene.control.RadioButton implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "radio-button.css";
    private static final String CSS_CLASS = "rt-radio-button";

    /**
     * The selected color specifies the color used by the border and fill when
     * in the selected state.
     */
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, RadioButton.this, "selectedColor",
            DefaultPalette.getInstance().getAccentColor());

    /**
     * The unselected color specifies the color used by the border when in the
     * unselected state.
     */
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, RadioButton.this, "unselectedColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base component color
     * to be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, RadioButton.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, RadioButton.this, "disableAnimation", false);

    /**
     * Creates a radio button with an empty string for its label.
     */
    public RadioButton()
    {
        super();
        initialize();
    }

    /**
     * Creates a radio button with the accent.
     * 
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public RadioButton(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a radio button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public RadioButton(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a radio button with the specified text and accent.
     * 
     * @param text A text string for its label.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public RadioButton(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public StyleableObjectProperty<Paint> selectedColorProperty()
    {
        return this.selectedColor;
    }

    public Paint getSelectedColor()
    {
        return selectedColor.get();
    }

    public void setSelectedColor(Paint color)
    {
        this.selectedColor.set(color);
    }

    public StyleableObjectProperty<Paint> unselectedColorProperty()
    {
        return this.unselectedColor;
    }

    public Paint getUnselectedColor()
    {
        return unselectedColor.get();
    }

    public void setUnselectedColor(Paint color)
    {
        this.unselectedColor.set(color);
    }

    public ObjectProperty<Paint> getOverlayColorProperty()
    {
        return this.overlayColor;
    }

    public Paint getOverlayColor()
    {
        return this.overlayColor.get();
    }

    public void setOverlayColor(Paint overlayColor)
    {
        this.overlayColor.set(overlayColor);
    }

    public BooleanProperty isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.get();
    }

    public void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.set(isAnimationDisabled);
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
    public String getUserAgentStylesheet()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtRadioButtonSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<RadioButton, Paint> SELECTED_COLOR = new CssMetaData<RadioButton, Paint>(
                "-rt-selected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.selectedColor == null || !control.selectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(RadioButton control)
            {
                return control.selectedColorProperty();
            }
        };
        private static final CssMetaData<RadioButton, Paint> UNSELECTED_COLOR = new CssMetaData<RadioButton, Paint>(
                "-rt-unselected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.unselectedColor == null || !control.unselectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(RadioButton control)
            {
                return control.unselectedColorProperty();
            }
        };
        private static final CssMetaData<RadioButton, Paint> OVERLAY_COLOR = new CssMetaData<RadioButton, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(RadioButton control)
            {
                return control.overlayColor;
            }
        };
        private static final CssMetaData<RadioButton, Boolean> DISABLE_ANIMATION = new CssMetaData<RadioButton, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(RadioButton control)
            {
                return control.isAnimationDisabled;
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.CheckBox.getClassCssMetaData());
            styleables.add(SELECTED_COLOR);
            styleables.add(UNSELECTED_COLOR);
            styleables.add(DISABLE_ANIMATION);
            styleables.add(OVERLAY_COLOR);
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
    
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    static
    {
        RadioButton.loadStyleSheet();
    }
}
