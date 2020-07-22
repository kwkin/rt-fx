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

/**
 * A table view shows rows of data broken into columns.
 *
 * @param <S> the type of data contained within the table view
 */
public class TableView<S> extends javafx.scene.control.TableView<S> implements RtStyleableComponent
{
    private static final String USER_AGENT_STYLESHEET = "table-view.css";
    private static final String CSS_CLASS = "rt-table-view";
    
    protected TableViewStyle style = TableViewStyle.ZEBRA;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final StyleablePropertyFactory<CheckBox> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.TableView.getClassCssMetaData());
    
    /**
     * Creates an empty {@code TableView}
     */
    public TableView()
    {
        super();
        initialize();
    }
    
    /**
     * Creates a {@code TableView} with the content set to the provided items
     * 
     * @param items the content to insert into the table view
     */
    public TableView(ObservableList<S> items)
    {
        super(items);
        initialize();
    }

    /**
     * Creates an empty {@code TableView} with the specified style.
     * 
     * @param style the style used to change the overall look of the button.
     */
    public TableView(TableViewStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates an empty {@code TableView} with the specified style and accent.
     * 
     * @param style the style used to change the overall look of the button.
     * @param accent the accent used to change the component's color scheme
     */
    public TableView(TableViewStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code TableView} with the specified items and style.
     * 
     * @param items the content to insert into the table view
     * @param style the style used to change the overall look of the button.
     */
    public TableView(ObservableList<S> items, TableViewStyle style)
    {
        super(items);
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code TableView} with the specified items, style, accent.
     * 
     * @param items the content to insert into the table view
     * @param style the style used to change the overall look of the button.
     * @param accent the accent used to change the component's color scheme
     */
    public TableView(ObservableList<S> items, TableViewStyle style, Accent accent)
    {
        super(items);
        this.style = style;
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
