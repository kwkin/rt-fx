package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class StackPane extends javafx.scene.layout.StackPane
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "stack-pane.css";
    private static final String CSS_CLASS = "rt-stack-pane";

    /**
     * Creates a StackPane layout with default CENTER alignment.
     */
    public StackPane()
    {
        super();
        initialize();
    }

    public StackPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a StackPane layout with default CENTER alignment.
     * 
     * @param children The initial set of children for this pane.
     */
    public StackPane(Node... children)
    {
        super();
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
            getStyleClass().add(this.accent.getCssName());
        }
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadLayouts(USER_AGENT_STYLESHEET));
    }
}