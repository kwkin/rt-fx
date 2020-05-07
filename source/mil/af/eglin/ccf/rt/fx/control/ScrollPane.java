package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ScrollPane extends javafx.scene.control.ScrollPane
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "scroll-pane.css";
    private static final String CSS_CLASS = "rt-scroll-pane";
    
    public ScrollPane()
    {
        super();
        initialize();
    }
    
    public ScrollPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ScrollPane(Node content)
    {
        super(content);
        initialize();
    }
    
    public ScrollPane(Node content, Accent accent)
    {
        super(content);
        this.accent = accent;
        initialize();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() 
    {
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
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
