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

public class CheckBoxTableCell<S, T> extends javafx.scene.control.cell.CheckBoxTableCell<S, T>
{
    private static final String USER_AGENT_STYLESHEET = "table-view.css";
    private static final String CSS_CLASS = "rt-check-box-table-cell";

    protected boolean isAccentSpecified = false;
    protected Accent accent = Accent.PRIMARY_MID;
    
    protected CheckBox checkBox;
    protected ObservableValue<Boolean> cellSelectedProperty;

    public CheckBoxTableCell()
    {
        super();
        initialize();
    }

    public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> getSelectedProperty)
    {
        super(getSelectedProperty);
        initialize();
    }

    public CheckBoxTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<T> converter)
    {
        super(getSelectedProperty, converter);
        initialize();
    }

    public CheckBoxTableCell(Accent accent)
    {
        super();
        this.accent = accent;
        this.isAccentSpecified = true;
        initialize();
    }

    public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> getSelectedProperty, Accent accent)
    {
        super(getSelectedProperty);
        this.accent = accent;
        this.isAccentSpecified = true;
        initialize();
    }

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
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
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

    @SuppressWarnings("unchecked")
    @Override
    public void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty)
        {
            setText(null);
            setGraphic(null);
        } else
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
