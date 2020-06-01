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

// TODO truncate floating label and helper/error text
public class TextField extends javafx.scene.control.TextField implements RtComponent, RtLabelFloatControl, RtDescriptionControl, ValidableControl<String>
{
    public static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    public static final PseudoClass HELPER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("helper");
    
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";

    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    private BooleanProperty isShowHelperText = new SimpleBooleanProperty();
    private StringProperty helperText = new SimpleStringProperty();
    private StringProperty errorText = new SimpleStringProperty();
    private ObjectProperty<RtGlyph> trailingIcon = new SimpleObjectProperty<RtGlyph>();
    
    private ValidableHandler<String> validationHandler = new ValidableHandler<>(this);
    
    // @formatter:off
    private StyleableBooleanProperty labelFloating = new StyleableBooleanProperty(false)
    {

        @Override 
        protected void invalidated() 
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, get());
        }

        @Override
        public CssMetaData<TextField, Boolean> getCssMetaData() 
        {
            return StyleableProperties.LABEL_FLOAT;
        }

        @Override
        public Object getBean()
        {
            return TextField.this;
        }

        @Override
        public String getName() 
        {
            return "labelFloat";
        }
    };
    
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, TextField.this, "unfocusColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, TextField.this, "focusColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, TextField.this, "overlayColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableDoubleProperty trailingIconGap = new SimpleStyleableDoubleProperty(
            StyleableProperties.TRAILING_ICON_PADDING, TextField.this, "trailingIconGap", 10.0);
    private StyleableObjectProperty<Paint> trailingIconColor = new SimpleStyleableObjectProperty<Paint>(
            StyleableProperties.TRAILING_ICON_COLOR, TextField.this, "trailingIconColor", DefaultPalette.getInstance().getLightBaseColor());
    private StyleableDoubleProperty helperTextHeight = new SimpleStyleableDoubleProperty(
            StyleableProperties.HELPER_TEXT_HEIGHT, TextField.this, "helperTextHeight", 16.0);
    private StyleableBooleanProperty disableAnimation = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, TextField.this, "disableAnimation", false);
    // @formatter:on

    public TextField()
    {
        super();
        initialize();
    }

    public TextField(String text)
    {
        super(text);
        initialize();
    }

    public TextField(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

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

    public ObjectProperty<RtGlyph> trailingGlyphProperty()
    {
        return this.trailingIcon;
    }

    public RtGlyph getTrailingGlyph()
    {
        return this.trailingIcon.get();
    }

    public void setTrailingGlyph(RtGlyph glyph)
    {
        this.trailingIcon.set(glyph);
    }

    public ObjectProperty<Paint> trailingGlyphColorProperty()
    {
        return this.trailingIconColor;
    }

    public Paint getTrailingGlyphColor()
    {
        return trailingIconColor.get();
    }

    public void setTrailingGlyphColor(Paint color)
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
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
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
        
        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.labelFloating.get());
        pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, this.isShowHelperText.get() || getValidators().size() > 0);
        this.isShowHelperText.addListener((ov, oldVal, newVal) -> 
        {
            pseudoClassStateChanged(HELPER_PSEUDOCLASS_STATE, newVal);
        });
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
                return control.labelFloating == null || !control.labelFloating.isBound();
            }

            @Override
            public StyleableBooleanProperty getStyleableProperty(TextField control)
            {
                return control.labelFloating;
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
        private static final CssMetaData<TextField, Number> TRAILING_ICON_PADDING = new CssMetaData<TextField, Number>(
                "-rt-trailing-icon-gap", SizeConverter.getInstance(), 10.0)
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
                "-rt-trailing-icon-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getLightBaseColor())
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
        private static final CssMetaData<TextField, Number> HELPER_TEXT_HEIGHT = new CssMetaData<TextField, Number>(
                "-rt-helper-text-height", SizeConverter.getInstance(), 16.0)
        {

            @Override
            public boolean isSettable(TextField control)
            {
                return control.helperTextHeight == null || !control.helperTextHeight.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(TextField control)
            {
                return control.helperTextHeight;
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
            styleables.add(TRAILING_ICON_PADDING);
            styleables.add(TRAILING_ICON_COLOR);
            styleables.add(HELPER_TEXT_HEIGHT);
            styleables.add(DISABLE_ANIMATION);
            // @formatter:on
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
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
