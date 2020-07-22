package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.TreeItem;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A treeview provides a hierarchical and collabsable view of nodes
 * 
 * @param <T> the type of item contained within the tree view
 */
public class TreeView<T> extends javafx.scene.control.TreeView<T>
{
    private static final String USER_AGENT_STYLESHEET = "tree-view.css";
    private static final String CSS_CLASS = "rt-tree-view";

    protected Accent accent = Accent.PRIMARY_MID;

    private static final StyleablePropertyFactory<ToggleButton> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ToggleButton.getClassCssMetaData());
    
    /**
     * Creates an empty {@code TreeView}
     */
    public TreeView()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code Treeview} with the provided node as its root
     * @param root
     */
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
        getStyleClass().add(this.accent.getStyleClassName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return FACTORY.getCssMetaData();
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
        TreeView.loadStyleSheet();
    }
}
