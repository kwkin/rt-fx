package test.demo.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.controller.PaneController;

public class ComboBoxPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Combo Boxes";
    
    public ComboBoxPanePresentation(PaneController controller)
    {
        super(TITLE);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createFilledComboBoxes());
        
        setContent(stackPane);
    }

    private Node createFilledComboBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Text boxes come in many permutations: accented, filled, and areas. The group below contains non-accented, filled and non-filled text boxes.");

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");

        VBox vBox = new VBox();
        vBox.setSpacing(16);

        ComboBox<String> primaryLightComboBox = new ComboBox<>(Accent.PRIMARY_LIGHT);
        primaryLightComboBox.setItems(items);
        primaryLightComboBox.setPromptText("Prompt Text");
        primaryLightComboBox.setValue(items.get(2));
        ComboBox<String> primaryMidComboBox = new ComboBox<>(Accent.PRIMARY_MID);
        primaryMidComboBox.setItems(items);
        primaryMidComboBox.setPromptText("Floating");
        primaryMidComboBox.setLabelFloat(true);
        primaryMidComboBox.setValue(items.get(2));

        TextField primaryMidTextBox = new TextField(Accent.PRIMARY_MID);
        primaryMidTextBox.setPromptText("Floating");
        primaryMidTextBox.setLabelFloat(true);

        vBox.getChildren().add(primaryLightComboBox);
        vBox.getChildren().add(primaryMidComboBox);
        vBox.getChildren().add(primaryMidTextBox);

        descriptionPane.setContent(vBox);
        
        return descriptionPane;
    }
}