package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class GridPane extends javafx.scene.layout.GridPane
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "grid-pane.css";
    private static final String CSS_CLASS = "rt-grid-pane";

    public GridPane()
    {
        super();
        initialize();
    }

    public GridPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}`
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
