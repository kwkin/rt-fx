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
import mil.af.eglin.ccf.rt.fx.control.skins.RtTextAreaSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.fx.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.validation.Validator;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * Text fields allow users to enter and edit multiple lines of text.
 */
public class TextArea extends javafx.scene.control.TextArea
        implements RtStyleableComponent, LabelFloatControl, DescriptionControl, ValidableControl<String>
{
    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");

    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "text-area.css";
    private static final String CSS_CLASS = "rt-text-area";

    private ValidableHandler<String> validationHandler = new ValidableHandler<>(this);

    // @formatter:off
    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    private BooleanProperty isShowHelperText = new SimpleBooleanProperty() 
    {
        @Override 
        protected void invalidated() 
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, get() || getValidators().size() > 0);
        }
    };
    private StringProperty helperText = new SimpleStringProperty();
    private StringProperty errorText = new SimpleStringProperty();

    private static final StyleablePropertyFactory<TextArea> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.TextArea.getClassCssMetaData());

    private static final CssMetaData<TextArea, Boolean> LABEL_FLOAT = 
            FACTORY.createBooleanCssMetaData("-rt-label-float", s -> s.isLabelFloating, false, false);
    private static final CssMetaData<TextArea, Paint> UNFOCUS_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-unfocus-color", s -> s.unfocusColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<TextArea, Paint> FOCUS_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-focus-color", s -> s.focusColor, DefaultPalette.getInstance().getAccentColor(), false);
    private static final CssMetaData<TextArea, Paint> OVERLAY_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<TextArea, Boolean> DISABLE_ANIMATION = 
            FACTORY.createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

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
     * The unfocus color specifies the accent colors used when the component is
     * unfocused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            UNFOCUS_COLOR, this, "unfocusColor");

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
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            OVERLAY_COLOR, this, "overlayColor");

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, this, "disableAnimation");
    // @formatter:on

    /**
     * Create a {@code TextArea} with an empty text input
     */
    public TextArea()
    {
        super();
        initialize();
    }

    /**
     * Create a {@code TextArea} with initial text content
     * 
     * @param text the initial text value
     */
    public TextArea(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Create a {@code TextArea} with the specified accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public TextArea(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Create a {@code TextArea} with initial text content and accent
     * 
     * @param text the initial text value
     * @param accent the accent used to change the component's color scheme
     */
    public TextArea(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

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

    public ObjectProperty<Paint> getOverlayColorProperty()
    {
        return this.overlayColor;
    }

    public Paint getOverlayColor()
    {
        return overlayColor.get();
    }

    public void setOverlayColor(Color overlayColor)
    {
        this.overlayColor.set(overlayColor);
    }

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
     * {@inheritDoc}
     */
    @Override
    public BooleanProperty isShowHelperTextProperty()
    {
        return this.isShowHelperText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getIsShowHelperText()
    {
        return this.isShowHelperText.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsShowHelperText(boolean isShowHelperText)
    {
        this.isShowHelperText.set(isShowHelperText);
    }

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

    public boolean isHelperTextVisible()
    {
        return isShowHelperText.get() || getValidators().size() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control getControl()
    {
        return this;
    }

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
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtTextAreaSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableValue<String> getObservableValue()
    {
        return textProperty();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
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

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    static
    {
        ScrollPane.loadStyleSheet();
        TextArea.loadStyleSheet();
    }
}
