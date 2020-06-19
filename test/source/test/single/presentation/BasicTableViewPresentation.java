package test.single.presentation;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.abstraction.data.immutable.Service;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;
import test.demo.presentation.data.TablePerson;
import test.demo.presentation.model.TableViewModel;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicTableViewPresentation extends TitledCard
{
    private static final String TITLE = "Table View";

    private PaneController controller;
    private TableViewModel model;

    public BasicTableViewPresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x1);
        this.controller = controller;
        this.model = controller.getTableViewModel();

        VBox stackPane = new VBox();

        stackPane.getChildren().add(createTableView());

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

        TableColumn<TablePerson, Integer> ageColumn = new TableColumn<TablePerson, Integer>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Integer>("age"));
        ageColumn.setPrefWidth(75);

        TableColumn<TablePerson, Double> coolFactorColumn = new TableColumn<TablePerson, Double>("Coolness");
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
}
