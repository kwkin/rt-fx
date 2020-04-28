package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.control.Tab;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TabPane extends javafx.scene.control.TabPane implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "tab-pane.css";
    private static final String CSS_CLASS = "rt-tab-pane";
    
    public TabPane()
    {
        super();
        initialize();
    }
    
    public TabPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public TabPane(Tab... tabs)
    {
        super(tabs);
        initialize();
    }

    public TabPane(Accent accent, Tab... tabs)
    {
        super(tabs);
        this.accent = accent;
        initialize();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
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
        getStyleClass().add(this.accent.getCssName());
    }
}
