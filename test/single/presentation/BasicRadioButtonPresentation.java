package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicRadioButtonPresentation extends TitledCard
{
    private static final String TITLE = "Radio Buttons";
    
    public BasicRadioButtonPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        vBox.getChildren().add(createRadioButtons());
        
        setContent(vBox);
    }

    private Node createRadioButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Like most RT-FX components, radio buttons can be accented with primary and secondary colors.");

        GridPane iconButtonPane = new GridPane();
        ToggleGroup primaryButtonGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("Option A");
        radioButton1.setToggleGroup(primaryButtonGroup);
        RadioButton radioButton2 = new RadioButton("Option B");
        radioButton2.setToggleGroup(primaryButtonGroup);
        radioButton2.setSelected(true);
        RadioButton radioButton3 = new RadioButton("Option C");
        radioButton3.setToggleGroup(primaryButtonGroup);
        iconButtonPane.add(radioButton1, 0, 0);
        iconButtonPane.add(radioButton2, 0, 1);
        iconButtonPane.add(radioButton3, 0, 2);

        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }
}