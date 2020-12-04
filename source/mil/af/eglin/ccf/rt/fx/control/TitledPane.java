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
 * A titled pane is a panel with a titled that can be opened and closed
 */
public class TitledPane extends javafx.scene.control.TitledPane
{
    /**
     * Creates a {@code TitledPane} with no title or content
     */
    public TitledPane()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code TitledPane} with the provided title and content
     * 
     * @param title the title of the TitledPane
     * @param content the content of the TitledPane
     */
    public TitledPane(String title, Node content)
    {
        super(title, content);
        initialize();
    }


    /**
     * Creates a {@code TitledPane} with the provided accent and no title or content
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public TitledPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code TitledPane} with the provided title, content, and accent
     * 
     * @param title the title of the TitledPane
     * @param content the content of the TitledPane
     * @param accent the accent used to change the component's color scheme
     */
    public TitledPane(String title, Node content, Accent accent)
    {
        super(title, content);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
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

    private static final StyleablePropertyFactory<TextField> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.TitledPane.getClassCssMetaData());
    
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

    private static final String USER_AGENT_STYLESHEET = "titled-pane.css";
    private static final String CSS_CLASS = "rt-titled-pane";

    protected Accent accent;

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        TitledPane.loadStyleSheet();
    }
}
