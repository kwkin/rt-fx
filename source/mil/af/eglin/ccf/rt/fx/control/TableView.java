package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TableViewStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TableView<S> extends javafx.scene.control.TableView<S>
{
    private static final String USER_AGENT_STYLESHEET = "table-view.css";
    private static final String CSS_CLASS = "rt-table-view";
    
    protected TableViewStyle style = TableViewStyle.ZEBRA;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final StyleablePropertyFactory<CheckBox> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.TableView.getClassCssMetaData());
    
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

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        ScrollPane.loadStyleSheet();
        TableView.loadStyleSheet();
    }
}
