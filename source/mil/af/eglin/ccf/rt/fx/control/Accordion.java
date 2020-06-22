package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Accordion extends javafx.scene.control.Accordion
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "accordion.css";
    private static final String CSS_CLASS = "rt-accordion";
    
    public Accordion()
    {
        super();
        initialize();
    }

    public Accordion(TitledPane... titledPanes)
    {
        super(titledPanes);
        initialize();
    }
    
    public Accordion(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Accordion(Accent accent, TitledPane... titledPanes)
    {
        super(titledPanes);
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

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        Accordion.loadStyleSheet();
    }
}
