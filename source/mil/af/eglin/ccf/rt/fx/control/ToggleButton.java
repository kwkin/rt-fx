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
import mil.af.eglin.ccf.rt.fx.control.skins.RtToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
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

    private static final String USER_AGENT_STYLESHEET = "toggle-button.css";
    private static final String CSS_CLASS = "rt-toggle-button";

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, ToggleButton.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());
    
    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, ToggleButton.this, "disableAnimation", false);

    /**
     * Creates a toggle button with an empty string for its label.
     */
    public ToggleButton()
    {
        super();
        initialize();
    }

    /**
     * Creates a toggle button with the specified accent.
     * 
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a toggle switch with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public ToggleButton(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a toggle switch with the specified text and accent.
     *
     * @param text A text string for its label.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a toggle switch with the specified text and graphic as its label.
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
     * Creates a toggle switch with the specified text and graphic as its label.
     * 
     * @param text A text string for its label.
     * @param graphic The icon for its label.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleButton(String text, Node graphic, Accent accent)
    {
        super(text, graphic);
        this.accent = accent;
        initialize();
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
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<ToggleButton, Paint> OVERLAY_COLOR = new CssMetaData<ToggleButton, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(ToggleButton control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ToggleButton control)
            {
                return control.overlayColor;
            }
        };
        
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
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.CheckBox.getClassCssMetaData());
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
