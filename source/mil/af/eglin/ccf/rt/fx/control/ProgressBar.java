package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtProgressBarSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ProgressBar extends javafx.scene.control.ProgressBar implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "progress-bar.css";
    private static final String CSS_CLASS = "rt-progress-bar";
    
    public ProgressBar()
    {
        super();
        initialize();
    }
    
    public ProgressBar(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public ProgressBar(double progress)
    {
        super(progress);
        initialize();
    }

    public ProgressBar(double progress, Accent accent)
    {
        super(progress);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtProgressBarSkin(this);
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
        return null;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
