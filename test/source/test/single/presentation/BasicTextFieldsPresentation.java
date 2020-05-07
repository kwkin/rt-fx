package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicTextFieldsPresentation extends TitledCard
{
    private static final String TITLE = "Text Fields";
    
    public BasicTextFieldsPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createTextBoxes());
        
        setContent(stackPane);
    }
    
    private Node createTextBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Text boxes come in two permutations: filled and flat. The group below contains filled text fields.");

        GridPane textboxGridPane = new GridPane();
        TextField primaryLightTextBox = new TextField();
        primaryLightTextBox.setPromptText("First Name");
        TextField primaryMidTextBox = new TextField();
        primaryMidTextBox.setPromptText("Last Name");
        TextField primaryDarkTextBox = new TextField();
        primaryDarkTextBox.setPromptText("Email");

        TextField secondaryLightTextBox = new TextField();
        secondaryLightTextBox.setPromptText("First Name");
        TextField secondaryMidTextBox = new TextField();
        secondaryMidTextBox.setPromptText("Last Name");
        TextField secondaryDarkTextBox = new TextField();
        secondaryDarkTextBox.setPromptText("Email");
        
        textboxGridPane.add(primaryLightTextBox, 0, 0);
        textboxGridPane.add(primaryMidTextBox, 0, 1);
        textboxGridPane.add(primaryDarkTextBox, 0, 2);
        
        textboxGridPane.add(secondaryLightTextBox, 1, 0);
        textboxGridPane.add(secondaryMidTextBox, 1, 1);
        textboxGridPane.add(secondaryDarkTextBox, 1, 2);

        descriptionPane.setContent(textboxGridPane);
        
        return descriptionPane;
    }
}