package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.TableColumnStyle;

/**
 * A {@link TableView} is made up of a number of TableColumn instances.
 * 
 * @param <S> the type of data contained within the table view
 * @param <T> the type of data contained within table columns cell
 */
public class TableColumn<S, T> extends javafx.scene.control.TableColumn<S, T>
{
    protected TableColumnStyle style = TableColumnStyle.TEXT;

    private static final String CSS_CLASS = "rt-table-column";

    /**
     * Creates an untitled {@code TableColumn} with default cell factory,
     * comparator, and onEditCommit.
     */
    public TableColumn()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code TableColumn} with the provided title, default cell
     * factory, comparator, and onEditCommit.
     * 
     * @param text the title
     */
    public TableColumn(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates an untitled {@code TableColumn} with default cell
     * factory, comparator, and onEditCommit and the provided style
     * 
     * @param style the style used to change the overall look of the table column
     */
    public TableColumn(TableColumnStyle style)
    {
        super();
        this.style = style;
        initialize();
    }

    /**
     * Creates a {@code TableColumn} with the provided title and style and with default cell
     * factory, comparator, and onEditCommit.
     * 
     * @param style the style used to change the overall look of the table column
     */
    public TableColumn(String text, TableColumnStyle style)
    {
        super(text);
        this.style = style;
        initialize();
    }

    private void initialize()
    {
        getStyleClass().add(String.format("%s-%s", CSS_CLASS, this.style.getCssStyleName()));
    }
}
