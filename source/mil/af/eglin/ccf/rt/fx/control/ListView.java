package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ListViewStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A list view displays a horizontal or vertical list of selectable items.
 * 
 * @param <T> the type of objects stored in the list view
 */
public class ListView<T> extends javafx.scene.control.ListView<T> implements RtStyleableComponent
{
    protected ListViewStyle style = ListViewStyle.SOLID;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "list-view.css";
    private static final String CSS_CLASS = "rt-list-view";

    private static final StyleablePropertyFactory<IconButton> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.ListView.getClassCssMetaData());

    /**
     * Creates a default ListView which will display contents stacked
     * vertically.
     */
    public ListView()
    {
        super();
        initialize();
    }

    /**
     * Creates a default ListView which will stack the contents retrieved from
     * the provided {@link ObservableList} vertically.
     * 
     * @param items the list of items to stack
     */
    public ListView(ObservableList<T> items)
    {
        super(items);
        initialize();
    }

    /**
     * Creates a default ListView with the specified style and display contents
     * stacked vertically.
     * 
     * @param style the style used to change the overall look of the
     *            {@code ListView}
     */
    public ListView(ListViewStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates a default ListView with the specified style, accent, and display
     * contents stacked vertically.
     * 
     * @param style the style used to change the overall look of the
     *            {@code ListView}
     */
    public ListView(ListViewStyle style, Accent accent)
    {
        super();
        this.accent = accent;
        this.style = style;
        initialize();
    }

    /**
     * Creates a default ListView with the specified style and contents
     * retrieved from the provided {@link ObservableList} vertically.
     * 
     * @param items the list of items to stack
     * @param style the style used to change the overall look of the
     *            {@code ListView}
     */
    public ListView(ObservableList<T> items, ListViewStyle style)
    {
        super(items);
        this.style = style;
        initialize();
    }

    /**
     * Creates a default ListView with the specified style, accent, and contents
     * retrieved from the provided {@link ObservableList} vertically.
     * 
     * @param items the list of items to stack
     * @param style the style used to change the overall look of the
     *            {@code ListView}
     */
    public ListView(ObservableList<T> items, ListViewStyle style, Accent accent)
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
        getStyleClass().add(this.accent.getStyleClassName());
        for (ListViewStyle listStyle : ListViewStyle.values())
        {
            pseudoClassStateChanged(listStyle.getPseudoClass(), listStyle == this.style);
        }
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
        ScrollPane.loadStyleSheet();
        ListView.loadStyleSheet();
    }
}
