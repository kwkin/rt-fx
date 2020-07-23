package test.demo.presentation.panes.controls;

import javafx.geometry.Pos;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import mil.af.eglin.ccf.rt.fx.validation.RequiredFieldValidator;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;
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

        SvgIcon cog = new SvgIcon(SvgFile.COG, IconSize.SIZE_24);
        TextField trailingIconTextField = new TextField(Accent.PRIMARY_DARK);
        trailingIconTextField.setPromptText("Floating and Icon");
        trailingIconTextField.setLabelFloat(true);
        trailingIconTextField.setTrailingIcon(cog);

        SvgIcon folder = new SvgIcon(SvgFile.FOLDER, IconSize.SIZE_24);
        IconButton folderButton = new IconButton(folder);
        TextField trailingButtonTextField = new TextField(Accent.SECONDARY_LIGHT);
        trailingButtonTextField.setPromptText("Button Icon");
        trailingButtonTextField.setTrailingIcon(folderButton);

        SvgIcon eyeOn = new SvgIcon(SvgFile.EYE, IconSize.SIZE_24);
        SvgIcon eyeOff = new SvgIcon(SvgFile.EYE_OUTLINE, IconSize.SIZE_24);
        IconToggleButton eyeToggleButton = new IconToggleButton(eyeOn, eyeOff);
        TextField trailingToggleTextField = new TextField(Accent.SECONDARY_MID);
        trailingToggleTextField.setPromptText("Toggle Button Icon");
        trailingToggleTextField.setTrailingIcon(eyeToggleButton);

        TextField helperTextField = new TextField(Accent.SECONDARY_DARK);
        helperTextField.setPromptText("Label");
        helperTextField.setHelperTextVisible(true);
        helperTextField.setHelperText("Helper Text");
        
        TextField allTextField = new TextField(Accent.SECONDARY_MID);
        allTextField.setPromptText("Label");
        allTextField.setLabelFloat(true);
        allTextField.setHelperTextVisible(true);
        allTextField.setHelperText("Helper Text");
        allTextField.setTrailingText("Trailing Text");

        RequiredFieldValidator requiredValidator = new RequiredFieldValidator("Field is required");
        TextField validableTextField = new TextField(Accent.PRIMARY_MID);
        validableTextField.setPromptText("Required");
        validableTextField.setLabelFloat(true);
        validableTextField.setHelperTextVisible(true);
        validableTextField.setHelperText("This can be invalidated.");
        validableTextField.getValidators().add(requiredValidator);
        validableTextField.setValidateCondition(ValidateCondition.UNFOCUS);
        validableTextField.validate();
        
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