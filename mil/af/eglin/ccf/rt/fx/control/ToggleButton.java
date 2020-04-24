package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.converters.BooleanConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ToggleButtonStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO add selected state
public class ToggleButton extends javafx.scene.control.ToggleButton implements RtComponent
{
    protected ToggleButtonStyle style = ToggleButtonStyle.RAISED;
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "toggle-button.css";
    private static final String CSS_CLASS = "rt-toggle-button";

    private StyleableObjectProperty<Boolean> isAnimationDisabled = new SimpleStyleableObjectProperty<>(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    
    public ToggleButton()
    {
        super();
        initialize();
    }
    
    public ToggleButton(String text)
    {
        super(text);
        initialize();
    }
    
    public ToggleButton(String text, ToggleButtonStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }
    
    public ToggleButton(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }
    
    public ToggleButton(String text, ToggleButtonStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    public ToggleButton(String text, Node graphic)
    {
        super(text, graphic);
    }

    public StyleableObjectProperty<Boolean> isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.getValue();
    }

    public void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.setValue(isAnimationDisabled);
    }
    
    public ToggleButtonStyle getButtonStyle()
    {
        return this.style;
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
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() 
    {
        return new RtToggleButtonSkin(this);
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<ToggleButton, Boolean> DISABLE_ANIMATION = new CssMetaData<ToggleButton, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ToggleButton control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(ToggleButton control)
            {
                return control.isAnimationDisabledProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(javafx.scene.control.CheckBox.getClassCssMetaData());
            styleables.add(DISABLE_ANIMATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}
