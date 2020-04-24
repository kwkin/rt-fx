package mil.af.eglin.ccf.rt.fx.layout;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class CardPane extends VBox
{
    protected Accent accent;
    
    private static final String USER_AGENT_STYLESHEET = "card-pane.css";
    private static final String CSS_CLASS = "rt-card-pane";

    public CardPane()
    {
        super();
        initialize();
    }

    public CardPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}`
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
