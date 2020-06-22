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
import mil.af.eglin.ccf.rt.fx.control.skins.RtTextAreaSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.control.validation.Validator;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO error and helper pseudo states do not apply correctly
public class TextArea extends javafx.scene.control.TextArea implements RtStyleableComponent, RtLabelFloatControl, RtDescriptionControl, ValidableControl<String>
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
    private StyleableBooleanProperty labelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, TextArea.this, "disableAnimation", false)
    {
        @Override 
        protected void invalidated() 
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, get());
        }
    };
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, TextArea.this, "unfocusColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, TextArea.this, "focusColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, TextArea.this, "overlayColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, TextArea.this, "disableAnimation", false);
    // @formatter:on
    
    public TextArea()
    {
        super();
        initialize();
    }
    
    public TextArea(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public TextArea(String text)
    {
        super(text);
        initialize();
    }
    
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
    protected Skin<?> createDefaultSkin()
    {
        return new RtTextAreaSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableValue<String> getObservable()
    {
        return textProperty();
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<TextArea, Paint> OVERLAY_COLOR = new CssMetaData<TextArea, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(TextArea control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextArea control)
            {
                return control.overlayColor;
            }
        };
        
        private static final CssMetaData<TextArea, Boolean> LABEL_FLOAT = new CssMetaData<TextArea, Boolean>(
                "-rt-label-float", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(TextArea control)
            {
                return control.labelFloating == null || !control.labelFloating.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextArea control)
            {
                return control.labelFloating;
            }
        };
        private static final CssMetaData<TextArea, Paint> UNFOCUS_COLOR = new CssMetaData<TextArea, Paint>(
                "-rt-unfocus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(TextArea control)
            {
                return control.unfocusColor == null || !control.unfocusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextArea control)
            {
                return control.unfocusColor;
            }
        };
        private static final CssMetaData<TextArea, Paint> FOCUS_COLOR = new CssMetaData<TextArea, Paint>(
                "-rt-focus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(TextArea control)
            {
                return control.focusColor == null || !control.focusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextArea control)
            {
                return control.focusColor;
            }
        };
        private static final CssMetaData<TextArea, Boolean> DISABLE_ANIMATION = new CssMetaData<TextArea, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(TextArea control)
            {
                return control.disableAnimation == null || !control.disableAnimation.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextArea control)
            {
                return control.disableAnimation;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.TextArea.getClassCssMetaData());
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
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        TextArea.loadStyleSheet();
        ScrollPane.loadStyleSheet();
    }
}
