package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
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
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO add an option to have a trailing icon
public class TextField extends javafx.scene.control.TextField implements RtComponent
{
    private static final PseudoClass FLOATING_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("floating");
    
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";
    
    private ObjectProperty<RtGlyph> trailingIcon = new SimpleObjectProperty<RtGlyph>();
    
    // @formatter:off
    private StyleableBooleanProperty labelFloating = new SimpleStyleableBooleanProperty(
            StyleableProperties.LABEL_FLOAT, this, "labelFloat", false);
    private StyleableObjectProperty<Paint> unfocusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFOCUS_COLOR, TextField.this, "unfocusColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> focusColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FOCUS_COLOR, TextField.this, "focusColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, TextField.this, "overlayColor", DefaultPalette.getInstance().getBaseColor());
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

    public StyleableBooleanProperty labelFloatProperty()
    {
        return this.labelFloating;
    }

    public boolean isLabelFloat()
    {
        return labelFloating.getValue();
    }

    public void setLabelFloat(final boolean labelFloat)
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

    public StyleableObjectProperty<Paint> unfocusProperty()
    {
        return this.unfocusColor;
    }

    public Paint getUnfocusColor()
    {
        return unfocusColor.getValue();
    }

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
        return overlayColor.getValue();
    }

    public void setOverlayColor(Paint overlayColor)
    {
        this.overlayColor.setValue(overlayColor);
    }

    public final StyleableBooleanProperty disableAnimationProperty()
    {
        return this.disableAnimation;
    }

    public final Boolean isDisableAnimation()
    {
        return this.disableAnimation.getValue();
    }

    public final void setDisableAnimation(Boolean disabled)
    {
        this.disableAnimation.set(disabled);
    }

    public final ObjectProperty<RtGlyph> trailingGlyphProperty()
    {
        return this.trailingIcon;
    }

    public final RtGlyph getTrailingGlyph()
    {
        return this.trailingIcon.getValue();
    }

    public final void setTrailingGlyph(RtGlyph glyph)
    {
        this.trailingIcon.setValue(glyph);
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
        getStyleClass().add(this.accent.getCssName());
        
        pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, this.labelFloating.getValue());
        this.labelFloating.addListener((ov, oldVal, newVal) -> 
        {
            pseudoClassStateChanged(FLOATING_PSEUDOCLASS_STATE, newVal);
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
            Collections.addAll(styleables, LABEL_FLOAT, UNFOCUS_COLOR, FOCUS_COLOR, DISABLE_ANIMATION);
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
