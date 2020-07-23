package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtTitledSeparatorSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A horizontal or vertical separator line with a label.
 * <p>
 * The label can contain text and/or a graphic. The separator can be oriented
 * horizontally or vertically.
 * <p>
 * A titled separator will belong to one of two mutually exclusive
 * pseudo-classes: "horizontal" or "vertical". The "horizontal pseudo-class
 * applies if the separator is horizontal, and the "vertical" pseudo-class
 * applies if the separator is vertical.
 * <p>
 * The label for the titled separator may be positioned using {@code Pos}.
 * Specifically, the label can be vertically placed at the top, center, or
 * bottom of the component. The label may also be horizontally placed at the
 * left, center, or right of the component. When centered, a gap will be added
 * between the label and the separators on each side equal to the
 * {@code SeparatorContentGap}.
 */
public class TitledSeparator extends Labeled
{
    protected Accent accent = Accent.BASE;

    private static final PseudoClass VERTICAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("vertical");
    private static final PseudoClass HORIZONTAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("horizontal");

    private static final String USER_AGENT_STYLESHEET = "titled-separator.css";
    private static final String CSS_CLASS = "rt-titled-separator";

    private static final StyleablePropertyFactory<TitledSeparator> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.Labeled.getClassCssMetaData());

    private static final CssMetaData<TitledSeparator, Orientation> ORIENTATION = FACTORY
            .createEnumCssMetaData(Orientation.class, "-fx-orientation", s -> s.orientation, Orientation.HORIZONTAL, false);
    private static final CssMetaData<TitledSeparator, Pos> TITLE_ALIGNMENT = FACTORY
            .createEnumCssMetaData(Pos.class, "-fx-title-alignment", s -> s.titleAlignment, Pos.CENTER, false);
    private static final CssMetaData<TitledSeparator, Number> SEPARATOR_CONTENT_GAP = 
            FACTORY.createSizeCssMetaData("-rt-separator-content-gap", s -> s.separatorContentGap, 0, false);

    /**
     * The orientation property indicates if the {@code TitledSeparator} is
     * horizontal or vertical
     */
    private StyleableObjectProperty<Orientation> orientation = new SimpleStyleableObjectProperty<Orientation>(
            ORIENTATION, this, "orientation", Orientation.HORIZONTAL)
    {
        @Override
        protected void invalidated()
        {
            final boolean isVertical = (get() == Orientation.VERTICAL);
            pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, isVertical);
            pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, !isVertical);
        }
    };

    /**
     * The position of the title
     * <p>
     * The label can be vertically placed at the top, center, or bottom of the
     * component. The label may also be horizontally placed at the left, center,
     * or right of the component. When centered, a gap will be added between the
     * label and the separators on each side equal to the
     */
    private StyleableObjectProperty<Pos> titleAlignment = new SimpleStyleableObjectProperty<>(
            TITLE_ALIGNMENT, this, "titleAlignment");

    /**
     * Returns the gap property used to pad the label
     * <p>
     * When the label is centered, the gap is used to specify the white space
     * between the label and the separator lines. When the label is positioned
     * to the left or right, the gap will specify the white space between the
     * label and the edge of the component.
     */
    private StyleableDoubleProperty separatorContentGap = new SimpleStyleableDoubleProperty(
            SEPARATOR_CONTENT_GAP, this, "separatorContentGap");

    /**
     * Creates a horizontal {@code Separator} with no text or graphic.
     */
    public TitledSeparator()
    {
        super();
        initialize();
    }

    /**
     * Creates a horizontal {@code Separator} with text
     * 
     * @param text The title of the separator
     */
    public TitledSeparator(String text)
    {
        super(text);
        this.setText(text);
        initialize();
    }

    /**
     * Creates a horizontal {@code Separator} with text and graphic
     * 
     * @param text The title of the separator
     * @param graphic The graphic for the separator label
     */
    public TitledSeparator(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates an horizontal {@code Separator} styled with the provided accent
     * 
     * @param accent The accent of the separator
     */
    public TitledSeparator(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a horizontal {@code Separator} with text and styled with the
     * provided accent
     * 
     * @param text The text of the separator
     * @param accent The accent of the separator
     */
    public TitledSeparator(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code Separator} with the specified text and orientation
     * 
     * @param text The text of the separator
     * @param orientation The orientation of the separator lines
     */
    public TitledSeparator(String text, Orientation orientation)
    {
        super(text);
        this.orientation.set(orientation);
        initialize();
    }

    /**
     * Creates a {@code Separator} with the specified accent and orientation
     * 
     * @param orientation The orientation of the separator lines
     * @param accent The accent of the separator
     */
    public TitledSeparator(Orientation orientation, Accent accent)
    {
        super();
        this.accent = accent;
        this.orientation.set(orientation);
        initialize();
    }

    /**
     * Creates a {@code Separator} with the specified text, accent, and
     * orientation
     * 
     * @param text The text of the separator
     * @param orientation The orientation of the separator lines
     * @param accent The accent of the separator
     */
    public TitledSeparator(String text, Orientation orientation, Accent accent)
    {
        super(text);
        this.accent = accent;
        this.orientation.set(orientation);
        initialize();
    }

    public ObjectProperty<Orientation> orientationProperty()
    {
        return orientation;
    }

    public void setOrientation(Orientation value)
    {
        orientation.set(value);
    }

    public Orientation getOrientation()
    {
        return orientation.get();
    }

    public ObjectProperty<Pos> titleAlignmentProperty()
    {
        return titleAlignment;
    }

    public void setTitleAlignment(Pos value)
    {
        titleAlignment.set(value);
    }

    public Pos getTitlealignment()
    {
        return titleAlignment.get();
    }

    public DoubleProperty separatorContentGapProperty()
    {
        return separatorContentGap;
    }

    public void setSeparatorContentGap(double value)
    {
        separatorContentGap.set(value);
    }

    public Double getSparatorContentGap()
    {
        return separatorContentGap.get();
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
        return new RtTitledSeparatorSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        setContentDisplay(ContentDisplay.LEFT);

        pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, orientation.getValue() != Orientation.VERTICAL);
        pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, orientation.getValue() == Orientation.VERTICAL);
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
        TitledSeparator.loadStyleSheet();
    }
}
