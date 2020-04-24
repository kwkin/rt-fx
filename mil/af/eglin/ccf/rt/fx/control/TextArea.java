package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TextArea extends javafx.scene.control.TextArea implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "text-area.css";
    private static final String CSS_CLASS = "rt-text-area";
    
    public TextArea()
    {
        super();
        initialize();
    }
    
    public TextArea(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public TextArea(String text)
    {
        super(text);
        initialize();
    }
    
    public TextArea(String text, Accent accent)
    {
        super(text);
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
