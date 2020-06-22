package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.ColorPickerButton;
import mil.af.eglin.ccf.rt.fx.control.ColorPickerIcon;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.validation.FunctionValidator;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidateCondition;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class ColorPickerPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Color Pickers";
    
    public ColorPickerPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);

        VBox vBox = new VBox();
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Color Pickers come in several varieties: combo boxes, buttons, and icons.");
        vBox.getChildren().add(descriptionPane);
        vBox.getChildren().add(new TitledSeparator("Combo Box"));
        vBox.getChildren().add(createComboColorPickers());
        vBox.getChildren().add(new TitledSeparator("Button Buttons"));
        vBox.getChildren().add(createButtonColorPickers());
        vBox.getChildren().add(new TitledSeparator("Icon Buttons"));
        vBox.getChildren().add(createIconColorPickers());
        setContent(vBox);
    }
    
    private Node createComboColorPickers()
    {
        VBox box = new VBox();
        box.setSpacing(16);
        ColorPicker rtUninitialized = new ColorPicker();
        ColorPicker rtInitialized = new ColorPicker();
        rtInitialized.setValue(Color.AQUAMARINE);
        rtInitialized.overlayColorProperty().bind(rtInitialized.valueProperty());
        ColorPicker rtAll = new ColorPicker();
        rtAll.setPromptText("Prompt Text");
        rtAll.setLabelFloat(true);
        rtAll.setValue(Color.RED);
        rtAll.setHelperText("Helper Text");
        rtAll.setIsShowHelperText(true);
        FunctionValidator<Color> nonOpaqueValidator = new FunctionValidator<>((value) -> 
        {
            return Double.compare(value.getOpacity(), 1) >= 0;
        });
        ColorPicker validatableColorPicker = new ColorPicker();
        validatableColorPicker.setValue(Color.TRANSPARENT);
        validatableColorPicker.setPromptText("Non-Opaque");
        validatableColorPicker.setLabelFloat(true);
        validatableColorPicker.setValidateCondition(ValidateCondition.CHANGED);
        validatableColorPicker.getValidators().add(nonOpaqueValidator);
        validatableColorPicker.validate();
        
        box.getChildren().add(rtUninitialized);
        box.getChildren().add(rtInitialized);
        box.getChildren().add(rtAll);
        box.getChildren().add(validatableColorPicker);
        return box;
    }
    
    private Node createButtonColorPickers()
    {
        VBox box = new VBox();
        box.setSpacing(16);
        ColorPickerButton rtButton = new ColorPickerButton();
        ColorPickerButton rtButtonInitialized = new ColorPickerButton();
        rtButtonInitialized.setValue(Color.SANDYBROWN);

        box.getChildren().add(rtButton);
        box.getChildren().add(rtButtonInitialized);
        return box;
    }
    
    private Node createIconColorPickers()
    {
        VBox box = new VBox();
        box.setSpacing(16);
        ColorPickerIcon rtIcon = new ColorPickerIcon();
        rtIcon.setValue(Color.TEAL);
        ColorPickerIcon rtIconWithText = new ColorPickerIcon();
        rtIconWithText.setLabelVisiblity(true);
        rtIconWithText.setValue(Color.MEDIUMTURQUOISE);

        box.getChildren().add(rtIcon);
        box.getChildren().add(rtIconWithText);
        return box;
    }
}
