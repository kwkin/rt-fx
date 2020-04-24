package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextFieldStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class TextFieldPanePresentation extends TitledCard
{
    private static final String TITLE = "Text Fields";
    
    public TextFieldPanePresentation(PaneController controller)
    {
        super(TITLE);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createTextBoxes());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createAccentTextBoxes());
        
        setContent(stackPane);
    }
    
    private Node createTextBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Text boxes come in two permutations: filled and flat. The group below contains filled text fields.");

        GridPane textboxGridPane = new GridPane();
        TextField primaryLightTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_LIGHT);
        primaryLightTextBox.setPromptText("First Name");
        TextField primaryMidTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_MID);
        primaryMidTextBox.setPromptText("Last Name");
        TextField primaryDarkTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_DARK);
        primaryDarkTextBox.setPromptText("Email");

        TextField secondaryLightTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_LIGHT);
        secondaryLightTextBox.setPromptText("First Name");
        TextField secondaryMidTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_MID);
        secondaryMidTextBox.setPromptText("Last Name");
        TextField secondaryDarkTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_DARK);
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
    
    private Node createAccentTextBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("This group contains accented text boxes, which defaults the text line with the primary or secondary color.");

        GridPane textboxGridPane = new GridPane();
        TextField primaryLightAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.PRIMARY_LIGHT);
        primaryLightAccentTextBox.setPromptText("First Name");
        TextField primaryMidAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.PRIMARY_MID);
        primaryMidAccentTextBox.setPromptText("Last Name");
        TextField primaryDarkAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.PRIMARY_DARK);
        primaryDarkAccentTextBox.setPromptText("Email");

        TextField secondaryLightAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.SECONDARY_LIGHT);
        secondaryLightAccentTextBox.setPromptText("First Name");
        TextField secondaryMidAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.SECONDARY_MID);
        secondaryMidAccentTextBox.setPromptText("Last Name");
        TextField secondaryDarkAccentTextBox = new TextField(TextFieldStyle.FLAT, Accent.SECONDARY_DARK);
        secondaryDarkAccentTextBox.setPromptText("Email");
        
        textboxGridPane.add(primaryLightAccentTextBox, 0, 0);
        textboxGridPane.add(primaryMidAccentTextBox, 0, 1);
        textboxGridPane.add(primaryDarkAccentTextBox, 0, 2);
        
        textboxGridPane.add(secondaryLightAccentTextBox, 1, 0);
        textboxGridPane.add(secondaryMidAccentTextBox, 1, 1);
        textboxGridPane.add(secondaryDarkAccentTextBox, 1, 2);

        descriptionPane.setContent(textboxGridPane);
        
        return descriptionPane;
    }
}