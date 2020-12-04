package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtTextFieldSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.fx.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.validation.Validator;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * Text fields allow users to enter and edit text.
 * <p>
 * A text field is typically skinned as a rounded box with a bottom border
 * <p>
 * A real-time text field can be several pseudostates in addition to the JavaFX
 * text field pseudo states. Specifically, this class defines
 * {@link TextField#islabelFloating floating}, {@link TextField#isShowHelperText
 * helper}, and {@link TextField#isValid error} pseudo states.
 */
public class TextField extends javafx.scene.control.TextField
        implements RtStyleableComponent, LabelFloatControl, DescriptionControl, ValidableControl<String>
{
    /**
     * Create a {@code TextField} with an empty text input
     */
    public TextField()
    {
        super();
        initialize();
    }

    /**
     * Create a {@code TextField} with initial text content
     * 
     * @param text the initial text value
     */
    public TextField(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Create a {@code TextField} with the specified accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public TextField(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Create a {@code TextField} with initial text content and accent
     * 
     * @param text the initial text value
     * @param accent the accent used to change the component's color scheme
     */
    public TextField(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtTextFieldSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());

        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.isLabelFloating.get());
        pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, isHelperTextVisible());
        this.validationHandler.getValidators().addListener(new ListChangeListener<Validator<String>>()
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Validator<String>> c)
            {
                pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, isHelperTextVisible());
            }
        });
    }

    /*************************************************************************
     *                                                                       *
     * Validation                                                            *
     *                                                                       *
     ************************************************************************/

    private ValidableHandler<String> validationHandler = new ValidableHandler<>(this);

    /**
     * Indicates if the current value is valid according to the validator
     * conditions.
     */
    private BooleanProperty isValid = new SimpleBooleanProperty(true);

    public ObservableList<Validator<String>> getValidators()
    {
        return this.validationHandler.getValidators();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate()
    {
        this.isValid.set(this.validationHandler.validate(getText()));
        return isValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyBooleanProperty isValidProperty()
    {
        return this.isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid()
    {
        return this.isValid.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValid(boolean isValid)
    {
        this.isValid.set(isValid);
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
    public ObservableValue<String> getValidableValue()
    {
        return textProperty();
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
     * Indicates if the helper text should be shown.
     * <p>
     * Helper text is typically a short description conveying additional
     * guidance about the input field. The helper text appears below the input.
     */
    private BooleanProperty isShowHelperText = new SimpleBooleanProperty()
    {
        @Override
        protected void invalidated()
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, get() || getValidators().size() > 0);
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty helperTextVisibleProperty()
    {
        return this.isShowHelperText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getHelperTextVisible()
    {
        return this.isShowHelperText.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHelperTextVisible(boolean isShowHelperText)
    {
        this.isShowHelperText.set(isShowHelperText);
    }

    public boolean isHelperTextVisible()
    {
        return isShowHelperText.get() || getValidators().size() > 0;
    }

    /**
     * The text to use for the helper description located below the input field.
     */
    private StringProperty helperText = new SimpleStringProperty();

    public StringProperty helperTextProperty()
    {
        return this.helperText;
    }

    public String getHelperText()
    {
        return this.helperText.get();
    }

    public void setHelperText(String helperText)
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
    public StringProperty errorMessageProperty()
    {
        return this.errorText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String message)
    {
        this.errorText.set(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage()
    {
        return this.errorText.get();
    }

    /**
     * Trailing text adds a label to the end of the text field.
     * <p>
     * Trailing text will override a trailing icon if both are set. The trailing
     * text is considered to be set if it is not empty or null.
     */
    private ObjectProperty<String> trailingText = new SimpleObjectProperty<String>();

    public ObjectProperty<String> trailingTextProperty()
    {
        return this.trailingText;
    }

    public String getTrailingText()
    {
        return this.trailingText.get();
    }

    public void setTrailingText(String text)
    {
        this.trailingText.set(text);
    }

    /**
     * A trailing icon adds an icon to the end of the text field. Input text
     * will be clipped before overlapping with the trailing icon.
     * <p>
     * Trailing text will override a trailing icon if both are set.
     */
    private ObjectProperty<Icon> trailingIcon = new SimpleObjectProperty<Icon>();

    public ObjectProperty<Icon> trailingIconProperty()
    {
        return this.trailingIcon;
    }

    public Icon getTrailingIcon()
    {
        return this.trailingIcon.get();
    }

    public void setTrailingIcon(Icon icon)
    {
        this.trailingIcon.set(icon);
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<TextField> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.TextField.getClassCssMetaData());

    private static final CssMetaData<TextField, Boolean> TRAILING_VISIBILITY = 
            FACTORY.createBooleanCssMetaData("-rt-label-float", s -> s.isTrailingVisible, false, false);
    
    /**
     * Toggles the visibility of the trailing text or icon.
     * <p>
     * Trailing text will override a trailing icon if both are set.
     */
    private StyleableBooleanProperty isTrailingVisible = new SimpleStyleableBooleanProperty(
            TRAILING_VISIBILITY, TextField.this, "trailingVisible", true);
    
    public StyleableBooleanProperty isTrailingVisibleProperty()
    {
        return this.isTrailingVisible;
    }

    public boolean isTrailingVisible()
    {
        return isTrailingVisible.get();
    }

    public void setTrailingVisible(final boolean labelFloat)
    {
        isTrailingVisible.set(labelFloat);
    }
    
    private static final CssMetaData<TextField, Boolean> LABEL_FLOAT = 
            FACTORY.createBooleanCssMetaData("-rt-label-float", s -> s.isLabelFloating, false, false);

    /**
     * When enabled, the prompt text will be positioned above the input text.
     * When disabled, the prompt text will disappear when the input text is
     * entered.
     */
    private StyleableBooleanProperty isLabelFloating = new SimpleStyleableBooleanProperty(
            LABEL_FLOAT, this, "labelFloat")
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
    public StyleableBooleanProperty labelFloatProperty()
    {
        return this.isLabelFloating;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLabelFloat()
    {
        return isLabelFloating.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabelFloat(final boolean labelFloat)
    {
        isLabelFloating.set(labelFloat);
    }


    private static final CssMetaData<TextField, Paint> FOCUS_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-focus-color", s -> s.focusColor, DefaultPalette.getInstance().getAccentColor(), false);
    
    /**
     * The focus color specifies the accent colors used when the component is
     * focused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            FOCUS_COLOR, this, "focusColor");
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableObjectProperty<Paint> focusColorProperty()
    {
        return this.focusColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paint getFocusColor()
    {
        return this.focusColor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFocusColor(Paint color)
    {
        this.focusColor.set(color);
    }
    
    private static final CssMetaData<TextField, Paint> UNFOCUS_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-unfocus-color", s -> s.unfocusColor, DefaultPalette.getInstance().getBaseColor(), false);

    /**
     * The unfocus color specifies the accent colors used when the component is
     * unfocused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            UNFOCUS_COLOR, this, "unfocusColor");

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableObjectProperty<Paint> unfocusColorProperty()
    {
        return this.unfocusColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paint getUnfocusColor()
    {
        return unfocusColor.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUnfocusColor(Paint color)
    {
        this.unfocusColor.set(color);
    }

    private static final CssMetaData<TextField, Paint> OVERLAY_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    
    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            OVERLAY_COLOR, this, "overlayColor");

    public ObjectProperty<Paint> overlayColorProperty()
    {
        return this.overlayColor;
    }

    public Paint getOverlayColor()
    {
        return overlayColor.get();
    }

    public void setOverlayColor(Paint overlayColor)
    {
        this.overlayColor.set(overlayColor);
    }

    private static final CssMetaData<TextField, Boolean> DISABLE_ANIMATION = 
            FACTORY.createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, this, "disableAnimation");

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableBooleanProperty disableAnimationProperty()
    {
        return this.isAnimationDisabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isDisableAnimation()
    {
        return this.isAnimationDisabled.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisableAnimation(Boolean disabled)
    {
        this.isAnimationDisabled.set(disabled);
    }

    private static final CssMetaData<TextField, Paint> TRAILING_ICON_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-trailing-icon-color", s -> s.trailingIconColor, DefaultPalette.getInstance().getLightBaseColor(), false);

    /**
     * Sets color of the trailing icon.
     * <p>
     * When unspecified, the trailing icon color will be specified by the color
     * used when defining the icon.
     */
    private StyleableObjectProperty<Paint> trailingIconColor = new SimpleStyleableObjectProperty<>(
            TRAILING_ICON_COLOR, this, "trailingIconColor");
    
    public ObjectProperty<Paint> trailingIconColorProperty()
    {
        return this.trailingIconColor;
    }

    public Paint getTrailingIconColor()
    {
        return trailingIconColor.get();
    }

    public void setTrailingIconColor(Color color)
    {
        this.trailingIconColor.set(color);
    }

    private static final CssMetaData<TextField, Number> TRAILING_PADDING = 
            FACTORY.createSizeCssMetaData("-rt-trailing-gap", s -> s.trailingIconGap, 10, false);
    
    /**
     * Sets the padding between the text field right border and the start of the
     * trailing icon.
     */
    private StyleableDoubleProperty trailingIconGap = new SimpleStyleableDoubleProperty(
            TRAILING_PADDING, this, "trailingIconGap");
    
    public DoubleProperty trailingIconGapProperty()
    {
        return this.trailingIconGap;
    }

    public double getTrailingIconGap()
    {
        return this.trailingIconGap.get();
    }

    public void setTrailingIconGap(double trailingIconGap)
    {
        this.trailingIconGap.set(trailingIconGap);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
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
    
    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";

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

    static
    {
        TextField.loadStyleSheet();
    }
}
