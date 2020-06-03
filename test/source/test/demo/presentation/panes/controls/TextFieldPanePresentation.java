package test.demo.presentation.panes.controls;

import javafx.scene.Node;
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
        descriptionPane.setDescription("Text Fields include many new features, including floating prompt text, trailing icons, helper text, and error states. Additionally, textfields allow for accents.");

        VBox vBox = new VBox();
        vBox.setSpacing(16);
        TextField textField = new TextField(Accent.PRIMARY_LIGHT);
        textField.setPromptText("Prompt Text");
        TextField floatingTextField = new TextField(Accent.PRIMARY_MID);
        floatingTextField.setPromptText("Floating");
        floatingTextField.setText("Input text");
        floatingTextField.setLabelFloat(true);

        SvgGlyph cog = new SvgGlyph(SvgFile.COG, IconSize.SIZE_24);
        TextField trailingIconTextField = new TextField(Accent.PRIMARY_DARK);
        trailingIconTextField.setPromptText("Floating and Icon");
        trailingIconTextField.setLabelFloat(true);
        trailingIconTextField.setTrailingGlyph(cog);

        SvgGlyph folder = new SvgGlyph(SvgFile.FOLDER, IconSize.SIZE_24);
        IconButton folderButton = new IconButton(folder);
        TextField trailingButtonTextField = new TextField(Accent.SECONDARY_LIGHT);
        trailingButtonTextField.setPromptText("Button Icon");
        trailingButtonTextField.setTrailingGlyph(folderButton);

        SvgGlyph eyeOn = new SvgGlyph(SvgFile.EYE, IconSize.SIZE_24);
        SvgGlyph eyeOff = new SvgGlyph(SvgFile.EYE_OUTLINE, IconSize.SIZE_24);
        IconToggleButton eyeToggleButton = new IconToggleButton(eyeOn, eyeOff);
        TextField trailingToggleTextField = new TextField(Accent.SECONDARY_MID);
        trailingToggleTextField.setPromptText("Toggle Button Icon");
        trailingToggleTextField.setTrailingGlyph(eyeToggleButton);

        TextField helperTextField = new TextField(Accent.SECONDARY_DARK);
        helperTextField.setPromptText("Label");
        helperTextField.setIsShowHelperText(true);
        helperTextField.setHelperText("Helper Text");
        
        SvgGlyph bell = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_24);
        TextField allTextField = new TextField(Accent.SECONDARY_MID);
        allTextField.setPromptText("Label");
        allTextField.setLabelFloat(true);
        allTextField.setIsShowHelperText(true);
        allTextField.setHelperText("Helper Text");
        allTextField.setTrailingGlyph(bell);

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator("Field is required.");
        TextField validableTextField = new TextField(Accent.PRIMARY_MID);
        validableTextField.setPromptText("Required");
        validableTextField.setLabelFloat(true);
        validableTextField.getValidators().add(requiredValidator);
        validableTextField.setValidateCondition(ValidateCondition.UNFOCUS);
        
        vBox.getChildren().add(textField);
        vBox.getChildren().add(floatingTextField);
        vBox.getChildren().add(trailingIconTextField);
        vBox.getChildren().add(trailingButtonTextField);
        vBox.getChildren().add(trailingToggleTextField);
        vBox.getChildren().add(helperTextField);
        vBox.getChildren().add(allTextField);
        vBox.getChildren().add(validableTextField);

        descriptionPane.setContent(vBox);
        
        return descriptionPane;
    }
}