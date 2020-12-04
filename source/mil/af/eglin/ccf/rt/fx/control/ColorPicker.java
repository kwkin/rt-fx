package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtColorPickerSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.fx.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.validation.Validator;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A color picker allows the user to select a color from a standard color
 * palette or specify a custom color.
 */
public class ColorPicker extends javafx.scene.control.ColorPicker
        implements RtStyleableComponent, LabelFloatControl, DescriptionControl, ValidableControl<Color>
{
    /**
     * Creates a {@code ColorPicker} initialized with a white value
     */
    public ColorPicker()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code ColorPicker} initialized with the provided color
     * 
     * @param color the initial color
     */
    public ColorPicker(Color color)
    {
        super(color);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtColorPickerSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());

        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.isLabelFloating.get());
        pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, this.isHelperTextVisible.get() || getValidators().size() > 0);
        this.isLabelFloating.addListener((ov, oldVal, newVal) ->
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, newVal);
        });
        this.isHelperTextVisible.addListener((ov, oldVal, newVal) ->
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, newVal);
        });
        this.validationHandler.getValidators().addListener(new ListChangeListener<Validator<Color>>()
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Validator<Color>> c)
            {
                pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, isHelperTextVisible());
            }
        });
        
        for (ColorPickerStyle buttonStyle : ColorPickerStyle.values())
        {
            pseudoClassStateChanged(buttonStyle.getPseudoClass(), buttonStyle == this.style);
        }
    }

    /*************************************************************************
     *                                                                       *
     * Validation                                                            *
     *                                                                       *
     ************************************************************************/

    private ValidableHandler<Color> validationHandler = new ValidableHandler<>(this);

    /**
     * Indicates if the current value is valid according to the validator
     * conditions.
     */
    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final ReadOnlyBooleanProperty isValidProperty()
    {
        return this.isValid;
    }

    @Override
    public void setValid(boolean isValid)
    {
        this.isValid.set(isValid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isValid()
    {
        return this.isValid.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean validate()
    {
        this.isValid.set(this.validationHandler.validate(getValue()));
        return isValid();
    }

    /**
     * Gets the list of validators used to check the current value 
     * 
     * @return the list of validators used to check the current value 
     */
    public ObservableList<Validator<Color>> getValidators()
    {
        return this.validationHandler.getValidators();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValidateCondition(ValidateCondition validateCondition)
    {
        this.validationHandler.setValidateCondition(validateCondition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidateCondition getValidateCondition()
    {
        return this.validationHandler.getValidateCondition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableValue<Color> getValidableValue()
    {
        return valueProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control getValidableControl()
    {
        return this;
    }
    
    /*************************************************************************
     *                                                                       *
     * Properties                                                            *
     *                                                                       *
     ************************************************************************/

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

    /**
     * Indicates if the helper text should be shown.
     * <p>
     * Helper text is typically a short description conveying additional
     * guidance about the input field. The helper text appears below the input.
     */
    private BooleanProperty isHelperTextVisible = new SimpleBooleanProperty()
    {
        @Override
        protected void invalidated()
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, get());
        }
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final BooleanProperty helperTextVisibleProperty()
    {
        return this.isHelperTextVisible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean getHelperTextVisible()
    {
        return this.isHelperTextVisible.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setHelperTextVisible(boolean isHelperTextVisible)
    {
        this.isHelperTextVisible.set(isHelperTextVisible);
    }

    public final boolean isHelperTextVisible()
    {
        return isHelperTextVisible.get() || getValidators().size() > 0;
    }


    /**
     * The text to use for the helper description located below the input field.
     */
    private StringProperty helperText = new SimpleStringProperty();

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringProperty helperTextProperty()
    {
        return this.helperText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getHelperText()
    {
        return this.helperText.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setHelperText(String helperText)
    {
        this.helperText.set(helperText);
    }

    /**
     * The text to use for the error description located below the input field.
     * The error description will override the helper text when the component is
     * invalid. When valid, the helper text will be visible.
     */
    private StringProperty errorText = new SimpleStringProperty();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final StringProperty errorMessageProperty()
    {
        return this.errorText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setErrorMessage(String message)
    {
        this.errorText.set(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getErrorMessage()
    {
        return this.errorText.get();
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<ColorPicker> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ColorPicker.getClassCssMetaData());

    private static final CssMetaData<ColorPicker, Boolean> LABEL_FLOAT = FACTORY
            .createBooleanCssMetaData("-rt-label-float", s -> s.isLabelFloating, false, false);

    /**
     * When enabled, the prompt text will be positioned above the input text.
     * When disabled, the prompt text will disappear when the input text is
     * entered.
     */
    private StyleableBooleanProperty isLabelFloating = new SimpleStyleableBooleanProperty(LABEL_FLOAT, this,
            "labelFloat")
    {
        @Override
        protected void invalidated()
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, get());
        }
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final StyleableBooleanProperty labelFloatProperty()
    {
        return this.isLabelFloating;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLabelFloat()
    {
        return isLabelFloating.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setLabelFloat(final boolean labelFloat)
    {
        isLabelFloating.set(labelFloat);
    }

    private static final CssMetaData<ColorPicker, Paint> UNFOCUS_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-unfocus-color", s -> s.unfocusColor, DefaultPalette.getInstance().getBaseColor(), false);
    /**
     * The unfocus color specifies the accent colors used when the component is
     * unfocused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(UNFOCUS_COLOR, this,
            "unfocusColor");

    /**
     * {@inheritDoc}
     */
    @Override
    public final StyleableObjectProperty<Paint> unfocusColorProperty()
    {
        return this.unfocusColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Paint getUnfocusColor()
    {
        return unfocusColor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setUnfocusColor(Paint color)
    {
        this.unfocusColor.set(color);
    }
    
    private static final CssMetaData<ColorPicker, Paint> FOCUS_COLOR = FACTORY.createPaintCssMetaData("-rt-focus-color",
            s -> s.focusColor, DefaultPalette.getInstance().getAccentColor(), false);
    
    /**
     * The focus color specifies the accent colors used when the component is
     * focused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(FOCUS_COLOR, this,
            "focusColor");

    /**
     * {@inheritDoc}
     */
    @Override
    public final StyleableObjectProperty<Paint> focusColorProperty()
    {
        return this.focusColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Paint getFocusColor()
    {
        return this.focusColor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setFocusColor(Paint color)
    {
        this.focusColor.set(color);
    }

    private static final CssMetaData<ColorPicker, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    
    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(OVERLAY_COLOR, this,
            "overlayColor");

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

    private static final CssMetaData<ColorPicker, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);
    
    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(DISABLE_ANIMATION, this,
            "disableAnimation");

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

    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/

    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");
    
    private static final String USER_AGENT_STYLESHEET = "color-picker.css";
    private static final String CSS_CLASS = "rt-color-picker";

    protected ColorPickerStyle style = ColorPickerStyle.COMBO_BOX;
    protected Accent accent = Accent.PRIMARY_MID;

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadComponent(USER_AGENT_STYLESHEET));
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
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    public ColorPickerStyle getButtonStyle()
    {
        return this.style;
    }

    static
    {
        ColorPicker.loadStyleSheet();
    }
}
