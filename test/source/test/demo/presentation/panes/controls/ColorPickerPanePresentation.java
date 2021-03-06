package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
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
        rtInitialized.setValue(Color.RED);

        Label buttonLabel = new Label("Button Color Pickers");
        ColorPicker rtIconUninitialized = new ColorPicker(ColorPickerStyle.BUTTON);
        ColorPicker rtIconInitialized = new ColorPicker(ColorPickerStyle.BUTTON);
        rtIconInitialized.setValue(Color.SANDYBROWN);

        Label iconLabel = new Label("Icon Color Pickers");
        ColorPicker rtIcon = new ColorPicker(ColorPickerStyle.ICON);
        rtIcon.setValue(Color.TEAL);
        ColorPicker rtIconWithText = new ColorPicker(ColorPickerStyle.ICON);
        rtIconWithText.setLabelVisiblity(true);
        rtIconWithText.setValue(Color.MEDIUMTURQUOISE);
        
        int row = 0;
        box.add(comboBoxLabel, 0, row++, 2, 1);
        box.add(rtUninitialized, 0, row);
        box.add(rtInitialized, 1, row++);

        box.add(buttonLabel, 0, row++, 2, 1);
        box.add(rtIconUninitialized, 0, row);
        box.add(rtIconInitialized, 1, row++);

        box.add(iconLabel, 0, row++, 2, 1);
        box.add(rtIcon, 0, row, 1, 1);
        box.add(rtIconWithText, 1, row++, 1, 1);
        
        descriptionPane.setContent(box);
        
        return descriptionPane;
    }
}
