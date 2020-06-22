package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

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

    public TableView(Accent accent)
    {
        super();
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
    
    public TableView(ObservableList<S> items, TableViewStyle style)
    {
        super(items);
        this.style = style;
        initialize();
    }
    
    public TableView(ObservableList<S> items, Accent accent)
    {
        super(items);
        this.accent = accent;
        initialize();
    }
    
    public TableView(ObservableList<S> items, TableViewStyle style, Accent accent)
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
        return null;
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
        for (TableViewStyle tableStyle : TableViewStyle.values())
        {
            pseudoClassStateChanged(tableStyle.getPseudoClass(), tableStyle == this.style);
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
        TableView.loadStyleSheet();
    }
}
