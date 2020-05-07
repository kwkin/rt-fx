package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class TextAreaPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Text Areas";
    
    public TextAreaPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createTextAreas());
        
        setContent(stackPane);
    }
    
    private Node createTextAreas()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Unliked text fields, text areas are filled. Text areas are alos taller and have automatically word wrap.");

        double textAreaHeight = 60;
        GridPane textboxGridPane = new GridPane();
        TextArea primaryLightTextArea = new TextArea(Accent.PRIMARY_LIGHT);
        primaryLightTextArea.setPrefHeight(textAreaHeight);
        primaryLightTextArea.setText("This is a sentence in a text area.");
        primaryLightTextArea.setWrapText(true);
        TextArea primaryMidTextArea = new TextArea(Accent.PRIMARY_MID);
        primaryMidTextArea.setPrefHeight(textAreaHeight);
        TextArea primaryDarkTextArea = new TextArea(Accent.PRIMARY_DARK);
        primaryDarkTextArea.setPrefHeight(textAreaHeight);

        TextArea secondaryLightTextArea = new TextArea(Accent.SECONDARY_LIGHT);
        secondaryLightTextArea.setPrefHeight(textAreaHeight);
        TextArea secondaryMidTextArea = new TextArea(Accent.SECONDARY_MID);
        secondaryMidTextArea.setPrefHeight(textAreaHeight);
        TextArea secondaryDarkTextArea = new TextArea(Accent.SECONDARY_DARK);
        secondaryDarkTextArea.setPrefHeight(textAreaHeight);
        
        textboxGridPane.add(primaryLightTextArea, 0, 0);
        textboxGridPane.add(primaryMidTextArea, 0, 1);
        textboxGridPane.add(primaryDarkTextArea, 0, 2);
        
        textboxGridPane.add(secondaryLightTextArea, 1, 0);
        textboxGridPane.add(secondaryMidTextArea, 1, 1);
        textboxGridPane.add(secondaryDarkTextArea, 1, 2);

        descriptionPane.setContent(textboxGridPane);
        
        return descriptionPane;
    }
}