package test.single.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicComboBoxPresentation extends TitledCard
{
    private static final String TITLE = "Combo Boxes";
    
    public BasicComboBoxPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

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
        
        GridPane textboxGridPane = new GridPane();
        ComboBox<String> primaryLightComboBox = new ComboBox<String>(items);
        primaryLightComboBox.setPromptText("First Name:");
        ComboBox<String> primaryMidComboBox = new ComboBox<String>(items);
        primaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> primaryDarkComboBox = new ComboBox<String>(items);
        primaryDarkComboBox.setPromptText("Images and Text");

        GridPane.setFillWidth(primaryLightComboBox, true);
        GridPane.setFillWidth(primaryMidComboBox, true);
        
        ComboBox<String> secondaryLightComboBox = new ComboBox<String>(items);
        secondaryLightComboBox.setPromptText("First Name:");
        ComboBox<String> secondaryMidComboBox = new ComboBox<String>(items);
        secondaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> secondaryDarkComboBox = new ComboBox<String>(items);
        secondaryDarkComboBox.setPromptText("Images and Text:");
        
        textboxGridPane.add(primaryLightComboBox, 0, 0);
        textboxGridPane.add(primaryMidComboBox, 0, 1);
        textboxGridPane.add(primaryDarkComboBox, 0, 2);
        
        textboxGridPane.add(secondaryLightComboBox, 1, 0);
        textboxGridPane.add(secondaryMidComboBox, 1, 1);
        textboxGridPane.add(secondaryDarkComboBox, 1, 2);

        descriptionPane.setContent(textboxGridPane);
        
        return descriptionPane;
    }
}