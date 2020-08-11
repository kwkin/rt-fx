package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A simple non-editable text control.
 * <p>
 * Labels are styled using one of several types specified using
 * {@link mil.af.eglin.ccf.rt.fx.control.style.LabelStyle LabelStyle}. By
 * default, the label will be
 * {@link mil.af.eglin.ccf.rt.fx.control.style.LabelStyle#NORMAL NORMAL}.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.style.LabelStyle
 */
public class Label extends javafx.scene.control.Label implements RtStyleableComponent
{
    protected LabelStyle style = LabelStyle.NORMAL;
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "label.css";
    private static final String CSS_CLASS = "rt-label";
    
    /**
     * Creates a {@code Label} with an empty string.
     */
    public Label()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text.
     * 
     * @param text A text string for the label.
     */
    public Label(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text and icon.
     *
     * @param text A text string for the label.
     * @param graphic The icon for the label.
     */
    public Label(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text and style.
     * 
     * @param text A text string for the label.
     * @param style The style type used to change the component's look.
     */
    public Label(String text, LabelStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text, style, and accent.
     * 
     * @param text A text string for the label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Label(String text, LabelStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text, icon, and style.
     *
     * @param text A text string for the label.
     * @param graphic The icon for the label.
     * @param style The style type used to change the component's look.
     */
    public Label(String text, Node graphic, LabelStyle style)
    {
        super(text, graphic);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code Label} with the specified text, icon, style, and accent.
     *
     * @param text A text string for the label.
     * @param graphic The icon for the label.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Label(String text, Node graphic, LabelStyle style, Accent accent)
    {
        super(text, graphic);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        for (LabelStyle labelStyle : LabelStyle.values())
        {
            pseudoClassStateChanged(labelStyle.getPseudoClass(), labelStyle == this.style);
        }
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<Label> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.Label.getClassCssMetaData());

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

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
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
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }
    
    /**
     * Gets the style type of the label.
     * 
     * @return The style type of the label.
     */
    public LabelStyle getLabelStyle()
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

    static
    {
        Label.loadStyleSheet();
    }
}
