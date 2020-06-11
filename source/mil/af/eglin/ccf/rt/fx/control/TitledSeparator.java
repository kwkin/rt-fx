package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.css.converters.SizeConverter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.SimpleStyleableDoubleProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
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

    private StyleableObjectProperty<Orientation> orientation = new SimpleStyleableObjectProperty<Orientation>(
            StyleableProperties.ORIENTATION, TitledSeparator.this, "orientation", Orientation.HORIZONTAL)
    {
        @Override
        protected void invalidated()
        {
            final boolean isVertical = (get() == Orientation.VERTICAL);
            pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, isVertical);
            pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, !isVertical);
        }
    };
    private StyleableObjectProperty<Pos> titleAlignment = new SimpleStyleableObjectProperty<>(
            StyleableProperties.TITLE_ALIGNMENT, TitledSeparator.this, "titleAlignment", Pos.CENTER);
    private StyleableDoubleProperty separatorContentGap = new SimpleStyleableDoubleProperty(
            StyleableProperties.SEPARATOR_CONTENT_GAP, TitledSeparator.this, "separatorContentGap", 0.0);

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
     * @param text
     *            The title of the separator
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
     * @param text
     *            The title of the separator
     * @param graphic
     *            The graphic for the separator label
     */
    public TitledSeparator(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates an horizontal {@code Separator} styled with the provided accent
     * 
     * @param accent
     *            The accent of the separator
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
     * @param text
     *            The text of the separator
     * @param accent
     *            The accent of the separator
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
     * @param text
     *            The text of the separator
     * @param orientation
     *            The orientation of the separator lines
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
     * @param orientation
     *            The orientation of the separator lines
     * @param accent
     *            The accent of the separator
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
     * @param text
     *            The text of the separator
     * @param orientation
     *            The orientation of the separator lines
     * @param accent
     *            The accent of the separator
     */
    public TitledSeparator(String text, Orientation orientation, Accent accent)
    {
        super(text);
        this.accent = accent;
        this.orientation.set(orientation);
        initialize();
    }

    /**
     * Returns the orientation property indicating if the {@code Separator} is
     * horizontal or vertical
     * 
     * @return The orientation property
     */
    public ObjectProperty<Orientation> orientationProperty()
    {
        return orientation;
    }

    /**
     * Sets the orientation of the {@code Separator} lines
     * 
     * @param value
     *            The orientation of the {@code Separator} lines
     */
    public void setOrientation(Orientation value)
    {
        orientation.set(value);
    }

    /**
     * Returns the orientation of the {@code Separator} lines
     * 
     * @return The orientation of the {@code Separator} lines
     */
    public Orientation getOrientation()
    {
        return orientation.get();
    }

    /**
     * Returns the position property of the title
     * <p>
     * The label can be vertically placed at the top, center, or bottom of the
     * component. The label may also be horizontally placed at the left, center,
     * or right of the component. When centered, a gap will be added between the
     * label and the separators on each side equal to the
     * 
     * @return value The position property label
     */
    public ObjectProperty<Pos> titleAlignmentProperty()
    {
        return titleAlignment;
    }

    /**
     * Sets the position of the title
     * <p>
     * The label can be vertically placed at the top, center, or bottom of the
     * component. The label may also be horizontally placed at the left, center,
     * or right of the component. When centered, a gap will be added between the
     * label and the separators on each side equal to the
     * 
     * @param value
     *            The position of the title
     */
    public void setTitleAlignment(Pos value)
    {
        titleAlignment.set(value);
    }

    /**
     * Returns the position of the title
     * <p>
     * The label can be vertically placed at the top, center, or bottom of the
     * component. The label may also be horizontally placed at the left, center,
     * or right of the component. When centered, a gap will be added between the
     * label and the separators on each side equal to the
     * 
     * @return value The position property label
     */
    public Pos getTitlealignment()
    {
        return titleAlignment.get();
    }

    /**
     * Returns the gap property used to pad the label
     * <p>
     * When the label is centered, the gap is used to specify the white space
     * between the label and the separator lines. When the label is positioned
     * to the left or right, the gap will specify the white space between the
     * label and the edge of the component.
     * 
     * @return The gap property used to pad the label
     */
    public DoubleProperty separatorContentGapProperty()
    {
        return separatorContentGap;
    }

    /**
     * Sets the gap used to pad the label
     * <p>
     * When the label is centered, the gap is used to specify the white space
     * between the label and the separator lines. When the label is positioned
     * to the left or right, the gap will specify the white space between the
     * label and the edge of the component.
     * 
     * @param value
     *            The gap used to pad the label
     */
    public void setSeparatorContentGap(double value)
    {
        separatorContentGap.set(value);
    }

    /**
     * Returns the gap used to pad the label
     * <p>
     * When the label is centered, the gap is used to specify the white space
     * between the label and the separator lines. When the label is positioned
     * to the left or right, the gap will specify the white space between the
     * label and the edge of the component.
     * 
     * @return The gap used to pad the label
     */
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
    protected Skin<?> createDefaultSkin()
    {
        return new RtTitledSeparatorSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
        setContentDisplay(ContentDisplay.LEFT);

        pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, orientation.getValue() != Orientation.VERTICAL);
        pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, orientation.getValue() == Orientation.VERTICAL);
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<TitledSeparator, Orientation> ORIENTATION = new CssMetaData<TitledSeparator, Orientation>(
                "-fx-orientation", new EnumConverter<Orientation>(Orientation.class), Orientation.HORIZONTAL)
        {

            @Override
            public Orientation getInitialValue(TitledSeparator node)
            {
                return node.getOrientation();
            }

            @Override
            public boolean isSettable(TitledSeparator control)
            {
                return control.orientation == null || !control.orientation.isBound();
            }

            @Override
            public StyleableProperty<Orientation> getStyleableProperty(TitledSeparator n)
            {
                return (StyleableProperty<Orientation>) (WritableValue<Orientation>) n.orientationProperty();
            }
        };

        private static final CssMetaData<TitledSeparator, Pos> TITLE_ALIGNMENT = new CssMetaData<TitledSeparator, Pos>(
                "-fx-title-alignment", new EnumConverter<Pos>(Pos.class), Pos.CENTER)
        {

            @Override
            public boolean isSettable(TitledSeparator control)
            {
                return control.titleAlignment == null || !control.titleAlignment.isBound();
            }

            @Override
            public StyleableProperty<Pos> getStyleableProperty(TitledSeparator n)
            {
                return (StyleableProperty<Pos>) (WritableValue<Pos>) n.titleAlignment;
            }
        };

        private static final CssMetaData<TitledSeparator, Number> SEPARATOR_CONTENT_GAP = new CssMetaData<TitledSeparator, Number>(
                "-rt-separator-content-gap", SizeConverter.getInstance(), 0)
        {

            @Override
            public boolean isSettable(TitledSeparator control)
            {
                return control.separatorContentGap == null || !control.separatorContentGap.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(TitledSeparator control)
            {
                return control.separatorContentGap;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<CssMetaData<? extends Styleable, ?>>(
                    Labeled.getClassCssMetaData());
            styleables.add(ORIENTATION);
            styleables.add(TITLE_ALIGNMENT);
            styleables.add(SEPARATOR_CONTENT_GAP);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
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
     * Returns the list of available CSS properties
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.STYLEABLES;
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
