package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Tooltip extends javafx.scene.control.Tooltip
{
    private static final String USER_AGENT_STYLESHEET = "tool-tip.css";
    private static final String CSS_CLASS = "rt-tool-tip";

    public Tooltip()
    {
        super();
        initialize();
    }
    
    public Tooltip(String text)
    {
        super(text);
        initialize();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
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
        Tooltip.loadStyleSheet();
    }
}
