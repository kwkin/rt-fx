package test.demo.presentation.panes.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
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
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createDirectionalCheckBoxes());
        
        setContent(vBox);
    }
    
    private Node createCheckBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Check Boxes can have two or three states. By default, check boxes have two states, but the third state can be enabled by setting allowDeterminate to true:");

        GridPane iconButtonPane = new GridPane();
        iconButtonPane.setPadding(new Insets(0));
        CheckBox primaryLightCheckBox = new CheckBox(Accent.PRIMARY_LIGHT);
        primaryLightCheckBox.setSelected(true);
        CheckBox primaryMidCheckBox = new CheckBox("Two State", Accent.PRIMARY_MID);
        CheckBox primaryDarkCheckBox = new CheckBox("Three State", Accent.PRIMARY_DARK);
        primaryDarkCheckBox.setAllowIndeterminate(true);
        primaryDarkCheckBox.setIndeterminate(true);
        iconButtonPane.add(primaryLightCheckBox, 0, 0);
        iconButtonPane.add(primaryMidCheckBox, 1, 0);
        iconButtonPane.add(primaryDarkCheckBox, 2, 0);
        
        CheckBox secondaryLightCheckBox = new CheckBox(Accent.SECONDARY_LIGHT);
        secondaryLightCheckBox.setSelected(false);
        CheckBox secondaryMidCheckBox = new CheckBox("Two State", Accent.SECONDARY_MID);
        secondaryMidCheckBox.setSelected(true);
        CheckBox secondaryDarkCheckBox = new CheckBox("Three State", Accent.SECONDARY_DARK);
        secondaryDarkCheckBox.setSelected(true);
        secondaryDarkCheckBox.setAllowIndeterminate(true);
        iconButtonPane.add(secondaryLightCheckBox, 0, 1);
        iconButtonPane.add(secondaryMidCheckBox, 1, 1);
        iconButtonPane.add(secondaryDarkCheckBox, 2, 1);

        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }
    
    private Node createDirectionalCheckBoxes()
    {
        FlowPane iconButtonPane = new FlowPane();
        
        CheckBox primaryMidCheckBox = new CheckBox("Left Text", Accent.PRIMARY_MID);
        primaryMidCheckBox.setSelected(true);
        primaryMidCheckBox.setContentDisplay(ContentDisplay.LEFT);
        CheckBox primaryDarkCheckBox = new CheckBox("Right Text", Accent.PRIMARY_DARK);
        primaryDarkCheckBox.setSelected(true);
        primaryMidCheckBox.setContentDisplay(ContentDisplay.RIGHT);
        iconButtonPane.getChildren().add(primaryMidCheckBox);
        iconButtonPane.getChildren().add(primaryDarkCheckBox);
        
        CheckBox secondaryMidCheckBox = new CheckBox("Top Text", Accent.SECONDARY_MID);
        secondaryMidCheckBox.setSelected(true);
        secondaryMidCheckBox.setContentDisplay(ContentDisplay.TOP);
        CheckBox secondaryDarkCheckBox = new CheckBox("Bottom Text", Accent.SECONDARY_DARK);
        secondaryDarkCheckBox.setSelected(true);
        secondaryDarkCheckBox.setContentDisplay(ContentDisplay.BOTTOM);
        iconButtonPane.getChildren().add(secondaryMidCheckBox);
        iconButtonPane.getChildren().add(secondaryDarkCheckBox);
        
        return iconButtonPane;
    }
}
