package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A button allows the user to take actions with a single press.
 * <p>
 * In general, icon buttons should be created using the {@link IconButton
 * IconButton} class whenever using a {@link Icon RtIcon} as the graphic. The
 * {@link IconButton IconButton} class provides additional API for styling and
 * using the icon inside the button. This class still allows the Icon type in
 * case a generic node wants to be used instead of {@link Icon RtIcon}.
 * 
 * @see IconButton
 * @see mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle
 */
public class Button extends javafx.scene.control.Button implements RtStyleableComponent
{
    /**
     * Creates a raised {@code Button} with an empty string for its label.
     */
    public Button()
    {
        super();
        initialize();
    }

    /**
     * Creates a raised {@code Button} with the specified text as its label.
     *
     * @param text a text string for its label
     */
    public Button(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified text and icon for its label.
     *
     * @param text a text string for its label
     * @param graphic the icon for its label
     */
    public Button(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified style.
     * 
     * @param style the style used to change the overall look of the button
     */
    public Button(ButtonStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified style and accent
     * 
     * @param style the style type used to change the component's look
     * @param accent the accent used to change the component's color scheme
     */
    public Button(ButtonStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified label text and style
     * 
     * @param text a text string for its label
     * @param style the style type used to change the component's look
     */
    public Button(String text, ButtonStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified label text, style, and accent
     * 
     * @param text a text string for its label
     * @param style the style type used to change the component's look
     * @param accent the accent used to change the component's color scheme
     */
    public Button(String text, ButtonStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified label text, icon, and style.
     *
     * @param text a text string for its label
     * @param graphic the icon for its label
     * @param style the style type used to change the component's look
     */
    public Button(String text, Node graphic, ButtonStyle style)
    {
        super(text, graphic);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code Button} with the specified label text, icon, style, and accent.
     *
     * @param text a text string for its label.
     * @param graphic the icon for its label.
     * @param style the style type used to change the component's look.
     * @param accent the accent used to change the component's color scheme.
     */
    public Button(String text, Node graphic, ButtonStyle style, Accent accent)
    {
        super(text, graphic);
        this.style = style;
        this.accent = accent;
        initialize();
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
        getStyleClass().add(this.accent.getStyleClassName());
        for (ButtonStyle buttonStyle : ButtonStyle.values())
        {
            pseudoClassStateChanged(buttonStyle.getPseudoClass(), buttonStyle == this.style);
        }
    }

    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/
    
    private static final StyleablePropertyFactory<Button> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.Button.getClassCssMetaData());

    private static final CssMetaData<Button, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData("-rt-overlay-color",
            s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    
    /**
     * The overlay color specifies the background color used when hovering and
     * arming.
     * <p>
     * The color is added on top of the component to allow the base button color
     * to be visible when a semi-opaque overlay color is provided.
     */
    private final SimpleStyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(OVERLAY_COLOR,
            this, "overlayColor");
    
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
    
    
    private static final CssMetaData<Button, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private final SimpleStyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, this, "isAnimationDisabled");

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
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }
    
    
    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/

    private static final String USER_AGENT_STYLESHEET = "button.css";
    private static final String CSS_CLASS = "rt-button";
    
    protected ButtonStyle style = ButtonStyle.RAISED;
    protected Accent accent = Accent.PRIMARY_MID;

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadComponent(USER_AGENT_STYLESHEET));
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
     * Gets the style type of the button.
     * 
     * @return The style type of the button
     */
    public ButtonStyle getButtonStyle()
    {
        return this.style;
    }

    static
    {
        Button.loadStyleSheet();
    }
}
