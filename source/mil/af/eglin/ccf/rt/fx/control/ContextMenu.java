package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.MenuItem;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A popup control containing a list of menu items.
 */
public class ContextMenu extends javafx.scene.control.ContextMenu implements RtStyleableComponent
{
    private static final String USER_AGENT_STYLESHEET = "context-menu.css";
    private static final String CSS_CLASS = "rt-context-menu";

    private Accent accent = Accent.PRIMARY_MID;

    private static final StyleablePropertyFactory<ContextMenu> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ContextMenu.getClassCssMetaData());

    /**
     * Creates an empty {@code ContextMenu}
     */
    public ContextMenu()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code ContextMenu} with the provided items
     * 
     * @param items the items used to fill the context menu
     */
    public ContextMenu(MenuItem... items)
    {
        super(items);
        initialize();
    }

    /**
     * Creates a {@code ContextMenu} with the provided accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public ContextMenu(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code ContextMenu} with the provided accent and items
     * 
     * @param items the items used to fill the context menu
     * @param accent the accent used to change the component's color scheme
     */
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
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
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
        ContextMenu.loadStyleSheet();
    }
}
