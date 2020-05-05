package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicCheckBoxPresentation extends TitledCard
{
    private static final String TITLE = "CheckBoxes";
    
    public BasicCheckBoxPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        vBox.getChildren().add(createCheckBoxes());
        
        setContent(vBox);
    }

    private Node createCheckBoxes()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Check Boxes can have two or three states. By default, check boxes have two states, but the third state can be enabled by setting allowDeterminate to true:");

        GridPane iconButtonPane = new GridPane();
        CheckBox checkBoxNoText = new CheckBox();
        checkBoxNoText.setSelected(true);
        CheckBox checkBoxText = new CheckBox("Two State");
        checkBoxNoText.setSelected(false);
        CheckBox checkBoxThreeState = new CheckBox("Three State");
        checkBoxThreeState.setAllowIndeterminate(true);
        checkBoxThreeState.setIndeterminate(true);
        iconButtonPane.add(checkBoxNoText, 0, 0);
        iconButtonPane.add(checkBoxText, 1, 0);
        iconButtonPane.add(checkBoxThreeState, 2, 0);

        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }
}