package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
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
//        stackPane.getChildren().add(new Separator());
//        stackPane.getChildren().add(createAccentTextBoxes());
        
        setContent(stackPane);
    }
    
    private Node createTextBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Text boxes come in two permutations: filled and flat. The group below contains filled text fields.");

        VBox vBox = new VBox();
        vBox.setSpacing(16);
        TextField primaryLightTextBox = new TextField(Accent.PRIMARY_LIGHT);
        primaryLightTextBox.setPromptText("Prompt Text");
        TextField primaryMidTextBox = new TextField(Accent.PRIMARY_MID);
        primaryMidTextBox.setPromptText("Floating");
        primaryMidTextBox.setLabelFloat(true);

        SvgGlyph cog = new SvgGlyph(SvgFile.COG, Color.valueOf("#ababab"), IconSize.SIZE_24);
        TextField primaryDarkTextBox = new TextField(Accent.PRIMARY_DARK);
        primaryDarkTextBox.setPromptText("Floating and Icon");
        primaryDarkTextBox.setLabelFloat(true);
        primaryDarkTextBox.setTrailingGlyph(cog);

        SvgGlyph bell = new SvgGlyph(SvgFile.BELL, Color.valueOf("#ababab"), IconSize.SIZE_24);
        IconButton bellButton = new IconButton(bell);
        TextField secondaryLightTextBox = new TextField(Accent.PRIMARY_DARK);
        secondaryLightTextBox.setPromptText("Button Icon");
        secondaryLightTextBox.setTrailingGlyph(bellButton);

        SvgGlyph eyeOn = new SvgGlyph(SvgFile.EYE, Color.valueOf("#ababab"), IconSize.SIZE_24);
        SvgGlyph eyeOff = new SvgGlyph(SvgFile.EYE_OUTLINE, Color.valueOf("#ababab"), IconSize.SIZE_24);
        IconToggleButton eyeToggleButton = new IconToggleButton(eyeOn, eyeOff);
        TextField secondaryMidTextBox = new TextField(Accent.PRIMARY_DARK);
        secondaryMidTextBox.setPromptText("Toggle Button Icon");
        secondaryMidTextBox.setTrailingGlyph(eyeToggleButton);
        
        vBox.getChildren().add(primaryLightTextBox);
        vBox.getChildren().add(primaryMidTextBox);
        vBox.getChildren().add(primaryDarkTextBox);
        vBox.getChildren().add(secondaryLightTextBox);
        vBox.getChildren().add(secondaryMidTextBox);

        descriptionPane.setContent(vBox);
        
        return descriptionPane;
    }
    
//    private Node createAccentTextBoxes()
//    {
//        DescriptionPane descriptionPane = new DescriptionPane();
//        descriptionPane.setDescription("This group contains accented text boxes, which defaults the text line with the primary or secondary color.");
//
//        GridPane textboxGridPane = new GridPane();
//        TextField primaryLightAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_LIGHT);
//        primaryLightAccentTextBox.setPromptText("First Name");
//        primaryLightAccentTextBox.setLabelFloat(true);
//        TextField primaryMidAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_MID);
//        primaryMidAccentTextBox.setPromptText("Last Name");
//        primaryMidAccentTextBox.setLabelFloat(true);
//        TextField primaryDarkAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.PRIMARY_DARK);
//        primaryDarkAccentTextBox.setPromptText("Email");
//        primaryDarkAccentTextBox.setLabelFloat(true);
//
//        TextField secondaryLightAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_LIGHT);
//        secondaryLightAccentTextBox.setPromptText("First Name");
//        secondaryLightAccentTextBox.setLabelFloat(true);
//        TextField secondaryMidAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_MID);
//        secondaryMidAccentTextBox.setPromptText("Last Name");
//        secondaryMidAccentTextBox.setLabelFloat(true);
//        TextField secondaryDarkAccentTextBox = new TextField(TextFieldStyle.FILLED, Accent.SECONDARY_DARK);
//        secondaryDarkAccentTextBox.setPromptText("Email");
//        secondaryDarkAccentTextBox.setLabelFloat(true);
//        
//        textboxGridPane.add(primaryLightAccentTextBox, 0, 0);
//        textboxGridPane.add(primaryMidAccentTextBox, 0, 1);
//        textboxGridPane.add(primaryDarkAccentTextBox, 0, 2);
//        
//        textboxGridPane.add(secondaryLightAccentTextBox, 1, 0);
//        textboxGridPane.add(secondaryMidAccentTextBox, 1, 1);
//        textboxGridPane.add(secondaryDarkAccentTextBox, 1, 2);
//
//        descriptionPane.setContent(textboxGridPane);
//        
//        return descriptionPane;
//    }
}