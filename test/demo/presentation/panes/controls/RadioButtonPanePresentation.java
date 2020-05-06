package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class RadioButtonPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Radio Buttons";
    
    public RadioButtonPanePresentation(PaneController controller)
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
        RadioButton primaryLightCheckBox = new RadioButton("Option A", Accent.PRIMARY_LIGHT);
        primaryLightCheckBox.setToggleGroup(primaryButtonGroup);
        RadioButton primaryMidCheckBox = new RadioButton("Option B", Accent.PRIMARY_MID);
        primaryMidCheckBox.setToggleGroup(primaryButtonGroup);
        primaryMidCheckBox.setSelected(true);
        RadioButton primaryDarkCheckBox = new RadioButton("Three State", Accent.PRIMARY_DARK);
        primaryDarkCheckBox.setToggleGroup(primaryButtonGroup);
        iconButtonPane.add(primaryLightCheckBox, 0, 0);
        iconButtonPane.add(primaryMidCheckBox, 0, 1);
        iconButtonPane.add(primaryDarkCheckBox, 0, 2);

        ToggleGroup secondaryButtonGroup = new ToggleGroup();
        RadioButton secondaryLightCheckBox = new RadioButton("Option A", Accent.SECONDARY_LIGHT);
        secondaryLightCheckBox.setToggleGroup(secondaryButtonGroup);
        RadioButton secondaryMidCheckBox = new RadioButton("Option B", Accent.SECONDARY_MID);
        secondaryMidCheckBox.setToggleGroup(secondaryButtonGroup);
        RadioButton secondaryDarkCheckBox = new RadioButton("Option C", Accent.SECONDARY_DARK);
        secondaryDarkCheckBox.setToggleGroup(secondaryButtonGroup);
        secondaryDarkCheckBox.setSelected(true);
        iconButtonPane.add(secondaryLightCheckBox, 1, 0);
        iconButtonPane.add(secondaryMidCheckBox, 1, 1);
        iconButtonPane.add(secondaryDarkCheckBox, 1, 2);

        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }
}
