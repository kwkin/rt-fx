package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ListViewStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ListView<T> extends javafx.scene.control.ListView<T> implements RtStyleableComponent
{
    // TODO change to pseudoclass
    protected ListViewStyle style = ListViewStyle.PLAIN;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "list-view.css";
    private static final String CSS_CLASS = "rt-list-view";
    
    public ListView()
    {
        super();
        initialize();
    }
    
    public ListView(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public ListView(ObservableList<T> items)
    {
        super(items);
        initialize();
    }
    
    public ListView(ObservableList<T> items, Accent accent)
    {
        super(items);
        this.accent = accent;
        initialize();
    }

    public ListView(ListViewStyle style)
    {
        super();
        this.style = style;
        initialize();
    }
    
    public ListView(Accent accent, ListViewStyle style)
    {
        super();
        this.accent = accent;
        this.style = style;
        initialize();
    }

    public ListView(ObservableList<T> items, ListViewStyle style)
    {
        super(items);
        this.style = style;
        initialize();
    }
    
    public ListView(ObservableList<T> items, Accent accent, ListViewStyle style)
    {
        super();
        this.accent = accent;
        this.style = style;
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
    public String getUserAgentStylesheet() 
    {
        return null;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
        for (ListViewStyle listStyle : ListViewStyle.values())
        {
            pseudoClassStateChanged(listStyle.getPseudoClass(), listStyle == this.style);
        }
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
        ListView.loadStyleSheet();
    }
}
