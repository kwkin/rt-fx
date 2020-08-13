package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.ScrollPane;
import mil.af.eglin.ccf.rt.fx.control.TableColumn;
import mil.af.eglin.ccf.rt.fx.control.TableView;
import mil.af.eglin.ccf.rt.fx.control.cell.CheckBoxTableCell;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.control.style.TableColumnStyle;
import mil.af.eglin.ccf.rt.fx.control.style.TableViewStyle;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.abstraction.data.immutable.Service;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.control.TitledContentPane;
import test.demo.controller.PaneController;
import test.demo.presentation.data.TablePerson;
import test.demo.presentation.model.TableViewModel;

public class TableComparison extends ScrollPane implements TitledContentPane
{
    private static final String TITLE = "Table View";

    private PaneController controller;
    private TableViewModel model;
    
    public TableComparison(PaneController controller)
    {
        StackPane border = new StackPane();
        SizedTitledCard card = new SizedTitledCard(TITLE, TitledCardSize.SIZE_3x3);
        
        this.controller = controller;
        this.model = controller.getTableViewModel();
        
        VBox vBox = new VBox();
        vBox.setSpacing(20);

        DescriptionPane description = new DescriptionPane();
        description.addLine("-Overall table Border");
        description.addLine("-Row Separators");
        description.addLine("-Column Header Text");
        description.addLine("-Column Header Separator");
        description.addLine("-Text and Number Alignments");
        description.addLine("-Scroll bar");
        
        Label rtLabel = new Label("RT-FX Table", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Table", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);

        vBox.getChildren().add(description);
        vBox.getChildren().add(rtLabel);
        vBox.getChildren().add(createRtTable());
        vBox.getChildren().add(jfxLabel);
        vBox.getChildren().add(createJfxTable());

        card.setContent(vBox);
        border.getChildren().add(card);

        setFitToWidth(true);
        setContent(border);
    }
    
    public Node createRtTable()
    {
        TableView<TablePerson> table = new TableView<TablePerson>(TableViewStyle.PLAIN, Accent.PRIMARY_MID);
        GridPane.setVgrow(table, Priority.ALWAYS);
        GridPane.setHgrow(table, Priority.ALWAYS);

        TableColumn<TablePerson, Boolean> isSubscribedColumn = new TableColumn<TablePerson, Boolean>("Sub");
        isSubscribedColumn.setCellValueFactory(new PropertyValueFactory<TablePerson, Boolean>("isSubscribed"));
        isSubscribedColumn.setPrefWidth(90);
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
        coolFactorColumn.setPrefWidth(110);
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
    
    public Node createJfxTable()
    {
        JFXTreeTableColumn<TablePerson, Boolean> isSubscribedColumn = new JFXTreeTableColumn<TablePerson, Boolean>("Sub");
        isSubscribedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, Boolean> param) -> 
        {
            return param.getValue().getValue().getIsSubscribedProperty();
        });
        isSubscribedColumn.setPrefWidth(90);

        JFXTreeTableColumn<TablePerson, String> firstNameColumn = new JFXTreeTableColumn<TablePerson, String>("First Name");
        firstNameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, String> param) -> 
        {
            return param.getValue().getValue().getFirstNameProperty();
        });
        firstNameColumn.setPrefWidth(125);


        JFXTreeTableColumn<TablePerson, String> emailColumn = new JFXTreeTableColumn<TablePerson, String>("Email");
        emailColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, String> param) -> 
        {
            return param.getValue().getValue().getEmailProperty();
        });
        emailColumn.setPrefWidth(200);

        JFXTreeTableColumn<TablePerson, Service> serviceColumn = new JFXTreeTableColumn<TablePerson, Service>("Service");
        serviceColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, Service> param) -> 
        {
            return param.getValue().getValue().getServiceProperty();
        });
        serviceColumn.setPrefWidth(100);

        JFXTreeTableColumn<TablePerson, String> ageColumn = new JFXTreeTableColumn<TablePerson, String>("Age");
        ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, String> param) -> 
        {
            return new SimpleStringProperty(param.getValue().getValue().getAgeProperty().getValue().toString());
        });
        ageColumn.setStyle("-fx-alignment: center-left");
        ageColumn.setPrefWidth(75);

        JFXTreeTableColumn<TablePerson, String> coolFactorColumn = new JFXTreeTableColumn<TablePerson, String>("Coolness");
        coolFactorColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<TablePerson, String> param) -> 
        {
            double value = param.getValue().getValue().getCoolFactorProperty().getValue();
            return new SimpleStringProperty(String.format("%.2f", value));
        });
        coolFactorColumn.setStyle("-fx-alignment: center-left");
        coolFactorColumn.setPrefWidth(110);

        final TreeItem<TablePerson> root = new RecursiveTreeItem<>(model.getPeople(), RecursiveTreeObject::getChildren);
        JFXTreeTableView<TablePerson> table = new JFXTreeTableView<TablePerson>(root);
        table.setEditable(true);
        table.setShowRoot(false);
        table.setEditable(false);
        
        GridPane.setVgrow(table, Priority.ALWAYS);
        GridPane.setHgrow(table, Priority.ALWAYS);
        
        table.getColumns().add(isSubscribedColumn);
        table.getColumns().add(firstNameColumn);
        table.getColumns().add(emailColumn);
        table.getColumns().add(serviceColumn);
        table.getColumns().add(ageColumn);
        table.getColumns().add(coolFactorColumn);
        
        return table;
    }

    @Override
    public String getTitle()
    {
        return TITLE;
    }

    @Override
    public Node getNodeContent()
    {
        return this;
    }
}
