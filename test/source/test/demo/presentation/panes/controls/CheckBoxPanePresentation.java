package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class CheckBoxPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Check Boxes";
    
    public CheckBoxPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        
        vBox.getChildren().add(createCheckBoxes());
        vBox.getChildren().add(new TitledSeparator("Titled"));
        
        setContent(vBox);
    }
    
    private Node createCheckBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Check Boxes can have two or three states. By default, check boxes have two states, but the third state can be enabled by setting allowDeterminate to true:");

        GridPane iconButtonPane = new GridPane();
        CheckBox primaryLightCheckBox = new CheckBox(Accent.PRIMARY_LIGHT);
        primaryLightCheckBox.setSelected(true);
        CheckBox primaryMidCheckBox = new CheckBox("Two State", Accent.PRIMARY_MID);
        primaryLightCheckBox.setSelected(false);
        CheckBox primaryDarkCheckBox = new CheckBox("Three State", Accent.PRIMARY_DARK);
        primaryDarkCheckBox.setAllowIndeterminate(true);
        primaryDarkCheckBox.setIndeterminate(true);
        iconButtonPane.add(primaryLightCheckBox, 0, 0);
        iconButtonPane.add(primaryMidCheckBox, 1, 0);
        iconButtonPane.add(primaryDarkCheckBox, 2, 0);
        
        CheckBox secondaryLightCheckBox = new CheckBox(Accent.SECONDARY_LIGHT);
        secondaryLightCheckBox.setSelected(true);
        CheckBox secondaryMidCheckBox = new CheckBox("Two State", Accent.SECONDARY_MID);
        secondaryMidCheckBox.setSelected(false);
        CheckBox secondaryDarkCheckBox = new CheckBox("Three State", Accent.SECONDARY_DARK);
        secondaryDarkCheckBox.setAllowIndeterminate(true);
        secondaryDarkCheckBox.setIndeterminate(true);
        iconButtonPane.add(secondaryLightCheckBox, 0, 1);
        iconButtonPane.add(secondaryMidCheckBox, 1, 1);
        iconButtonPane.add(secondaryDarkCheckBox, 2, 1);

        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }
}
