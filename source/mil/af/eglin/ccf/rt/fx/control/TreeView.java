package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.TreeItem;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TreeView<T> extends javafx.scene.control.TreeView<T>
{
    private static final String USER_AGENT_STYLESHEET = "tree-view.css";
    private static final String CSS_CLASS = "rt-tree-view";

    protected Accent accent = Accent.PRIMARY_MID;
    
    public TreeView()
    {
        super();
        initialize();
    }

    public TreeView(TreeItem<T> root)
    {
        super(root);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
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
