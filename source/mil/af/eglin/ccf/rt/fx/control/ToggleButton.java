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
import mil.af.eglin.ccf.rt.fx.control.skins.RtToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A bi-state selection control allowing the user to toggle options.
 * <p>
 * A toggle button is typically skinned normal button, but has a selected and
 * unselected state after arming.
 */
public class ToggleButton extends javafx.scene.control.ToggleButton implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    protected IconToggleButtonStyle style = IconToggleButtonStyle.RAISED;

    private static final String USER_AGENT_STYLESHEET = "toggle-button.css";
    private static final String CSS_CLASS = "rt-toggle-button";

    private static final StyleablePropertyFactory<ToggleButton> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ToggleButton.getClassCssMetaData());

    private static final CssMetaData<ToggleButton, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData("-rt-overlay-color",
            s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<ToggleButton, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * The overlay color specifies the background color used when hovering and
     * arming.
     * <p>
     * The color is added on top of the component to allow the base button color
     * to be visible when a semi-opaque overlay color is provided.
     */
    private final SimpleStyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(OVERLAY_COLOR,
            this, "overlayColor");

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private final SimpleStyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, this, "isAnimationDisabled");

    /**
     * Creates a {@code ToggleButton} with an empty string for its label.
     */
    public ToggleButton()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public ToggleButton(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text and graphic as its label.
     * 
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     */
    public ToggleButton(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified accent.
     * 
     * @param style The style type used to change the component's look.
     */
    public ToggleButton(IconToggleButtonStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified accent.
     * 
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(IconToggleButtonStyle style, Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text and accent.
     *
     * @param text A text string for its label.
     * @param style The style type used to change the component's look.
     */
    public ToggleButton(String text, IconToggleButtonStyle style)
    {
        super(text);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text and accent.
     *
     * @param text A text string for its label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(String text, IconToggleButtonStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text and graphic as its label.
     * 
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     * @param style The style type used to change the component's look.
     */
    public ToggleButton(String text, Node graphic, IconToggleButtonStyle style)
    {
        super(text, graphic);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code ToggleButton} with the specified text and graphic as its label.
     * 
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(String text, Node graphic, IconToggleButtonStyle style, Accent accent)
    {
        super(text, graphic);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Gets the style type of the toggle button.
     * 
     * @return The style type of the toggle button
     */
    public IconToggleButtonStyle getButtonStyle()
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
        return new RtToggleButtonSkin(this);
    }

    private void initialize()
    {
        getStyleClass().clear();;
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        for (IconToggleButtonStyle buttonStyle : IconToggleButtonStyle.values())
        {
            pseudoClassStateChanged(buttonStyle.getPseudoClass(), buttonStyle == this.style);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
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
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    static
    {
        ToggleButton.loadStyleSheet();
    }
}
