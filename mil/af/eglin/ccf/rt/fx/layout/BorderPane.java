package mil.af.eglin.ccf.rt.fx.layout;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO implement dialog pane
public class BorderPane extends javafx.scene.layout.BorderPane
{
    protected Accent accent;
    
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
        return ResourceLoader.loadLayouts(USER_AGENT_STYLESHEET);
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        if (this.accent != null)
        {
            getStyleClass().add(this.accent.getCssName());
        }
    }
}