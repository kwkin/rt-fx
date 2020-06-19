package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicColorPickerPresentation extends TitledCard
{
    private static final String TITLE = "Color Pickers";
    
    public BasicColorPickerPresentation()
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
//        ColorPicker rtIconUninitialized = new ColorPicker();
//        ColorPicker rtIconInitialized = new IconColorPicker();
//        rtIconInitialized.setValue(Color.SANDYBROWN);
        
        
        int row = 0;
        box.add(comboBoxLabel, 0, row++, 2, 1);
        box.add(rtUninitialized, 0, row);
        box.add(rtInitialized, 1, row++);

        box.add(buttonLabel, 0, row++, 2, 1);
//        box.add(rtIconUninitialized, 0, row);
//        box.add(rtIconInitialized, 1, row++);
        
        descriptionPane.setContent(box);
        
        return descriptionPane;
    }
}
