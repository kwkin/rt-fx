package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtToggleSwitchSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A bi-state or tri-state selection control allowing the user to toggle
 * options.
 * <p>
 * A toggle switch is typically skinned as a short line with a thumb on top. The
 * thumb is positioned to the left when unselected and to the right when
 * selected.
 * <p>
 * {@link Checkbox Checkboxes) and {@link ToggleSwitch Toggleswitches} provide
 * similar behavior, but should be used in different situations. Checkboxes
 * should be use when presented a list of multiple related options, while toggle
 * switches should be used when one more independent options are present.
 */
public class ToggleSwitch extends javafx.scene.control.CheckBox implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "toggle-switch.css";
    private static final String CSS_CLASS = "rt-toggle-switch";

    private static final StyleablePropertyFactory<ToggleSwitch> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ToggleButton.getClassCssMetaData());

    private static final CssMetaData<ToggleSwitch, Number> LINE_WIDTH = FACTORY.createSizeCssMetaData(
            "-rt-line-width", s -> s.lineWidth, 22.0, false);
    private static final CssMetaData<ToggleSwitch, Number> LINE_LENGTH = FACTORY.createSizeCssMetaData(
            "-rt-line-length", s -> s.lineLength, 18.0, false);
    private static final CssMetaData<ToggleSwitch, Number> THUMB_RADIUS = FACTORY.createSizeCssMetaData(
            "-rt-thumb-radius", s -> s.thumbRadius, 18.0, false);
    private static final CssMetaData<ToggleSwitch, Paint> SELECTED_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-selected-thumb-color", s -> s.selectedThumbColor, DefaultPalette.getInstance().getAccentColor(), false);
    private static final CssMetaData<ToggleSwitch, Paint> UNSELECTED_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-unselected-thumb-color", s -> s.unselectedThumbColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<ToggleSwitch, Paint> SELECTED_LINE_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-selected-line-color", s -> s.selectedLineColor, DefaultPalette.getInstance().getLightAccentColor(), false);
    private static final CssMetaData<ToggleSwitch, Paint> UNSELECTED_LINE_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-unselected-line-color", s -> s.unselectedLineColor, DefaultPalette.getInstance().getLightBaseColor(), false);
    private static final CssMetaData<ToggleSwitch, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<ToggleSwitch, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * The width of the line in pixels
     */
    private StyleableDoubleProperty lineWidth = new SimpleStyleableDoubleProperty(LINE_WIDTH,
            ToggleSwitch.this, "lineWidth", 22.0);

    /**
     * The length of the line in pixels
     */
    private StyleableDoubleProperty lineLength = new SimpleStyleableDoubleProperty(LINE_LENGTH,
            ToggleSwitch.this, "lineLength", 18.0);

    /**
     * The radius of the thumb in pixels
     */
    private StyleableDoubleProperty thumbRadius = new SimpleStyleableDoubleProperty(THUMB_RADIUS,
            ToggleSwitch.this, "thumbRadius", 8.0);

    /**
     * The color used by the thumb when in the selected state.
     */
    private StyleableObjectProperty<Paint> selectedThumbColor = new SimpleStyleableObjectProperty<>(
            SELECTED_COLOR, ToggleSwitch.this, "selectedThumbColor");

    /**
     * The color used by the thumb when in the unselected or indeterminate
     * state.
     */
    private StyleableObjectProperty<Paint> unselectedThumbColor = new SimpleStyleableObjectProperty<>(
            UNSELECTED_COLOR, ToggleSwitch.this, "unselectedThumbColor");

    /**
     * The color used by the line when in the selected state.
     */
    private StyleableObjectProperty<Paint> selectedLineColor = new SimpleStyleableObjectProperty<>(
            SELECTED_LINE_COLOR, ToggleSwitch.this, "selectedLineColor");
    /**
     * The color used by the line when in the unselected or indeterminate state.
     */
    private StyleableObjectProperty<Paint> unselectedLineColor = new SimpleStyleableObjectProperty<>(
            UNSELECTED_LINE_COLOR, ToggleSwitch.this, "unselectedLineColor");

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the toggle switch.
     * <p>
     * The color is added on top of the button to allow the base button color to
     * be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            OVERLAY_COLOR, ToggleSwitch.this, "overlayColor");

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, ToggleSwitch.this, "disableAnimation");

    /**
     * Creates a toggle switch with an empty string for its label.
     */
    public ToggleSwitch()
    {
        super();
        initialize();
    }

    /**
     * Creates a toggle switch with the specified accent.
     * 
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public ToggleSwitch(Accent accent)
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
    public ToggleSwitch(String text)
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
    public ToggleSwitch(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public DoubleProperty lineWidthProperty()
    {
        return this.lineWidth;
    }

    public double getLineWidth()
    {
        return this.lineWidth.get();
    }

    public void setLineWidth(double lineWidth)
    {
        this.lineWidth.set(lineWidth);
    }

    public DoubleProperty lineLengthProperty()
    {
        return this.lineLength;
    }

    public double getLineLength()
    {
        return this.lineLength.get();
    }

    public void setLineLength(double lineLength)
    {
        this.lineLength.set(lineLength);
    }

    public DoubleProperty thumbRadiusProperty()
    {
        return this.thumbRadius;
    }

    public double getThumbRadius()
    {
        return this.thumbRadius.get();
    }

    public void setThumbRadius(double thumbRadius)
    {
        this.thumbRadius.set(thumbRadius);
    }

    public ObjectProperty<Paint> selectedColorProperty()
    {
        return this.selectedThumbColor;
    }

    public Paint getSelectedThumbColor()
    {
        return selectedThumbColor.get();
    }

    public void setSelectedColor(Paint color)
    {
        this.selectedThumbColor.set(color);
    }

    public ObjectProperty<Paint> unselectedColorProperty()
    {
        return this.unselectedThumbColor;
    }

    public Paint getUnselectedThumbColor()
    {
        return unselectedThumbColor.get();
    }

    public void setUnselectedColor(Paint color)
    {
        this.unselectedThumbColor.set(color);
    }

    public ObjectProperty<Paint> selectedLineColorProperty()
    {
        return this.selectedLineColor;
    }

    public Paint getSelectedLineColor()
    {
        return selectedLineColor.get();
    }

    public void setSelectedLineColor(Paint color)
    {
        this.selectedLineColor.set(color);
    }

    public StyleableObjectProperty<Paint> unselectedLineColorProperty()
    {
        return this.unselectedLineColor;
    }

    public Paint getUnselectedLineColor()
    {
        return unselectedLineColor.get();
    }

    public void setUnselectedLineColor(Paint color)
    {
        this.unselectedLineColor.set(color);
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
        return new RtToggleSwitchSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
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
        ToggleSwitch.loadStyleSheet();
    }
}
