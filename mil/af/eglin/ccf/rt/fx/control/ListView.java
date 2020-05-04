package mil.af.eglin.ccf.rt.fx.control;

import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ListViewStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ListView<T> extends javafx.scene.control.ListView<T> implements RtComponent
{
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
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }
}
