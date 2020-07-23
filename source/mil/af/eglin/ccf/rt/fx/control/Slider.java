package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtSliderSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A slider allow users to make a selection from a range of values.
 * <p>
 * A slider displays a continuous or discrete range of values along a track, and
 * values are selected by dragging a thumb over the track.
 */
public class Slider extends javafx.scene.control.Slider implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "slider.css";
    private static final String CSS_CLASS = "rt-slider";

    private static final StyleablePropertyFactory<Slider> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.Slider.getClassCssMetaData());

    private static final CssMetaData<Slider, Paint> THUMB_COLOR = FACTORY.createPaintCssMetaData("-rt-thumb-color",
            s -> s.thumbColor, DefaultPalette.getInstance().getAccentColor(), false);
    private static final CssMetaData<Slider, Paint> FILLED_TRACK_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-filled-track-color", s -> s.filledTrackColor, DefaultPalette.getInstance().getAccentColor(), false);
    private static final CssMetaData<Slider, Paint> UNFILLED_TRACK_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-unfilled-track-color", s -> s.unfilledTrackColor, DefaultPalette.getInstance().getLightBaseColor(),
            false);
    private static final CssMetaData<Slider, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData("-rt-overlay-color",
            s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<Slider, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * The color of the thumb.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableObjectProperty<Paint> thumbColor = new SimpleStyleableObjectProperty<>(THUMB_COLOR, this,
            "thumbColor");

    /**
     * The filled track color specifies the color of the track from the minimum
     * end of the slider to the thumb.
     */
    private StyleableObjectProperty<Paint> filledTrackColor = new SimpleStyleableObjectProperty<>(FILLED_TRACK_COLOR,
            this, "filledTrackColor");

    /**
     * The unfilled track color specifies the color of the track from the thumb
     * to the maximum end of the slider.
     */
    private StyleableObjectProperty<Paint> unfilledTrackColor = new SimpleStyleableObjectProperty<>(
            UNFILLED_TRACK_COLOR, this, "unfilledTrackColor");

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the toggle switch.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(OVERLAY_COLOR, this,
            "overlayColor");

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(DISABLE_ANIMATION, this,
            "disableAnimation");

    /**
     * Creates a default slider instance.
     */
    public Slider()
    {
        super();
        initialize();
    }

    /**
     * Creates a default slider instance with the specified accent.
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public Slider(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Constructs a Slider control with the specified slider min, max and
     * current value values.
     * 
     * @param min Slider minimum value.
     * @param max Slider maximum value.
     * @param value Slider current value.
     */
    public Slider(double min, double max, double value)
    {
        super(min, max, value);
        initialize();
    }

    /**
     * Constructs a Slider control with the specified slider min, max and
     * current value values.
     * 
     * @param min Slider minimum value.
     * @param max Slider maximum value.
     * @param value Slider current value.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Slider(double min, double max, double value, Accent accent)
    {
        super(min, max, value);
        this.accent = accent;
        initialize();
    }

    public final StyleableObjectProperty<Paint> thumbColorProperty()
    {
        return this.thumbColor;
    }

    public final Paint getThumbColor()
    {
        return this.thumbColor.get();
    }

    public final void setThumbColor(Paint color)
    {
        this.thumbColor.set(color);
    }

    public final StyleableObjectProperty<Paint> filledTrackColorProperty()
    {
        return this.filledTrackColor;
    }

    public final Paint getFilledTrackColor()
    {
        return filledTrackColor.get();
    }

    public final void setFilledTrackColor(Paint color)
    {
        this.filledTrackColor.set(color);
    }

    public final StyleableObjectProperty<Paint> unfilledTrackColorProperty()
    {
        return this.unfilledTrackColor;
    }

    public final Paint getUnfilledTrackColor()
    {
        return unfilledTrackColor.get();
    }

    public final void setUnfilledTrackColor(Paint color)
    {
        this.unfilledTrackColor.set(color);
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
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtSliderSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
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
        Slider.loadStyleSheet();
    }
}
