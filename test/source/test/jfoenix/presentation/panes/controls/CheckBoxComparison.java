package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXCheckBox;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class CheckBoxComparison extends SizedTitledCard
{
    private static final String TITLE = "Check Boxes";

    public CheckBoxComparison(PaneController controller)
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
        
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("-Animation (Ripple, Hover State)");
        descriptionPane.addLine("-Text Style");
        descriptionPane.addLine("-Mark Style");
        descriptionPane.addLine("-Indeterminate Style (outline color)");
        descriptionPane.addLine("-Hover State");
        descriptionPane.addLine("-Cursor");
        
        Label rtLabel = new Label("RT-FX Checkboxes", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Checkboxes", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);

        int row = 0;
        javafx.scene.control.CheckBox fxComponent = new javafx.scene.control.CheckBox("JavaFX CheckBox");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,           0, row++, 2, 1);
        grid.add(rtLabel,               0, row  , 1, 1);
        grid.add(jfxLabel,              1, row++, 1, 1);
        grid.add(createRtCheckBoxes(),  0, row  , 1, 1);
        grid.add(createJfxCheckBoxes(), 1, row++, 1, 1);
        grid.add(descriptionPane,       0, row++, 2, 1);
        
        setContent(grid);
    }

    public Node createRtCheckBoxes()
    {
        VBox box = new VBox();
        box.setSpacing(20);
        
        CheckBox rtUnselectedCheckBox = new CheckBox("Unselected", Accent.PRIMARY_MID);
        CheckBox rtSelectedCheckBox = new CheckBox("Selected", Accent.PRIMARY_MID);
        rtSelectedCheckBox.setSelected(true);
        CheckBox rtIndeterminateCheckBox = new CheckBox("Indeterminate", Accent.PRIMARY_MID);
        rtIndeterminateCheckBox.setAllowIndeterminate(true);
        rtIndeterminateCheckBox.setIndeterminate(true);
        CheckBox rtDisabledCheckBox = new CheckBox("Disabled", Accent.PRIMARY_MID);
        rtDisabledCheckBox.setDisable(true);

        box.getChildren().add(rtUnselectedCheckBox);
        box.getChildren().add(rtSelectedCheckBox);
        box.getChildren().add(rtIndeterminateCheckBox);
        box.getChildren().add(rtDisabledCheckBox);
        
        return box;
    }
    
    public Node createJfxCheckBoxes()
    {
        VBox box = new VBox();
        box.setSpacing(20);
        
        JFXCheckBox jfxUnselectedCheckBox = new JFXCheckBox("Unselected");
        JFXCheckBox jfxSelectedCheckBox = new JFXCheckBox("Selected");
        jfxSelectedCheckBox.setSelected(true);
        JFXCheckBox jfxIndeterminateCheckBox = new JFXCheckBox("Indeterminate");
        jfxIndeterminateCheckBox.setAllowIndeterminate(true);
        jfxIndeterminateCheckBox.setIndeterminate(true);
        JFXCheckBox jfxDisabledCheckBox = new JFXCheckBox("Disabled");
        jfxDisabledCheckBox.setDisable(true);
        
        box.getChildren().add(jfxUnselectedCheckBox);
        box.getChildren().add(jfxSelectedCheckBox);
        box.getChildren().add(jfxIndeterminateCheckBox);
        box.getChildren().add(jfxDisabledCheckBox);
        
        return box;
    }
}
