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
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtTitledSeparatorSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO add option to align the label to the left/right/top/bottom of the separator

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
 */
public class TitledSeparator extends Labeled
{
    private static final PseudoClass VERTICAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("vertical");
    private static final PseudoClass HORIZONTAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("horizontal");

    protected Accent accent = Accent.BASE;

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
    private StyleableObjectProperty<HPos> halignment = new SimpleStyleableObjectProperty<>(
            StyleableProperties.HALIGNMENT, TitledSeparator.this, "halignment", HPos.CENTER);
    private StyleableObjectProperty<VPos> valignment = new SimpleStyleableObjectProperty<>(
            StyleableProperties.VALIGNMENT, TitledSeparator.this, "valignment", VPos.CENTER);
    private StyleableDoubleProperty separatorContentGap = new SimpleStyleableDoubleProperty(
            StyleableProperties.SEPARATOR_CONTENT_GAP, TitledSeparator.this, "separatorContentGap", 0.0);

    /**
     * Creates a new horizontal separator.
     */
    public TitledSeparator()
    {
        super();
        initialize();
    }

    public TitledSeparator(String title)
    {
        super(title);
        this.setText(title);
        initialize();
    }

    public TitledSeparator(String title, Node graphic)
    {
        super(title, graphic);
        initialize();
    }

    public TitledSeparator(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public TitledSeparator(String title, Accent accent)
    {
        super(title);
        this.accent = accent;
        initialize();
    }

    public TitledSeparator(String title, Orientation orientation)
    {
        super(title);
        this.orientation.set(orientation);
        initialize();
    }

    public TitledSeparator(Orientation orientation, Accent accent)
    {
        super();
        this.accent = accent;
        this.orientation.set(orientation);
        initialize();
    }

    public TitledSeparator(String title, Orientation orientation, Accent accent)
    {
        super(title);
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

    public ObjectProperty<HPos> halignmentProperty()
    {
        return halignment;
    }

    public void setOrientation(HPos value)
    {
        halignment.set(value);
    }

    public HPos getHalignment()
    {
        return halignment.get();
    }

    public ObjectProperty<VPos> valignmentProperty()
    {
        return valignment;
    }

    public void setOrientation(VPos value)
    {
        valignment.set(value);
    }

    public VPos getValignment()
    {
        return valignment.get();
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

        private static final CssMetaData<TitledSeparator, HPos> HALIGNMENT = new CssMetaData<TitledSeparator, HPos>(
                "-fx-halignment", new EnumConverter<HPos>(HPos.class), HPos.CENTER)
        {

            @Override
            public boolean isSettable(TitledSeparator control)
            {
                return control.halignment == null || !control.halignment.isBound();
            }

            @Override
            public StyleableProperty<HPos> getStyleableProperty(TitledSeparator n)
            {
                return (StyleableProperty<HPos>) (WritableValue<HPos>) n.halignmentProperty();
            }
        };

        private static final CssMetaData<TitledSeparator, VPos> VALIGNMENT = new CssMetaData<TitledSeparator, VPos>(
                "-fx-valignment", new EnumConverter<VPos>(VPos.class), VPos.CENTER)
        {

            @Override
            public boolean isSettable(TitledSeparator n)
            {
                return n.valignment == null || !n.valignment.isBound();
            }

            @Override
            public StyleableProperty<VPos> getStyleableProperty(TitledSeparator control)
            {
                return control.valignment;
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
            styleables.add(HALIGNMENT);
            styleables.add(VALIGNMENT);
            styleables.add(SEPARATOR_CONTENT_GAP);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.STYLEABLES;
    }

    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
