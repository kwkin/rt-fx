package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.MenuItem;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ContextMenu extends javafx.scene.control.ContextMenu implements RtComponent
{
    private static final String USER_AGENT_STYLESHEET = "context-menu.css";
    private static final String CSS_CLASS = "rt-context-menu";
    
    private Accent accent = Accent.PRIMARY_MID;

    public ContextMenu()
    {
        super();
        initialize();
    }
    
    public ContextMenu(MenuItem... items)
    {
        super(items);
        initialize();
    }

    public ContextMenu(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ContextMenu(Accent accent, MenuItem... items)
    {
        super(items);
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
