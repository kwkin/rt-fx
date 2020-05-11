package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtTextFieldSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextFieldStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO add an option to have a trailing icon
public class TextField extends javafx.scene.control.TextField implements RtComponent
{
    protected TextFieldStyle style = TextFieldStyle.FILLED;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";

    // @formatter:off
    private StyleableBooleanProperty labelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, this, "lableFloat", false);
    private StyleableObjectProperty<Paint> unFocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, TextField.this, "unFocusColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, TextField.this, "focusColor", DefaultPalette.getInstance().getAccentColor());
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

    public TextField(TextFieldStyle style)
    {
        this();
        this.style = style;
        initialize();
    }

    public TextField(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public TextField(TextFieldStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    public TextFieldStyle getButtonStyle()
    {
        return this.style;
    }

    public final StyleableBooleanProperty labelFloatProperty()
    {
        return this.labelFloating;
    }

    public final boolean isLabelFloat()
    {
        return labelFloating.getValue();
    }

    public final void setLabelFloat(final boolean labelFloat)
    {
        labelFloating.setValue(labelFloat);
    }

    public StyleableObjectProperty<Paint> focusColorProperty()
    {
        return this.focusColor;
    }

    public Paint getFocusColor()
    {
        return this.focusColor.getValue();
    }

    public void setFocusColor(Paint color)
    {
        this.focusColor.set(color);
    }

    public StyleableObjectProperty<Paint> unFocusColorProperty()
    {
        return this.unFocusColor;
    }

    public Paint getUnFocusColor()
    {
        return unFocusColor.getValue();
    }

    public void setUnFocusColor(Paint color)
    {
        this.unFocusColor.set(color);
    }

    public final StyleableBooleanProperty disableAnimationProperty()
    {
        return this.disableAnimation;
    }

    public final Boolean isDisableAnimation()
    {
        return this.disableAnimation.getValue();
    }

    public final void setDisableAnimation(final Boolean disabled)
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
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
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
                return control.labelFloatProperty();
            }
        };
        private static final CssMetaData<TextField, Paint> UNFOCUS_COLOR = new CssMetaData<TextField, Paint>(
                "-rt-unfocus-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(TextField control)
            {
                return control.unFocusColor == null || !control.unFocusColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(TextField control)
            {
                return control.unFocusColorProperty();
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
                return control.focusColorProperty();
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
                return control.disableAnimationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.TextField.getClassCssMetaData());
            Collections.addAll(styleables, LABEL_FLOAT, UNFOCUS_COLOR, FOCUS_COLOR, DISABLE_ANIMATION);
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
