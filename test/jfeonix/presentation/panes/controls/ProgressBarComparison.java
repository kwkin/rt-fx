package test.jfeonix.presentation.panes.controls;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.ProgressBar;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class ProgressBarComparison extends TitledCard
{
    private static final String TITLE = "Progress Bar Pane";
    
    public ProgressBarComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        ColumnConstraints constraint = new ColumnConstraints();
        constraint.setFillWidth(true);
        constraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(constraint, constraint);

        Label rtLabel = new Label("RT-FX Progress Bars", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Progress Bars", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("-Style");
        descriptionPane.addLine("-Spinner");

        int row = 0;
        javafx.scene.control.ProgressBar fxComponent = new javafx.scene.control.ProgressBar(0.5);
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,           0, row++, 2, 1);
        grid.add(rtLabel, 0, row, 1, 1);
        grid.add(jfxLabel, 1, row++, 1, 1);
        grid.add(createRtProgressPane(), 0, row, 1, 1);
        grid.add(createJfxProgressPane(), 1, row++, 1, 1);
        grid.add(descriptionPane, 0, row++, 2, 1);
        setContent(grid);
    }

    public Node createRtProgressPane()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        ProgressBar progress25 = new ProgressBar(0.25);
        progress25.setMinWidth(200);
        ProgressBar progress75 = new ProgressBar(0.75);
        progress75.setMinWidth(200);
        ProgressBar progressIndeterminate = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndeterminate.setMinWidth(200);
        
        box.getChildren().add(progress25);
        box.getChildren().add(progress75);
        box.getChildren().add(progressIndeterminate);
        return box;
    }

    public Node createJfxProgressPane()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        JFXProgressBar progress25 = new JFXProgressBar(0.25);
        progress25.setMinWidth(200);
        JFXProgressBar progress75 = new JFXProgressBar(0.75);
        progress75.setMinWidth(200);
        JFXProgressBar progressIndeterminate = new JFXProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndeterminate.setMinWidth(200);
        
        JFXSpinner spinner25 = new JFXSpinner(0.25);
        JFXSpinner spinner75 = new JFXSpinner(0.75);
        JFXSpinner spinnerIndeterminate = new JFXSpinner(ProgressBar.INDETERMINATE_PROGRESS);
        
        box.getChildren().add(progress25);
        box.getChildren().add(progress75);
        box.getChildren().add(progressIndeterminate);
        box.getChildren().add(spinner25);
        box.getChildren().add(spinner75);
        box.getChildren().add(spinnerIndeterminate);
        return box;
    }
}