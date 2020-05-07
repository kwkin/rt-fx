package mil.af.eglin.ccf.rt.fx.control;

import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TableViewStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TableView<S> extends javafx.scene.control.TableView<S>
{
    private static final String USER_AGENT_STYLESHEET = "table-view.css";
    private static final String CSS_CLASS = "rt-table-view";
    
    protected TableViewStyle style = TableViewStyle.ZEBRA;
    protected Accent accent = Accent.PRIMARY_MID;
    
    public TableView()
    {
        super();
        initialize();
    }
    
    public TableView(ObservableList<S> items)
    {
        super(items);
        initialize();
    }

    public TableView(TableViewStyle style)
    {
        super();
        this.style = style;
        initialize();
    }
    
    public TableView(TableViewStyle style, ObservableList<S> items)
    {
        super(items);
        this.style = style;
        initialize();
    }

    public TableView(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public TableView(Accent accent, ObservableList<S> items)
    {
        super(items);
        this.accent = accent;
        initialize();
    }

    public TableView(TableViewStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public TableView(TableViewStyle style, Accent accent, ObservableList<S> items)
    {
        super(items);
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public Accent getAccent()
    {
        return this.accent;
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
