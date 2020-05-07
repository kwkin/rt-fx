package mil.af.eglin.ccf.rt.fx.layout;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TextFlow extends javafx.scene.text.TextFlow
{
    protected Accent accent;
    
    private static final String USER_AGENT_STYLESHEET = "text-flow.css";
    private static final String CSS_CLASS = "rt-text-flow";

    public TextFlow()
    {
        super();
        initialize();
    }

    public TextFlow(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public TextFlow(Node... children)
    {
        super(children);
        initialize();
    }

    public TextFlow(Accent accent, Node... children)
    {
        super(children);
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
