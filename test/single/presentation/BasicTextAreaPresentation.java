package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.TitledCardSize;
import test.single.controls.TitledCard;

public class BasicTextAreaPresentation extends TitledCard
{
    private static final String TITLE = "Text Areas";
    
    public BasicTextAreaPresentation()
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
        TextArea primaryLightTextArea = new TextArea();
        primaryLightTextArea.setPrefHeight(textAreaHeight);
        primaryLightTextArea.setText("This is a sentence in a text area.");
        primaryLightTextArea.setWrapText(true);
        TextArea primaryMidTextArea = new TextArea();
        primaryMidTextArea.setPrefHeight(textAreaHeight);
        TextArea primaryDarkTextArea = new TextArea();
        primaryDarkTextArea.setPrefHeight(textAreaHeight);

        TextArea secondaryLightTextArea = new TextArea();
        secondaryLightTextArea.setPrefHeight(textAreaHeight);
        TextArea secondaryMidTextArea = new TextArea();
        secondaryMidTextArea.setPrefHeight(textAreaHeight);
        TextArea secondaryDarkTextArea = new TextArea();
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