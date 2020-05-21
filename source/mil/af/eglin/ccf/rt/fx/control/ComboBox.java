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
import mil.af.eglin.ccf.rt.fx.control.skins.RtComboBoxSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.control.validation.Validator;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> implements RtComponent, RtLabelFloatControl, ValidableControl<T>
{
    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");
    
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-combo-box";

    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    private BooleanProperty isShowHelperText = new SimpleBooleanProperty();
    private StringProperty helperText = new SimpleStringProperty();
    private StringProperty errorText = new SimpleStringProperty();    
    private ValidableHandler<T> validationHandler = new ValidableHandler<>(this);

    private StyleableBooleanProperty labelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, this, "labelFloat", false);
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, ComboBox.this, "unfocusColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, ComboBox.this, "focusColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, ComboBox.this, "overlayColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableDoubleProperty helperTextHeight = new SimpleStyleableDoubleProperty(
            StyleableProperties.HELPER_TEXT_HEIGHT, ComboBox.this, "helperTextHeight", 16.0);
    private StyleableObjectProperty<Paint> promptTextFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.PROMPT_TEXT_FILL, ComboBox.this, "prompteTxtFill", DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, ComboBox.this, "disableAnimation", false);
    
    public ComboBox()
    {
        super();
        initialize();
    }
    
    public ComboBox(Accent accent) 
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ComboBox(ObservableList<T> items) 
    {
        super(items);
        initialize();
    }
    
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
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
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
    public boolean validate()
    {
        this.isValid.set(this.validationHandler.validate(getValue()));
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

    @Override
    public ObservableValue<T> getObservable()
    {
        return valueProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableBooleanProperty labelFloatProperty()
    {
        return this.labelFloating;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLabelFloat()
    {
        return labelFloating.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabelFloat(final boolean labelFloat)
    {
        labelFloating.set(labelFloat);
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

    public StyleableObjectProperty<Paint> promptTextFillProperty()
    {
        return this.promptTextFill;
    }

    public Paint getPromptTextFill()
    {
        return this.promptTextFill.get();
    }

    public void setPromptTextFill(Paint color)
    {
        this.promptTextFill.set(color);
    }

    public DoubleProperty helperTextHeightProperty()
    {
        return this.helperTextHeight;
    }

    public double getHelperTextHeight()
    {
        return this.helperTextHeight.get();
    }

    public void setHelperTextHeight(double helperTextHeight)
    {
        this.helperTextHeight.set(helperTextHeight);
    }
    
    public boolean isHelperTextVisible()
    {
        return isShowHelperText.get() || getValidators().size() > 0;
    }

    public ObservableList<Validator<T>> getValidators()
    {
        return this.validationHandler.getValidators();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StyleableObjectProperty<Paint> unfocusProperty()
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

    @Override
    public StyleableBooleanProperty disableAnimationProperty()
    {
        return this.disableAnimation;
    }

    @Override
    public Boolean isDisableAnimation()
    {
        return this.disableAnimation.get();
    }

    @Override
    public void setDisableAnimation(Boolean disabled)
    {
        this.disableAnimation.set(disabled);
    }

    public ObjectProperty<Paint> getOverlayColorProperty()
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

    public BooleanProperty isShowHelperTextProperty()
    {
        return this.isShowHelperText;
    }

    public boolean getIsShowHelperText()
    {
        return this.isShowHelperText.get();
    }

    public void setIsShowHelperText(boolean isShowHelperText)
    {
        this.isShowHelperText.set(isShowHelperText);
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
    public boolean setValid(boolean isValid)
    {
        return isValid;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());

        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.labelFloating.get());
        pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, this.isShowHelperText.get() || getValidators().size() > 0);
        this.labelFloating.addListener((ov, oldVal, newVal) -> 
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
                return control.labelFloating == null || !control.labelFloating.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(ComboBox<?> control)
            {
                return control.labelFloating;
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
        private static final CssMetaData<ComboBox<?>, Number> HELPER_TEXT_HEIGHT = new CssMetaData<ComboBox<?>, Number>(
                "-rt-helper-text-height", SizeConverter.getInstance(), 16.0)
        {

            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.helperTextHeight == null || !control.helperTextHeight.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(ComboBox<?> control)
            {
                return control.helperTextHeight;
            }
        };
        private static final CssMetaData<ComboBox<?>, Paint> PROMPT_TEXT_FILL = new CssMetaData<ComboBox<?>, Paint>(
                "-fx-prompt-text-fill", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.promptTextFill == null || !control.promptTextFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ComboBox<?> control)
            {
                return control.promptTextFill;
            }
        };
        private static final CssMetaData<ComboBox<?>, Boolean> DISABLE_ANIMATION = new CssMetaData<ComboBox<?>, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ComboBox<?> control)
            {
                return control.disableAnimation == null || !control.disableAnimation.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(ComboBox<?> control)
            {
                return control.disableAnimation;
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
            styleables.add(PROMPT_TEXT_FILL);
            styleables.add(HELPER_TEXT_HEIGHT);
            styleables.add(DISABLE_ANIMATION);
            // @formatter:on
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
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
