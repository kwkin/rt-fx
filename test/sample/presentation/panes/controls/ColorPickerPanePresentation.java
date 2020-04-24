package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.IconColorPicker;
import mil.af.eglin.ccf.rt.fx.control.IconColorPicker2;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class ColorPickerPanePresentation extends TitledCard
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
        IconColorPicker rtIconUninitialized = new IconColorPicker();
        IconColorPicker rtIconInitialized = new IconColorPicker();
        rtIconInitialized.setValue(Color.SANDYBROWN);

        Label iconLabel = new Label("Icon Color Pickers");
        IconColorPicker2 rtIcon = new IconColorPicker2();
        rtIcon.setValue(Color.TEAL);
        IconColorPicker2 rtIconWithText = new IconColorPicker2();
        rtIconWithText.setLabelVisibility(true);
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
