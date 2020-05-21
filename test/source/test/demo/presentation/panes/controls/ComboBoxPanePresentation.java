package test.demo.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.RegexValidator;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
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
        ObservableList<String> validItems = FXCollections.observableArrayList();
        validItems.add("Invalid");
        validItems.add("Invalid");
        validItems.add("Valid");
        validItems.add("Invalid");
        validItems.add("Invalid");

        VBox vBox = new VBox();
        vBox.setSpacing(16);

        ComboBox<String> comboBox = new ComboBox<>(Accent.PRIMARY_LIGHT);
        comboBox.setItems(items);
        comboBox.setPromptText("Prompt Text");
        comboBox.setValue(items.get(2));
        ComboBox<String> floatingComboBox = new ComboBox<>(Accent.PRIMARY_MID);
        floatingComboBox.setItems(items);
        floatingComboBox.setPromptText("Floating");
        floatingComboBox.setLabelFloat(true);
        floatingComboBox.setValue(items.get(2));
        ComboBox<String> helperComboBox = new ComboBox<>(Accent.PRIMARY_DARK);
        helperComboBox.setItems(items);
        helperComboBox.setHelperText("Helper Text");
        helperComboBox.setIsShowHelperText(true);
        ComboBox<String> editableComboBox = new ComboBox<>(Accent.SECONDARY_LIGHT);
        editableComboBox.setItems(items);
        editableComboBox.setEditable(true);
        ComboBox<String> editableFloatingComboBox = new ComboBox<>(Accent.SECONDARY_MID);
        editableFloatingComboBox.setItems(items);
        editableFloatingComboBox.setEditable(true);
        floatingComboBox.setPromptText("Floating");
        floatingComboBox.setLabelFloat(true);

        ComboBox<String> allComboBox = new ComboBox<>(Accent.SECONDARY_DARK);
        allComboBox.setItems(items);
        allComboBox.setEditable(true);
        allComboBox.setHelperText("Helper Text");
        allComboBox.setLabelFloat(true);
        allComboBox.setIsShowHelperText(true);
        allComboBox.setPromptText("Floating");
        
        RegexValidator regexValidator = new RegexValidator("Valid");
        ComboBox<String> validatableComboBox = new ComboBox<>(Accent.PRIMARY_MID);
        validatableComboBox.setItems(validItems);
        validatableComboBox.setValue("Valid");
        validatableComboBox.getValidators().add(regexValidator);
        validatableComboBox.setValidateCondition(ValidateCondition.CHANGED);
        
        vBox.getChildren().add(comboBox);
        vBox.getChildren().add(floatingComboBox);
        vBox.getChildren().add(helperComboBox);
        vBox.getChildren().add(editableComboBox);
        vBox.getChildren().add(editableFloatingComboBox);
        vBox.getChildren().add(allComboBox);
        vBox.getChildren().add(validatableComboBox);

        descriptionPane.setContent(vBox);
        
        return descriptionPane;
    }
}