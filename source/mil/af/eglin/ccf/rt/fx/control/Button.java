package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO incorporate logging
// Logged components should have easily discernable names. Some concerns:
// Not all components have a variable name
// Some components are dynamically created, such as in a for loop.
// Some components may have the exact same content (remove icon buttons in a list of entries)
//
// Some ideas:
// If a specific logging name is defined, use that. (Maybe not do this to reduce variance with all these if/else)
// Else If the component/layout has a variable name, use that and the index in the parent layout
// Else use the component/layout type, context (if component), and the index in the parent layout
// Use reflection to recursively get variable names of the component and parent layouts/scene
//
// Examples:
// mainScene.sideBar.mainTree.treeItem-5.deleteIcon
// mainScene.sideBar.tree-view-1.tree-item-5.icon-button-(delete)-2
//
// TODO default button style
// TODO cancel button style
public class Button extends javafx.scene.control.Button implements RtComponent
{
    protected ButtonStyle style = ButtonStyle.RAISED;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "button.css";
    private static final String CSS_CLASS = "rt-button";

    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, Button.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, Button.this, "disableAnimation", false);

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

    public BooleanProperty isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.get();
    }

    public void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.set(isAnimationDisabled);
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

    /**
     * {@inheritDoc}
     */
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
        return null;
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
        private static final CssMetaData<Button, Paint> OVERLAY_COLOR = new CssMetaData<Button, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(Button control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Button control)
            {
                return control.overlayColor;
            }
        };

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
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ToggleButton.getClassCssMetaData());
            styleables.add(OVERLAY_COLOR);
            styleables.add(DISABLE_ANIMATION);
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

    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
