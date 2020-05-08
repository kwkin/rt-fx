package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.ChoiceBox;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class ComboBoxComparison extends SizedTitledCard
{
    private static final String TITLE = "Combo Box";

    public ComboBoxComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        ColumnConstraints rightConstraint = new ColumnConstraints();
        rightConstraint.setFillWidth(true);
        rightConstraint.setPercentWidth(50);
        ColumnConstraints leftConstraint = new ColumnConstraints();
        leftConstraint.setFillWidth(true);
        leftConstraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(leftConstraint, leftConstraint);

        Label rtLabel = new Label("RT-FX Combo Box", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Combo Box", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("-Animation");
        descriptionPane.addLine("-Style");
        descriptionPane.addLine("-Fill Width");
        descriptionPane.addLine("-Arrow Direction when showing popup");
        descriptionPane.addLine("-Popup");
        descriptionPane.addLine("-Popup item padding");

        Label rtChoiceLabel = new Label("RT-FX Choice Box", LabelStyle.LARGE);
        rtChoiceLabel.setAlignment(Pos.CENTER);
        rtChoiceLabel.setMaxWidth(Double.MAX_VALUE);
        
        int row = 0;
        javafx.scene.control.ComboBox<String> fxComponent = new javafx.scene.control.ComboBox<>();
        fxComponent.setPromptText("JavaFX ComboBox");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("JavaFX Choice Box");
        javafx.scene.control.ChoiceBox<String> fxComponentChoice = new javafx.scene.control.ChoiceBox<>(items);
        fxComponentChoice.getSelectionModel().select(0);
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        
        grid.add(fxComponent,         0, row, 1, 1);
        grid.add(fxComponentChoice,   1, row++, 1, 1);
        grid.add(rtLabel,             0, row, 1, 1);
        grid.add(jfxLabel, 1, row++,  1, 1);
        grid.add(createRtComboBox(),  0, row, 1, 1);
        grid.add(createJfxComboBox(), 1, row++, 1, 1);
        grid.add(descriptionPane,     0, row++, 2, 1);
//        grid.add(rtChoiceLabel,       0, row++, 1, 1);
//        grid.add(createRtChoiceBox(), 0, row, 1, 1);
        setContent(grid);
    }

    public Node createRtComboBox()
    {
        VBox box = new VBox();
        box.setSpacing(30);

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        
        ComboBox<String> rtCombobox = new ComboBox<String>(items, Accent.PRIMARY_MID);        
        ComboBox<String> rtEditable = new ComboBox<String>(items, Accent.PRIMARY_MID);   
        rtEditable.setPromptText("Editable");
        rtEditable.setEditable(true);  
        ComboBox<String> rtDisabled = new ComboBox<String>(items, Accent.PRIMARY_MID);
        rtDisabled.setPromptText("Disabled");
        rtDisabled.setDisable(true);
        box.getChildren().add(rtCombobox);
        box.getChildren().add(rtEditable);
        box.getChildren().add(rtDisabled);
        return box;
    }

    public Node createJfxComboBox()
    {
        VBox box = new VBox();
        box.setSpacing(30);

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        
        JFXComboBox<String> rtCombobox = new JFXComboBox<String>(items);        
        JFXComboBox<String> rtEditable = new JFXComboBox<String>(items);
        rtEditable.setPromptText("Editable");
        rtEditable.setEditable(true);
        JFXComboBox<String> disabled = new JFXComboBox<String>(items);
        disabled.setPromptText("Disabled");
        disabled.setDisable(true);
        box.getChildren().add(rtCombobox);
        box.getChildren().add(rtEditable);
        return box;
    }

    public Node createRtChoiceBox()
    {
        VBox box = new VBox();
        box.setSpacing(30);

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        
        ChoiceBox<String> rtCombobox = new ChoiceBox<String>(items, Accent.PRIMARY_MID);  
        ChoiceBox<String> rtDisabled = new ChoiceBox<String>(items, Accent.PRIMARY_MID);  
        rtDisabled.setDisable(true);
        box.getChildren().add(rtCombobox);
        box.getChildren().add(rtDisabled);
        return box;
    }
}
