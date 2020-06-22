package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.MenuItem;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ContextMenu extends javafx.scene.control.ContextMenu implements RtStyleableComponent
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

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        ContextMenu.loadStyleSheet();
    }
}
