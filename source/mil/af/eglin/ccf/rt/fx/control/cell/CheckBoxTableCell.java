package mil.af.eglin.ccf.rt.fx.control.cell;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import javafx.util.StringConverter;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.TableView;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A bi-state selection control for adding a check box to a table.
 *
 * @param <S> the type of the elements contained within the TableView
 * @param <T> the type of the elements contained within the TableColumn.
 */
public class CheckBoxTableCell<S, T> extends javafx.scene.control.cell.CheckBoxTableCell<S, T>
{
    private static final String USER_AGENT_STYLESHEET = "table-view.css";
    private static final String CSS_CLASS = "rt-check-box-table-cell";

    protected boolean isAccentSpecified = false;
    protected Accent accent = Accent.PRIMARY_MID;

    protected CheckBox checkBox;
    protected ObservableValue<Boolean> cellSelectedProperty;

    /**
     * Creates a {@code CheckBoxTableCell}
     */
    public CheckBoxTableCell()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code CheckBoxTableCell} with a custom callback to retrieve an
     * observable value for a given cell index
     * 
     * @param getSelectedProperty a callback that returns an observable value
     *            given an index from the table column
     */
    public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> getSelectedProperty)
    {
        super(getSelectedProperty);
        initialize();
    }

    /**
     * Creates a {@code CheckBoxTableCell} with a custom callback and converter
     * 
     * @param getSelectedProperty a callback that returns an observable value
     *            given an index from the table column
     * @param converter A StringConverter that, given an object of type T, will
     *            return a String that can be used to represent the object
     *            visually.
     */
    public CheckBoxTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<T> converter)
    {
        super(getSelectedProperty, converter);
        initialize();
    }

    /**
     * Creates a {@code CheckBoxTableCell} with the provided accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public CheckBoxTableCell(Accent accent)
    {
        super();
        this.accent = accent;
        this.isAccentSpecified = true;
        initialize();
    }

    /**
     * Creates a {@code CheckBoxTableCell} with a custom callback and accent
     * 
     * @param getSelectedProperty a callback that returns an observable value
     *            given an index from the table column
     * @param accent the accent used to change the component's color scheme
     */
    public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> getSelectedProperty, Accent accent)
    {
        super(getSelectedProperty);
        this.accent = accent;
        this.isAccentSpecified = true;
        initialize();
    }

    /**
     * Creates a {@code CheckBoxTableCell} with a custom callback, converter,
     * and accent
     * 
     * @param getSelectedProperty a callback that returns an observable value
     *            given an index from the table column
     * @param converter A StringConverter that, given an object of type T, will
     *            return a String that can be used to represent the object
     *            visually.
     * @param accent the accent used to change the component's color scheme
     */
    public CheckBoxTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<T> converter, Accent accent)
    {
        super(getSelectedProperty, converter);
        this.accent = accent;
        this.isAccentSpecified = true;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return ResourceLoader.getInstance().loadComponent(USER_AGENT_STYLESHEET);
    }

    private void initialize()
    {
        this.checkBox = new CheckBox(this.accent);
        getStyleClass().add(CSS_CLASS);
        setGraphic(checkBox);

        if (!this.isAccentSpecified)
        {
            if (getTableView() != null)
            {
                if (getTableView() instanceof TableView)
                {
                    TableView<?> tableView = (TableView<?>) getTableView();
                    this.accent = tableView.getAccent();
                    getStyleClass().add(this.accent.getStyleClassName());
                }
            }
            tableViewProperty().addListener((ov, oldVal, newVal) ->
            {
                if (newVal instanceof TableView)
                {
                    getStyleClass().remove(this.accent.getStyleClassName());
                    this.accent = ((TableView<?>) newVal).getAccent();
                    getStyleClass().add(this.accent.getStyleClassName());
                }
            });
        }
        else
        {
            getStyleClass().add(this.accent.getStyleClassName());
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setGraphic(checkBox);

            if (cellSelectedProperty instanceof BooleanProperty)
            {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty) cellSelectedProperty);
            }

            ObservableValue<?> obsValue = getSelectedProperty();
            if (obsValue instanceof BooleanProperty)
            {
                cellSelectedProperty = (ObservableValue<Boolean>) obsValue;
                checkBox.selectedProperty().bindBidirectional((BooleanProperty) cellSelectedProperty);
            }

            // @formatter:off
            checkBox.disableProperty().bind(Bindings.not(
                getTableView().editableProperty().and(
                getTableColumn().editableProperty()).and(
                editableProperty())
            ));
            // @formatter:on
        }
    }

    protected ObservableValue<?> getSelectedProperty()
    {
        return getSelectedStateCallback() != null ? getSelectedStateCallback().call(getIndex())
                : getTableColumn().getCellObservableValue(getIndex());
    }
}
