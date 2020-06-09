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
// TODO if a logging name is required, consider renaming Button to RtButtton, since we lose the benefit of simply replacing the import statement
// TODO default button style
// TODO cancel button style
// TODO use pseudo classes for styles
// TODO link button styles in Java doc

/**
 * A simple button control allows the user to take actions with a single press.
 * <p>
 * In general, icon buttons should be created using the {@link IconButton
 * IconButton} class whenever using a
 * {@link RtIcon RtIcon} as the graphic. The
 * {@link IconButton IconButton} class provides additional API for styling and
 * using the icon inside the button. This class still allows the Icon type in
 * case a generic node wants to be used instead of {@link RtIcon RtIcon}.
 * 
 * @see IconButton
 */
public class Button extends javafx.scene.control.Button implements RtStyleableComponent
{
    protected ButtonStyle style = ButtonStyle.RAISED;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "button.css";
    private static final String CSS_CLASS = "rt-button";

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, Button.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, Button.this, "disableAnimation", false);

    /**
     * Creates a raised button with an empty string for its label.
     */
    public Button()
    {
        super();
        initialize();
    }

    /**
     * Creates a button with the specified style.
     * 
     * @param style The style used to change the overall look of the button.
     */
    public Button(ButtonStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates a button with the specified style and accent
     * 
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Button(ButtonStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a raised button with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public Button(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a button with the specified label text and style
     * 
     * @param text A text string for its label.
     * @param style The style type used to change the component's look.
     */
    public Button(String text, ButtonStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }

    /**
     * Creates a button with the specified label text, style, and accent
     * 
     * @param text A text string for its label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
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
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     */
    public Button(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates a button with the specified label text, icon, and style.
     *
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     * @param style The style type used to change the component's look.
     */
    public Button(String text, Node graphic, ButtonStyle style)
    {
        super(text, graphic);
        this.style = style;
        initialize();
    }

    /**
     * Creates a button with the specified label text, icon, style, and accent.
     *
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Button(String text, Node graphic, ButtonStyle style, Accent accent)
    {
        super(text, graphic);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Gets the style type of the button.
     * 
     * @return The style type of the button
     */
    public ButtonStyle getButtonStyle()
    {
        return this.style;
    }

    public final ObjectProperty<Paint> overlayColorProperty()
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

    public final BooleanProperty isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public final boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.get();
    }

    public final void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.set(isAnimationDisabled);
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
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
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
