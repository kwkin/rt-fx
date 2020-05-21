package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.RequiredFieldValidator;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
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

        SvgGlyph cog = new SvgGlyph(SvgFile.COG, IconSize.SIZE_24);
        TextField primaryDarkTextBox = new TextField(Accent.PRIMARY_DARK);
        primaryDarkTextBox.setPromptText("Floating and Icon");
        primaryDarkTextBox.setLabelFloat(true);
        primaryDarkTextBox.setTrailingGlyph(cog);

        SvgGlyph folder = new SvgGlyph(SvgFile.FOLDER, IconSize.SIZE_24);
        IconButton folderButton = new IconButton(folder);
        TextField secondaryLightTextBox = new TextField(Accent.SECONDARY_LIGHT);
        secondaryLightTextBox.setPromptText("Button Icon");
        secondaryLightTextBox.setTrailingGlyph(folderButton);

        SvgGlyph eyeOn = new SvgGlyph(SvgFile.EYE, IconSize.SIZE_24);
        SvgGlyph eyeOff = new SvgGlyph(SvgFile.EYE_OUTLINE, IconSize.SIZE_24);
        IconToggleButton eyeToggleButton = new IconToggleButton(eyeOn, eyeOff);
        TextField secondaryMidTextBox = new TextField(Accent.SECONDARY_MID);
        secondaryMidTextBox.setPromptText("Toggle Button Icon");
        secondaryMidTextBox.setTrailingGlyph(eyeToggleButton);

        TextField secondaryDarkTextBox = new TextField(Accent.SECONDARY_DARK);
        secondaryDarkTextBox.setPromptText("Label");
        secondaryDarkTextBox.setIsShowHelperText(true);
        secondaryDarkTextBox.setHelperText("Helper Text");

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator("Field is required.");
        TextField validableTextField = new TextField(Accent.PRIMARY_MID);
        validableTextField.setPromptText("Required");
        validableTextField.setLabelFloat(true);
        validableTextField.getValidators().add(requiredValidator);
        validableTextField.setValidateCondition(ValidateCondition.UNFOCUS);
        
        SvgGlyph bell = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_24);
        TextField allEnabledTextBox = new TextField(Accent.SECONDARY_MID);
        allEnabledTextBox.setPromptText("Label");
        allEnabledTextBox.setLabelFloat(true);
        allEnabledTextBox.setIsShowHelperText(true);
        allEnabledTextBox.setHelperText("Helper Text");
        allEnabledTextBox.setTrailingGlyph(bell);
        
        vBox.getChildren().add(primaryLightTextBox);
        vBox.getChildren().add(primaryMidTextBox);
        vBox.getChildren().add(primaryDarkTextBox);
        vBox.getChildren().add(secondaryLightTextBox);
        vBox.getChildren().add(secondaryMidTextBox);
        vBox.getChildren().add(secondaryDarkTextBox);
        vBox.getChildren().add(validableTextField);
        vBox.getChildren().add(allEnabledTextBox);

        descriptionPane.setContent(vBox);
        
        return descriptionPane;
    }
}