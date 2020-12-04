package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A simple non-editable text control.
 * <p>
 * Text is styled using one of several types specified using
 * {@link mil.af.eglin.ccf.rt.fx.control.style.TextStyle TextStyle}. By default,
 * the button will be
 * {@link mil.af.eglin.ccf.rt.fx.control.style.TextStyle#NORMAL NORMAL}.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.style.TextStyle
 */
public class Text extends javafx.scene.text.Text implements RtStyleableComponent
{
    /**
     * Creates a text with an empty string.
     */
    public Text()
    {
        super();
        initialize();
    }

    /**
     * Creates a text with the specified text.
     * 
     * @param text a text string for the text
     */
    public Text(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a text at the given coordinates with the given string.
     * 
     * @param x The horizontal position of the text.
     * @param y The vertical position of the text.
     * @param text Text to be contained in the instance.
     */
    public Text(double x, double y, String text)
    {
        super(x, y, text);
        initialize();
    }

    /**
     * Creates a text with the specified text and style.
     * 
     * @param text A text string for the text.
     * @param style The style type used to change the component's look.
     */
    public Text(String text, TextStyle style)
    {
        super(text);
        this.style = style;
        initialize();
    }

    /**
     * Creates a text with the specified text, style, and accent.
     * 
     * @param text A text string for the text.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Text(String text, TextStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a text at the given coordinates with the given string and style.
     * 
     * @param x The horizontal position of the text.
     * @param y The vertical position of the text.
     * @param text Text to be contained in the instance.
     * @param style The style type used to change the component's look.
     */
    public Text(double x, double y, String text, TextStyle style)
    {
        super(x, y, text);
        this.style = style;
        initialize();
    }


    /**
     * Creates a text at the given coordinates with the given string, style, and accent.
     * 
     * @param x The horizontal position of the text.
     * @param y The vertical position of the text.
     * @param text Text to be contained in the instance.
     * @param style The style type used to change the component's look.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public Text(double x, double y, String text, TextStyle style, Accent accent)
    {
        super(x, y, text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        for (TextStyle labelStyle : TextStyle.values())
        {
            pseudoClassStateChanged(labelStyle.getPseudoClass(), labelStyle == this.style);
        }
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<Text> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.text.Text.getClassCssMetaData());

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
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/

    private static final String USER_AGENT_STYLESHEET = "text.css";
    private static final String CSS_CLASS = "rt-text";
    
    protected TextStyle style = TextStyle.NORMAL;
    protected Accent accent = Accent.BASE;

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
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
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
        Text.loadStyleSheet();
    }
}
