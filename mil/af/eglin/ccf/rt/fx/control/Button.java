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
import mil.af.eglin.ccf.rt.fx.control.skins.RtButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO incorporate logging
public class Button extends javafx.scene.control.Button implements RtComponent
{
    protected ButtonStyle style = ButtonStyle.RAISED;
    protected Accent accent;
    
    private static final String USER_AGENT_STYLESHEET = "button.css";
    private static final String CSS_CLASS = "rt-button";

    // TODO convert to BooleanProperty
    private StyleableObjectProperty<Boolean> isAnimationDisabled = new SimpleStyleableObjectProperty<>(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    
    /**
     * Creates a button with an empty string for its label.
     */
    public Button()
    {
        super();
        initialize();
    }

    public Button(ButtonStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    public Button(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Button(ButtonStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a button with the specified text as its label.
     *
     * @param text
     *            A text string for its label.
     */
    public Button(String text)
    {
        super(text);
        initialize();
    }

    public Button(String text, ButtonStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }

    public Button(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public Button(String text, ButtonStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a button with the specified text and icon for its label.
     *
     * @param text
     *            A text string for its label.
     * @param graphic
     *            the icon for its label.
     */
    public Button(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    public Button(String text, Node graphic, ButtonStyle style)
    {
        super(text, graphic);
        this.style = style;
        initialize();
    }

    public Button(String text, Node graphic, Accent accent)
    {
        super(text, graphic);
        this.accent = accent;
        initialize();
    }

    public Button(String text, Node graphic, ButtonStyle style, Accent accent)
    {
        super(text, graphic);
        this.style = style;
        this.accent = accent;
        initialize();
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

    public ButtonStyle getButtonStyle()
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

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtButtonSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        if (this.accent != null)
        {
            getStyleClass().add(this.accent.getCssName());
        }
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<Button, Boolean> DISABLE_ANIMATION = new CssMetaData<Button, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(Button control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(Button control)
            {
                return control.isAnimationDisabledProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ToggleButton.getClassCssMetaData());
            // @formatter:off
            Collections.addAll(styleables, DISABLE_ANIMATION);
            // @formatter:on
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}
