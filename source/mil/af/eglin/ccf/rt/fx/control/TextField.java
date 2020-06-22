package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.css.converters.SizeConverter;

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
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtTextFieldSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.control.validation.Validator;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
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
        implements RtStyleableComponent, RtLabelFloatControl, RtDescriptionControl, ValidableControl<String>
{
    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");

    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";

    private ValidableHandler<String> validationHandler = new ValidableHandler<>(this);

    /**
     * Indicates if the current value is valid according to the validator
     * conditions.
     */
    private BooleanProperty isValid = new SimpleBooleanProperty(true);

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
     * The text to use for the helper description located below the input field.
     */
    private StringProperty helperText = new SimpleStringProperty();

    /**
     * The text to use for the error description located below the input field.
     * The error description will override the helper text when the component is
     * invalid. When valid, the helper text will be visible.
     */
    private StringProperty errorText = new SimpleStringProperty();

    /**
     * Trailing text adds a label to the end of the text field.
     * <p>
     * Trailing text will override a trailing icon if both are set. The trailing
     * text is considered to be set if it is not empty or null.
     */
    private ObjectProperty<String> trailingText = new SimpleObjectProperty<String>();

    /**
     * A trailing icon adds an icon to the end of the text field. Input text
     * will be clipped before overlapping with the trailing icon.
     * <p>
     * Trailing text will override a trailing icon if both are set.
     */
    private ObjectProperty<RtIcon> trailingIcon = new SimpleObjectProperty<RtIcon>();

    /**
     * When enabled, the prompt text will be positioned above the input text.
     * When disabled, the prompt text will disappear when the input text is
     * entered.
     */
    private StyleableBooleanProperty islabelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, TextField.this, "disableAnimation", false)
    {
        @Override
        protected void invalidated()
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, get());
        }
    };

    /**
     * Toggles the visibility of the trailing text or icon.
     * <p>
     * Trailing text will override a trailing icon if both are set.
     */
    private StyleableBooleanProperty isTrailingVisible = new SimpleStyleableBooleanProperty(
            StyleableProperties.TRAILING_VISIBILITY, TextField.this, "trailingVisible", true);

    /**
     * The unfocus color specifies the accent colors used when the component is
     * unfocused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, TextField.this, "unfocusColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * The focus color specifies the accent colors used when the component is
     * focused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, TextField.this, "focusColor",
            DefaultPalette.getInstance().getAccentColor());

    /**
     * The overlay color specifies the background color used when combobox is
     * hovered over or focused
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, TextField.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * Sets the padding between the text field right border and the start of the
     * trailing icon.
     */
    private StyleableDoubleProperty trailingIconGap = new SimpleStyleableDoubleProperty(
            StyleableProperties.TRAILING_PADDING, TextField.this, "trailingIconGap", 10.0);

    /**
     * Sets color of the trailing icon.
     * <p>
     * When unspecified, the trailing icon color will be specified by the color
     * used when defining the icon.
     */
    private StyleableObjectProperty<Paint> trailingIconColor = new SimpleStyleableObjectProperty<Paint>(
            StyleableProperties.TRAILING_ICON_COLOR, TextField.this, "trailingIconColor",
            DefaultPalette.getInstance().getLightBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, TextField.this, "disableAnimation", false);

    /**
     * Create a text field with an empty text input
     */
    public TextField()
    {
        super();
        initialize();
    }

    /**
     * Create a text field with initial text content
     */
    public TextField(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Create a text field with the specified accent
     */
    public TextField(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Create a text field with initial text content and accent
     */
    public TextField(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableBooleanProperty labelFloatProperty()
    {
        return this.islabelFloating;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLabelFloat()
    {
        return islabelFloating.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabelFloat(final boolean labelFloat)
    {
        islabelFloating.set(labelFloat);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableBooleanProperty disableAnimationProperty()
    {
        return this.disableAnimation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isDisableAnimation()
    {
        return this.disableAnimation.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisableAnimation(Boolean disabled)
    {
        this.disableAnimation.set(disabled);
    }

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

    public ObjectProperty<RtIcon> trailingIconProperty()
    {
        return this.trailingIcon;
    }

    public RtIcon getTrailingIcon()
    {
        return this.trailingIcon.get();
    }

    public void setTrailingIcon(RtIcon icon)
    {
        this.trailingIcon.set(icon);
    }

    public ObjectProperty<Paint> trailingIconColorProperty()
    {
        return this.trailingIconColor;
    }

    public Paint getTrailingIconColor()
    {
        return trailingIconColor.get();
    }

    public void setTrailingIconColor(Paint color)
    {
        this.trailingIconColor.set(color);
    }

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

    public void setValidateCondition(ValidateCondition validateCondition)
    {
        this.validationHandler.setValidateCondition(validateCondition);
    }

    public void getValidateCondition(ValidateCondition validateCondition)
    {
        this.validationHandler.getValidateCondition();
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
        return new RtTextFieldSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableValue<String> getObservable()
    {
        return textProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());

        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.islabelFloating.get());
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

    private static class StyleableProperties
    {
        private static final CssMetaData<TextField, Paint> OVERLAY_COLOR = new CssMetaData<TextField, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextField control)
            {
                return control.overlayColor;
            }
        };

        private static final CssMetaData<TextField, Boolean> LABEL_FLOAT = new CssMetaData<TextField, Boolean>(
                "-rt-label-float", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.islabelFloating == null || !control.islabelFloating.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextField control)
            {
                return control.islabelFloating;
            }
        };

        private static final CssMetaData<TextField, Boolean> TRAILING_VISIBILITY = new CssMetaData<TextField, Boolean>(
                "-rt-trailing-visiblity", BooleanConverter.getInstance(), true)
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.isTrailingVisible == null || !control.isTrailingVisible.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextField control)
            {
                return control.isTrailingVisible;
            }
        };
        private static final CssMetaData<TextField, Paint> UNFOCUS_COLOR = new CssMetaData<TextField, Paint>(
                "-rt-unfocus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.unfocusColor == null || !control.unfocusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextField control)
            {
                return control.unfocusColor;
            }
        };
        private static final CssMetaData<TextField, Paint> FOCUS_COLOR = new CssMetaData<TextField, Paint>(
                "-rt-focus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.focusColor == null || !control.focusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextField control)
            {
                return control.focusColor;
            }
        };
        private static final CssMetaData<TextField, Number> TRAILING_PADDING = new CssMetaData<TextField, Number>(
                "-rt-trailing-gap", SizeConverter.getInstance(), 10.0)
        {

            @Override
            public boolean isSettable(TextField control)
            {
                return control.trailingIconGap == null || !control.trailingIconGap.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(TextField control)
            {
                return control.trailingIconGap;
            }
        };
        private static final CssMetaData<TextField, Paint> TRAILING_ICON_COLOR = new CssMetaData<TextField, Paint>(
                "-rt-trailing-icon-color", PaintConverter.getInstance(),
                DefaultPalette.getInstance().getLightBaseColor())
        {

            @Override
            public boolean isSettable(TextField control)
            {
                return control.trailingIconColor == null || !control.trailingIconColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextField control)
            {
                return control.trailingIconColor;
            }
        };
        private static final CssMetaData<TextField, Boolean> DISABLE_ANIMATION = new CssMetaData<TextField, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.disableAnimation == null || !control.disableAnimation.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextField control)
            {
                return control.disableAnimation;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.TextField.getClassCssMetaData());
            // @formatter:off
            styleables.add(LABEL_FLOAT);
            styleables.add(UNFOCUS_COLOR);
            styleables.add(FOCUS_COLOR);
            styleables.add(OVERLAY_COLOR);
            styleables.add(TRAILING_PADDING);
            styleables.add(TRAILING_ICON_COLOR);
            styleables.add(DISABLE_ANIMATION);
            // @formatter:on
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * Returns the list of available CSS properties
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
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
        TextField.loadStyleSheet();
    }
}
