package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

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
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtComboBoxSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.control.validation.Validator;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * Combo boxes allow the user to select one or more options from a list of
 * options.
 * <p>
 * A combo box is typically skinned as a text entry with a drop down button.
 * Selecting the drop down button will open a list of selectable options.
 */
public class ComboBox<T> extends javafx.scene.control.ComboBox<T>
        implements RtStyleableComponent, RtLabelFloatControl, RtDescriptionControl, ValidableControl<T>
{
    protected Accent accent = Accent.PRIMARY_MID;

    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");

    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-combo-box";

    private ValidableHandler<T> validationHandler = new ValidableHandler<>(this);

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
     * When enabled, the prompt text will be positioned above the input text.
     * When disabled, the prompt text will disappear when the input text is
     * entered.
     */
    private StyleableBooleanProperty isLabelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, this, "labelFloat", false)
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
            StyleableProperties.UNFOCUS_COLOR, ComboBox.this, "unfocusColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * The focus color specifies the accent colors used when the component is
     * focused.
     * <p>
     * Accented color typically include the border, prompt text, and drop down
     * icon.
     */
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, ComboBox.this, "focusColor",
            DefaultPalette.getInstance().getAccentColor());

    /**
     * The overlay color specifies the background color used when combobox is
     * hovered over or focused
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, ComboBox.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, ComboBox.this, "disableAnimation", false);

    /**
     * Creates a default ComboBox instance with an empty items list and default
     * selection model.
     */
    public ComboBox()
    {
        super();
        initialize();
    }

    /**
     * Creates a default ComboBox instance with the provided accent, an empty
     * items list, and the default selection model.
     * 
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ComboBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a default ComboBox instance with the provided items list and a
     * default selection model.
     * 
     * @param items The list of items available to the combo box.
     */
    public ComboBox(ObservableList<T> items)
    {
        super(items);
        initialize();
    }

    /**
     * Creates a default ComboBox instance with the provided accent, items list,
     * and a default selection model.
     * 
     * @param items The list of items available to the combo box.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ComboBox(ObservableList<T> items, Accent accent)
    {
        super(items);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ReadOnlyBooleanProperty isValidProperty()
    {
        return this.isValid;
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

    public ObservableList<Validator<T>> getValidators()
    {
        return this.validationHandler.getValidators();
    }

    public void setValidateCondition(ValidateCondition validateCondition)
    {
        this.validationHandler.setValidateCondition(validateCondition);
    }

    public void getValidateCondition(ValidateCondition validateCondition)
    {
        this.validationHandler.getValidateCondition();
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public final BooleanProperty isShowHelperTextProperty()
    {
        return this.isShowHelperText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean getIsShowHelperText()
    {
        return this.isShowHelperText.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setIsShowHelperText(boolean isShowHelperText)
    {
        this.isShowHelperText.set(isShowHelperText);
    }

    public final boolean isHelperTextVisible()
    {
        return isShowHelperText.get() || getValidators().size() > 0;
    }

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

    public final ObjectProperty<Paint> getOverlayColorProperty()
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

    @Override
    public ObservableValue<T> getObservable()
    {
        return valueProperty();
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
    public Control getControl()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtComboBoxSkin<>(this);
    }

    @Override
    public void setValid(boolean isValid)
    {
        this.isValid.set(isValid);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());

        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.isLabelFloating.get());
        pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, this.isShowHelperText.get() || getValidators().size() > 0);
        this.isLabelFloating.addListener((ov, oldVal, newVal) ->
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, newVal);
        });
        this.isShowHelperText.addListener((ov, oldVal, newVal) ->
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, newVal);
        });
        this.validationHandler.getValidators().addListener(new ListChangeListener<Validator<T>>()
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Validator<T>> c)
            {
                pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, isHelperTextVisible());
            }
        });
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<ComboBox<?>, Paint> OVERLAY_COLOR = new CssMetaData<ComboBox<?>, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ComboBox<?> control)
            {
                return control.overlayColor;
            }
        };

        private static final CssMetaData<ComboBox<?>, Boolean> LABEL_FLOAT = new CssMetaData<ComboBox<?>, Boolean>(
                "-rt-label-float", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.isLabelFloating == null || !control.isLabelFloating.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(ComboBox<?> control)
            {
                return control.isLabelFloating;
            }
        };
        private static final CssMetaData<ComboBox<?>, Paint> UNFOCUS_COLOR = new CssMetaData<ComboBox<?>, Paint>(
                "-rt-unfocus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.unfocusColor == null || !control.unfocusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ComboBox<?> control)
            {
                return control.unfocusColor;
            }
        };
        private static final CssMetaData<ComboBox<?>, Paint> FOCUS_COLOR = new CssMetaData<ComboBox<?>, Paint>(
                "-rt-focus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.focusColor == null || !control.focusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ComboBox<?> control)
            {
                return control.focusColor;
            }
        };
        private static final CssMetaData<ComboBox<?>, Boolean> DISABLE_ANIMATION = new CssMetaData<ComboBox<?>, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(ComboBox<?> control)
            {
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ComboBox.getClassCssMetaData());
            // @formatter:off
            styleables.add(LABEL_FLOAT);
            styleables.add(UNFOCUS_COLOR);
            styleables.add(FOCUS_COLOR);
            styleables.add(OVERLAY_COLOR);
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
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
