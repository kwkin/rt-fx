package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXSlider;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.Slider;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class SliderComparison extends SizedTitledCard
{
    private static final String TITLE = "Sliders";

    public SliderComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x3);

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
        
        Label rtHorizontalLabel = new Label("RT-FX Sliders", LabelStyle.LARGE);
        rtHorizontalLabel.setAlignment(Pos.CENTER);
        rtHorizontalLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxHorizontalLabel = new Label("JFeonix Sldiers", LabelStyle.LARGE);
        jfxHorizontalLabel.setAlignment(Pos.CENTER);
        jfxHorizontalLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane description = new DescriptionPane();
        description.addLine("-Style (dot size, track size, drop shadow)");
        description.addLine("-Value Dialog");
        description.addLine("-Padding (leave solely to panes or add additional padding to component?)");
        
        int row = 0;
        javafx.scene.control.Slider fxComponent = new javafx.scene.control.Slider(-50, 50, 0);
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        javafx.scene.control.Slider fxComponentVertical = new javafx.scene.control.Slider(-50, 50, 0);
        GridPane.setHalignment(fxComponentVertical, HPos.CENTER);
        fxComponentVertical.setOrientation(Orientation.VERTICAL);
        grid.add(fxComponent,           0, row, 1, 1);
        grid.add(fxComponentVertical,   1, row++, 1, 1);
        
        grid.add(rtHorizontalLabel,     0, row  , 1, 1);
        grid.add(jfxHorizontalLabel,    1, row++, 1, 1);
        grid.add(createRtSliders(),     0, row  , 1, 1);
        grid.add(createJfxSliders(),    1, row++, 1, 1);
        grid.add(createRtVerticalSliders(),  0, row  , 1, 1);
        grid.add(createJfxVerticalSliders(), 1, row++, 1, 1);
        grid.add(description, 0, row++, 2, 1);
        setContent(grid);
    }

    public Node createRtSliders()
    {
        VBox box = new VBox();
        box.setSpacing(20);
        
        Slider rtSlider = new Slider(-50, 50, -25);
        Slider rtLabels =new Slider(-50, 50, 25);
        rtLabels.setShowTickLabels(true);
        rtLabels.setShowTickMarks(true);
        Slider rtDisabled = new Slider(-50, 50, 0);
        rtDisabled.setDisable(true);
        box.getChildren().add(rtSlider);
        box.getChildren().add(rtLabels);
        box.getChildren().add(rtDisabled);
        return box;
    }
    
    public Node createJfxSliders()
    {
        VBox box = new VBox();
        box.setSpacing(20);
        
        JFXSlider jfxSlider = new JFXSlider(-50, 50, -25);
        JFXSlider jfxLabels =new JFXSlider(-50, 50, 25);
        jfxLabels.setShowTickLabels(true);
        jfxLabels.setShowTickMarks(true);
        JFXSlider jfxDisabled = new JFXSlider(-50, 50, 0);
        jfxDisabled.setDisable(true);
        box.getChildren().add(jfxSlider);
        box.getChildren().add(jfxLabels);
        box.getChildren().add(jfxDisabled);
        return box;
    }

    public Node createRtVerticalSliders()
    {
        HBox box = new HBox();
        Slider rtSlider = new Slider(-50, 50, -25);
        rtSlider.setOrientation(Orientation.VERTICAL);
        Slider rtLabels =new Slider(-50, 50, 25);
        rtLabels.setShowTickLabels(true);
        rtLabels.setShowTickMarks(true);
        rtLabels.setOrientation(Orientation.VERTICAL);
        box.getChildren().add(rtSlider);
        box.getChildren().add(rtLabels);
        return box;
    }
    
    public Node createJfxVerticalSliders()
    {
        HBox box = new HBox();
        JFXSlider jfxSlider = new JFXSlider(-50, 50, -25);
        jfxSlider.setOrientation(Orientation.VERTICAL);
        JFXSlider jfxLabels =new JFXSlider(-50, 50, 25);
        jfxLabels.setShowTickLabels(true);
        jfxLabels.setShowTickMarks(true);
        jfxLabels.setOrientation(Orientation.VERTICAL);
        box.getChildren().add(jfxSlider);
        box.getChildren().add(jfxLabels);
        return box;
    }
}
