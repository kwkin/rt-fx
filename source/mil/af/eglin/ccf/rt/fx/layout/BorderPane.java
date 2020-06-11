package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO implement dialog pane
public class BorderPane extends javafx.scene.layout.BorderPane
{
    protected Accent accent = Accent.BASE;
    
    private static final String USER_AGENT_STYLESHEET = "border-pane.css";
    private static final String CSS_CLASS = "rt-border-pane";

    public BorderPane()
    {
        super();
        initialize();
    }
    
    public BorderPane(Accent accent)
    {
        super();
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
        getStyleClass().add(this.accent.getCssName());
    }

    /**
     * Loads the user agent stylesheet specific to this layout
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        BorderPane.loadStyleSheet();
    }
}