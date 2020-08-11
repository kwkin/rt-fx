package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A control that provides a scrolled, clipped viewport of its contents.
 */
public class ScrollPane extends javafx.scene.control.ScrollPane
{
    /**
     * Creates an empty {@code ScrollPane}
     */
    public ScrollPane()
    {
        super();
        initialize();
    }

    /**
     * Creates an empty {@code ScrollPane} with the provided accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public ScrollPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }


    /**
     * Creates an empty {@code ScrollPane} with the provided content and accent
     * 
     * @param content the node to set as the scroll pane's content
     */
    public ScrollPane(Node content)
    {
        super(content);
        initialize();
    }

    /**
     * Creates an empty {@code ScrollPane} with the provided content and accent
     * 
     * @param content the node to set as the scroll pane's content
     * @param accent the accent used to change the component's color scheme
     */
    public ScrollPane(Node content, Accent accent)
    {
        super(content);
        this.accent = accent;
        initialize();
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        if (this.accent != null)
        {
            getStyleClass().add(this.accent.getStyleClassName());
        }
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<ScrollPane> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.ScrollPane.getClassCssMetaData());

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

    private static final String USER_AGENT_STYLESHEET = "scroll-pane.css";
    private static final String CSS_CLASS = "rt-scroll-pane";
    
    protected Accent accent = Accent.PRIMARY_MID;

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
    
    static
    {
        ScrollPane.loadStyleSheet();
    }
}
