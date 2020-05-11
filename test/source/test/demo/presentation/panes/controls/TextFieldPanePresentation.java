package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextFieldStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class TextFieldPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Text Fields";
    
    public TextFieldPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);

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
        TextField primaryLightAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_LIGHT);
        primaryLightAccentTextBox.setPromptText("First Name");
        primaryLightAccentTextBox.setLabelFloat(true);
        TextField primaryMidAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_MID);
        primaryMidAccentTextBox.setPromptText("Last Name");
        primaryMidAccentTextBox.setLabelFloat(true);
        TextField primaryDarkAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_DARK);
        primaryDarkAccentTextBox.setPromptText("Email");
        primaryDarkAccentTextBox.setLabelFloat(true);

        TextField secondaryLightAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_LIGHT);
        secondaryLightAccentTextBox.setPromptText("First Name");
        secondaryLightAccentTextBox.setLabelFloat(true);
        TextField secondaryMidAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_MID);
        secondaryMidAccentTextBox.setPromptText("Last Name");
        secondaryMidAccentTextBox.setLabelFloat(true);
        TextField secondaryDarkAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_DARK);
        secondaryDarkAccentTextBox.setPromptText("Email");
        secondaryDarkAccentTextBox.setLabelFloat(true);
        
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