package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import mil.af.eglin.ccf.rt.fx.validation.RequiredFieldValidator;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class TextAreaPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Text Areas";
    
    public TextAreaPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createTextAreas());
        
        setContent(stackPane);
    }
    
    private Node createTextAreas()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Unliked text fields, text areas are filled. Text areas are alos taller and have automatically word wrap.");

        double textAreaHeight = 100;
        double textAreaHelperHeight = 130;
        GridPane textboxGridPane = new GridPane();
        TextArea primaryLightTextArea = new TextArea("This is a sentence initialized in a text area.", Accent.PRIMARY_LIGHT);
        primaryLightTextArea.setPrefHeight(textAreaHeight);
        primaryLightTextArea.setWrapText(true);
        
        TextArea primaryMidTextArea = new TextArea(Accent.PRIMARY_MID);
        primaryMidTextArea.setPrefHeight(textAreaHeight);
        primaryMidTextArea.setPromptText("Floating");
        primaryMidTextArea.setLabelFloat(true);
        
        TextArea primaryDarkTextArea = new TextArea(Accent.PRIMARY_DARK);
        primaryDarkTextArea.setPrefHeight(textAreaHelperHeight);
        primaryDarkTextArea.setPromptText("Prompt Text");
        primaryDarkTextArea.setIsShowHelperText(true);
        primaryDarkTextArea.setHelperText("Helper Text");

        TextArea secondaryLightTextArea = new TextArea(Accent.SECONDARY_LIGHT);
        secondaryLightTextArea.setPrefHeight(textAreaHelperHeight);
        secondaryLightTextArea.setLabelFloat(true);
        secondaryLightTextArea.setPromptText("Floating");
        secondaryLightTextArea.setIsShowHelperText(true);
        secondaryLightTextArea.setHelperText("Helper Text");
        
        TextArea secondaryMidTextArea = new TextArea("This is a sentence initialized in a text area.", Accent.SECONDARY_MID);
        secondaryMidTextArea.setPrefHeight(textAreaHelperHeight);
        secondaryMidTextArea.setLabelFloat(true);
        secondaryMidTextArea.setPromptText("Floating");
        secondaryMidTextArea.setIsShowHelperText(true);
        secondaryMidTextArea.setHelperText("Helper Text");
        
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator("Field is required.");
        TextArea secondaryDarkTextArea = new TextArea(Accent.SECONDARY_DARK);
        secondaryDarkTextArea.setPrefHeight(textAreaHelperHeight);
        secondaryDarkTextArea.setLabelFloat(true);
        secondaryDarkTextArea.setPromptText("Required");
        secondaryDarkTextArea.getValidators().add(requiredValidator);
        secondaryDarkTextArea.setValidateCondition(ValidateCondition.UNFOCUS);
        
        textboxGridPane.add(primaryLightTextArea, 0, 0);
        textboxGridPane.add(primaryMidTextArea, 1, 0);
        textboxGridPane.add(primaryDarkTextArea, 0, 1);
        textboxGridPane.add(secondaryLightTextArea, 1, 1);
        textboxGridPane.add(secondaryMidTextArea, 0, 2);
        textboxGridPane.add(secondaryDarkTextArea, 1, 2);

        descriptionPane.setContent(textboxGridPane);
        
        return descriptionPane;
    }
}