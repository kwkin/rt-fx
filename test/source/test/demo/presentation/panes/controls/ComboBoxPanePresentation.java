package test.demo.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
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
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createFlatComboBoxes());
        
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
        ComboBox<String> primaryLightComboBox = new ComboBox<String>(items, Accent.PRIMARY_LIGHT);
        primaryLightComboBox.setPromptText("First Name:");
        ComboBox<String> primaryMidComboBox = new ComboBox<String>(items, Accent.PRIMARY_MID);
        primaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> primaryDarkComboBox = new ComboBox<String>(items, Accent.PRIMARY_DARK);
        primaryDarkComboBox.setPromptText("Images and Text");

        GridPane.setFillWidth(primaryLightComboBox, true);
        GridPane.setFillWidth(primaryMidComboBox, true);
        
        ComboBox<String> secondaryLightComboBox = new ComboBox<String>(items, Accent.SECONDARY_LIGHT);
        secondaryLightComboBox.setPromptText("First Name:");
        ComboBox<String> secondaryMidComboBox = new ComboBox<String>(items, Accent.SECONDARY_MID);
        secondaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> secondaryDarkComboBox = new ComboBox<String>(items, Accent.SECONDARY_DARK);
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

    private Node createFlatComboBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Text boxes come in many permutations: accented, filled, and areas. The group below contains non-accented, filled and non-filled text boxes.");

        GridPane textboxGridPane = new GridPane();
        ComboBox<String> primaryLightComboBox = new ComboBox<String>(Accent.PRIMARY_LIGHT);
        primaryLightComboBox.setPromptText("First Name:");
        
        ComboBox<String> primaryMidComboBox = new ComboBox<String>(Accent.PRIMARY_MID);
        primaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> primaryDarkComboBox = new ComboBox<String>(Accent.PRIMARY_DARK);
        primaryDarkComboBox.setPromptText("Images and Text");

        ComboBox<String> secondaryLightComboBox = new ComboBox<String>(Accent.SECONDARY_LIGHT);
        secondaryLightComboBox.setPromptText("First Name:");
        ComboBox<String> secondaryMidComboBox = new ComboBox<String>(Accent.SECONDARY_MID);
        secondaryMidComboBox.setPromptText("Last Name:");
        ComboBox<String> secondaryDarkComboBox = new ComboBox<String>(Accent.SECONDARY_DARK);
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