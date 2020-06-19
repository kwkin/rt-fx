package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.ColorPickerButton;
import mil.af.eglin.ccf.rt.fx.control.ColorPickerIcon;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
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
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createColorPickers());
        
        setContent(stackPane);
    }
    
    private Node createColorPickers()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Color Pickers come in several varieties: combo boxes, buttons, and icons.");

        GridPane box = new GridPane();
        
        Label comboBoxLabel = new Label("Combo Box Color Pickers");
        ColorPicker rtUninitialized = new ColorPicker();
        ColorPicker rtInitialized = new ColorPicker();
        rtInitialized.setPromptText("Prompt Text");
        rtInitialized.setLabelFloat(true);
        rtInitialized.setValue(Color.RED);
        rtInitialized.setHelperText("Helper Text");
        rtInitialized.setIsShowHelperText(true);
        

        Label buttonLabel = new Label("Button Color Pickers");
        ColorPickerButton rtButton = new ColorPickerButton();
        ColorPickerButton rtButtonInitialized = new ColorPickerButton();
        rtButtonInitialized.setValue(Color.SANDYBROWN);

        Label iconLabel = new Label("Icon Color Pickers");
        ColorPickerIcon rtIcon = new ColorPickerIcon();
        rtIcon.setValue(Color.TEAL);
        ColorPickerIcon rtIconWithText = new ColorPickerIcon();
        rtIconWithText.setLabelVisiblity(true);
        rtIconWithText.setValue(Color.MEDIUMTURQUOISE);
        
        int row = 0;
        box.add(comboBoxLabel, 0, row++, 2, 1);
        box.add(rtUninitialized, 0, row);
        box.add(rtInitialized, 1, row++);

        box.add(buttonLabel, 0, row++, 2, 1);
        box.add(rtButton, 0, row);
        box.add(rtButtonInitialized, 1, row++);

        box.add(iconLabel, 0, row++, 2, 1);
        box.add(rtIcon, 0, row, 1, 1);
        box.add(rtIconWithText, 1, row++, 1, 1);
        
        descriptionPane.setContent(box);
        
        return descriptionPane;
    }
}
