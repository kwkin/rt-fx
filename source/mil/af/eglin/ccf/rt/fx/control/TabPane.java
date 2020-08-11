package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Tab;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A control that allows switching between a group of {@link Tab Tabs}. Only one
 * tab may be visible at a time.
 */
public class TabPane extends javafx.scene.control.TabPane implements RtStyleableComponent
{

    /**
     * Creates a {@code TabPane} with no tabs
     */
    public TabPane()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code TabPane} with the provided tabs
     * 
     * @param tabs the tabs to display inside the tab pane
     */
    public TabPane(Tab... tabs)
    {
        super(tabs);
        initialize();
    }

    /**
     * Creates a {@code TabPane} with the provided accent and no tabs
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public TabPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code TabPane} with the provided accent and tabs
     * 
     * @param accent the accent used to change the component's color scheme
     * @param tabs the tabs to display inside the tab pane
     */
    public TabPane(Accent accent, Tab... tabs)
    {
        super(tabs);
        this.accent = accent;
        initialize();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<TabPane> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.TabPane.getClassCssMetaData());
    
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
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/

    private static final String USER_AGENT_STYLESHEET = "tab-pane.css";
    private static final String CSS_CLASS = "rt-tab-pane";
    
    protected Accent accent = Accent.PRIMARY_MID;

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
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
    public Accent getAccent()
    {
        return this.accent;
    }

    static
    {
        TabPane.loadStyleSheet();
    }
}
