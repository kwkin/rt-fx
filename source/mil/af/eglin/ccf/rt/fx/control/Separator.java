package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Orientation;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A horizontal or vertical separator line.
 */
public class Separator extends javafx.scene.control.Separator implements RtStyleableComponent
{
    /**
     * Creates a horizontal {@code Separator}.
     */
    public Separator()
    {
        super();
        initialize();
    }

    /**
     * Creates a new {@code Separator} with the provided orientation.
     * 
     * @param orientation the orientation used to specify a horizontal or vertical separator
     */
    public Separator(Orientation orientation)
    {
        super(orientation);
        initialize();
    }


    /**
     * Creates a horizontal {@code Separator} with the provided accent.
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public Separator(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a horizontal {@code Separator} with the provided orientation and accent.
     * 
     * @param orientation the orientation used to specify a horizontal or vertical separator
     * @param accent the accent used to change the component's color scheme
     */
    public Separator(Orientation orientation, Accent accent)
    {
        super(orientation);
        this.accent = accent;
        initialize();
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<Separator> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.Separator.getClassCssMetaData());


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
    
    private static final String USER_AGENT_STYLESHEET = "separator.css";
    private static final String CSS_CLASS = "rt-separator";

    protected Accent accent = Accent.BASE;
    
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
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }
    
    static
    {
        Separator.loadStyleSheet();
    }
}
