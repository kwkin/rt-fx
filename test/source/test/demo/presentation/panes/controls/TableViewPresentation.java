package test.demo.presentation.panes.controls;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.TableColumn;
import mil.af.eglin.ccf.rt.fx.control.TableView;
import mil.af.eglin.ccf.rt.fx.control.cell.CheckBoxTableCell;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TableColumnStyle;
import mil.af.eglin.ccf.rt.fx.control.style.TableViewStyle;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.abstraction.data.immutable.Service;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;
import test.demo.presentation.data.TablePerson;
import test.demo.presentation.model.TableViewModel;

public class TableViewPresentation extends SizedTitledCard
{
    private static final String TITLE = "Table View";

    private PaneController controller;
    private TableViewModel model;

    public TableViewPresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);
        this.controller = controller;
        this.model = controller.getTableViewModel();

        VBox stackPane = new VBox();

        stackPane.getChildren().add(createTableView());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createTableView2());

        setContent(stackPane);
    }

    private Node createTableView()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("RT-FX tables can be styled with zebra stripes and various accents. Accents change the color of highlighted cells and nested components.");

        TableView<TablePerson> table = new TableView<TablePerson>();
        
        TableColumn<TablePerson, Boolean> isSubscribedColumn = new TableColumn<TablePerson, Boolean>("Sub");
        isSubscribedColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Boolean>("isSubscribed"));
        isSubscribedColumn.setPrefWidth(60);

        isSubscribedColumn.setCellFactory(column ->
        {
            CheckBoxTableCell<TablePerson, Boolean> ctCell = new CheckBoxTableCell<>();
            ctCell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>()
            {
                @Override
                public ObservableValue<Boolean> call(Integer index)
                {
                    TablePerson person = model.getPeople().get(index);
                    person.getIsSubscribedProperty().addListener((ov, oldVal, newVal) ->
                    {
                        controller.updatePersonIsSubscribed(person.getId(), newVal);
                    });
                    return person.getIsSubscribedProperty();
                }
            });
            return ctCell;
        });

        TableColumn<TablePerson, String> firstNameColumn = new TableColumn<TablePerson, String>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("firstName"));
        firstNameColumn.setPrefWidth(125);

        TableColumn<TablePerson, String> lastNameColumn = new TableColumn<TablePerson, String>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("lastName"));
        lastNameColumn.setPrefWidth(125);

        TableColumn<TablePerson, String> emailColumn = new TableColumn<TablePerson, String>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("email"));
        emailColumn.setPrefWidth(200);

        TableColumn<TablePerson, Service> serviceColumn = new TableColumn<TablePerson, Service>("Service");
        serviceColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Service>("service"));
        serviceColumn.setPrefWidth(100);

        TableColumn<TablePerson, Integer> ageColumn = new TableColumn<TablePerson, Integer>("Age",
                TableColumnStyle.NUMBER);
        ageColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Integer>("age"));
        ageColumn.setPrefWidth(75);

        TableColumn<TablePerson, Double> coolFactorColumn = new TableColumn<TablePerson, Double>("Coolness",
                TableColumnStyle.NUMBER);
        coolFactorColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Double>("coolFactor"));
        coolFactorColumn.setPrefWidth(100);
        coolFactorColumn.setCellFactory(column ->
        {
            return new TableCell<TablePerson, Double>()
            {
                @Override
                protected void updateItem(Double item, boolean empty)
                {
                    super.updateItem(item, empty);

                    if (item == null || empty)
                    {
                        setText(null);
                        setStyle("");
                    } 
                    else
                    {
                        setText(String.format("%.2f", item));
                    }
                }
            };
        });

        table.setEditable(true);

        table.setItems(model.getPeople());
        table.getColumns().add(isSubscribedColumn);
        table.getColumns().add(firstNameColumn);
        table.getColumns().add(emailColumn);
        table.getColumns().add(serviceColumn);
        table.getColumns().add(ageColumn);
        table.getColumns().add(coolFactorColumn);

        descriptionPane.setContent(table);

        return descriptionPane;
    }

    private Node createTableView2()
    {
        TableView<TablePerson> table = new TableView<TablePerson>(TableViewStyle.PLAIN, Accent.PRIMARY_MID);
        
        TableColumn<TablePerson, Boolean> isSubscribedColumn = new TableColumn<TablePerson, Boolean>("Sub");
        isSubscribedColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Boolean>("isSubscribed"));
        isSubscribedColumn.setPrefWidth(60);

        isSubscribedColumn.setCellFactory(column ->
        {
            CheckBoxTableCell<TablePerson, Boolean> ctCell = new CheckBoxTableCell<>();
            ctCell.setSelectedStateCallback(new Callback<Integer, ObservableValue<Boolean>>()
            {
                @Override
                public ObservableValue<Boolean> call(Integer index)
                {
                    TablePerson person = model.getPeople().get(index);
                    person.getIsSubscribedProperty().addListener((ov, oldVal, newVal) ->
                    {
                        controller.updatePersonIsSubscribed(person.getId(), newVal);
                    });
                    return person.getIsSubscribedProperty();
                }
            });
            return ctCell;
        });

        TableColumn<TablePerson, String> firstNameColumn = new TableColumn<TablePerson, String>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("firstName"));
        firstNameColumn.setPrefWidth(125);

        TableColumn<TablePerson, String> lastNameColumn = new TableColumn<TablePerson, String>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("lastName"));
        lastNameColumn.setPrefWidth(125);

        TableColumn<TablePerson, String> emailColumn = new TableColumn<TablePerson, String>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, String>("email"));
        emailColumn.setPrefWidth(200);

        TableColumn<TablePerson, Service> serviceColumn = new TableColumn<TablePerson, Service>("Service");
        serviceColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Service>("service"));
        serviceColumn.setPrefWidth(100);

        TableColumn<TablePerson, Integer> ageColumn = new TableColumn<TablePerson, Integer>("Age",
                TableColumnStyle.NUMBER);
        ageColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Integer>("age"));
        ageColumn.setPrefWidth(75);

        TableColumn<TablePerson, Double> coolFactorColumn = new TableColumn<TablePerson, Double>("Coolness",
                TableColumnStyle.NUMBER);
        coolFactorColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Double>("coolFactor"));
        coolFactorColumn.setPrefWidth(100);
        coolFactorColumn.setCellFactory(column ->
        {
            return new TableCell<TablePerson, Double>()
            {
                @Override
                protected void updateItem(Double item, boolean empty)
                {
                    super.updateItem(item, empty);

                    if (item == null || empty)
                    {
                        setText(null);
                        setStyle("");
                    } 
                    else
                    {
                        setText(String.format("%.2f", item));
                    }
                }
            };
        });

        table.setEditable(true);

        table.setItems(model.getPeople());
        table.getColumns().add(isSubscribedColumn);
        table.getColumns().add(firstNameColumn);
        table.getColumns().add(emailColumn);
        table.getColumns().add(serviceColumn);
        table.getColumns().add(ageColumn);
        table.getColumns().add(coolFactorColumn);

        return table;
    }
}
