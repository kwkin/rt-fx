package mil.af.eglin.ccf.rt.fx.control;

import javafx.geometry.Orientation;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Separator extends javafx.scene.control.Separator implements RtComponent
{
    protected Accent accent = Accent.BASE_MID;
    
    private static final String USER_AGENT_STYLESHEET = "separator.css";
    private static final String CSS_CLASS = "rt-separator";
    
    public Separator()
    {
        super();
        initialize();
    }
    
    public Separator(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Separator(Orientation orientation)
    {
        super(orientation);
        initialize();
    }

    public Separator(Orientation orientation, Accent accent)
    {
        super(orientation);
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
