package test.jfeonix.presentation.panes.controls;

import com.jfoenix.controls.JFXRadioButton;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class RadioButtonComparison extends TitledCard
{
    private static final String TITLE = "Radio Buttons";

    public RadioButtonComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        ColumnConstraints rightConstraint = new ColumnConstraints();
        rightConstraint.setFillWidth(true);
        rightConstraint.setPercentWidth(50);
        ColumnConstraints leftConstraint = new ColumnConstraints();
        leftConstraint.setFillWidth(true);
        leftConstraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(leftConstraint, leftConstraint);

        Label rtLabel = new Label("RT-FX Radio Buttons", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Radio Buttons", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("-Animation (Ripple)");
        descriptionPane.addLine("-Text Style");
        descriptionPane.addLine("-Unselected Radio Color");

        int row = 0;
        javafx.scene.control.RadioButton fxComponent = new javafx.scene.control.RadioButton("Radio Button");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,             0, row++, 2, 1);
        grid.add(rtLabel,                 0, row  , 1, 1);
        grid.add(jfxLabel,                1, row++, 1, 1);
        grid.add(createRtRadioButtons(),  0, row  , 1, 1);
        grid.add(createJfxRadioButtons(), 1, row++, 1, 1);
        grid.add(descriptionPane,         0, row++, 2, 1);
        setContent(grid);
    }

    public Node createRtRadioButtons()
    {
        VBox box = new VBox();
        ToggleGroup rtRadio = new ToggleGroup();
        RadioButton rtUnselected = new RadioButton("Radio 1");
        rtUnselected.setToggleGroup(rtRadio);
        RadioButton rtSelected = new RadioButton("Radio 2");
        rtSelected.setSelected(true);
        rtSelected.setToggleGroup(rtRadio);
        RadioButton rtDisabled = new RadioButton("Radio 3");
        rtDisabled.setDisable(true);
        rtDisabled.setToggleGroup(rtRadio);

        box.getChildren().add(rtUnselected);
        box.getChildren().add(rtSelected);
        box.getChildren().add(rtDisabled);
        return box;
    }

    public Node createJfxRadioButtons()
    {
        VBox box = new VBox();
        ToggleGroup jfxRadio = new ToggleGroup();
        JFXRadioButton jfxUnselected = new JFXRadioButton("Radio 1");
        jfxUnselected.setToggleGroup(jfxRadio);
        JFXRadioButton jfxSelected = new JFXRadioButton("Radio 2");
        jfxSelected.setSelected(true);
        jfxSelected.setToggleGroup(jfxRadio);
        JFXRadioButton jfxDisabled = new JFXRadioButton("Radio 3");
        jfxDisabled.setDisable(true);
        jfxDisabled.setToggleGroup(jfxRadio);

        box.getChildren().add(jfxUnselected);
        box.getChildren().add(jfxSelected);
        box.getChildren().add(jfxDisabled);
        return box;
    }
}
