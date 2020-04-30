package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class ButtonComparison extends TitledCard
{
    private static final String TITLE = "Buttons";
    
    public ButtonComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        grid.setHgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        ColumnConstraints rightConstraint = new ColumnConstraints();
        rightConstraint.setFillWidth(true);
        rightConstraint.setPercentWidth(50);
        ColumnConstraints leftConstraint = new ColumnConstraints();
        leftConstraint.setFillWidth(true);
        leftConstraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(leftConstraint, leftConstraint);

        DescriptionPane flatDescription = new DescriptionPane();
        flatDescription.addLine("-Animation");
        flatDescription.addLine("-Corner Radius");
        flatDescription.addLine("-Hover State");
        flatDescription.addLine("-Cursor");
        flatDescription.addLine("-Stretch");
        Label rtFlatLabel = new Label("RT-FX Flat Buttons", LabelStyle.LARGE);
        rtFlatLabel.setAlignment(Pos.CENTER);
        rtFlatLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxFlatLabel = new Label("JFeonix Flat Buttons", LabelStyle.LARGE);
        jfxFlatLabel.setAlignment(Pos.CENTER);
        jfxFlatLabel.setMaxWidth(Double.MAX_VALUE);

        DescriptionPane raisedDescription = new DescriptionPane();
        raisedDescription.addLine("-Animation");
        raisedDescription.addLine("-Corner Radius");
        raisedDescription.addLine("-Drop Shadow");
        raisedDescription.addLine("-Hover State");
        raisedDescription.addLine("-Cursor");
        Label rtRaiedLabel = new Label("RT-FX Raised Buttons", LabelStyle.LARGE);
        rtRaiedLabel.setAlignment(Pos.CENTER);
        rtRaiedLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxRaisedLabel = new Label("JFeonix Raised Buttons", LabelStyle.LARGE);
        jfxRaisedLabel.setAlignment(Pos.CENTER);
        jfxRaisedLabel.setMaxWidth(Double.MAX_VALUE);
        
        int row = 0;
        javafx.scene.control.Button fxComponent = new javafx.scene.control.Button("JavaFX Button");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,        0, row++, 2, 1);
        grid.add(rtFlatLabel,        0, row  , 1, 1);
        grid.add(jfxFlatLabel,       1, row++, 1, 1);
        grid.add(createRtButtons(),  0, row  , 1, 1);
        grid.add(createJfxButtons(), 1, row++, 1, 1);
        grid.add(flatDescription,    0, row++, 2, 1);

        grid.add(rtRaiedLabel,             0, row  , 1, 1);
        grid.add(jfxRaisedLabel,           1, row++, 1, 1);
        grid.add(createRtRaisedButtons(),  0, row  , 1, 1);
        grid.add(createJfxRaisedButtons(), 1, row++, 1, 1);
        grid.add(raisedDescription,        0, row++, 2, 1);
        
        setContent(grid);
    }
    
    public Node createRtButtons()
    {
        HBox box = new HBox();
        box.getChildren().add(new Button("BUTTON", ButtonStyle.FLAT));
        box.getChildren().add(new Button("ACCENTED", ButtonStyle.FLAT, Accent.PRIMARY_MID));
        Button rtDisabledButton = new Button("DISABLED", ButtonStyle.FLAT);
        rtDisabledButton.setDisable(true);
        box.getChildren().add(rtDisabledButton);
        return box;
    }
    
    public Node createJfxButtons()
    {
        HBox box = new HBox();
        JFXButton jfxButton = new JFXButton("BUTTON");
        jfxButton.getStyleClass().add("button-flat");
        JFXButton jfxAccentedButton = new JFXButton("ACCENTED");
        jfxAccentedButton.getStyleClass().add("button-flat");
        jfxAccentedButton.setStyle("-fx-text-fill:#414f63");
        JFXButton jfxDisabledButton = new JFXButton("DISABLED");
        jfxDisabledButton.setDisable(true);
        
        box.getChildren().add(jfxButton);
        box.getChildren().add(jfxAccentedButton);
        box.getChildren().add(jfxDisabledButton);
        return box;
    }
    
    public Node createRtRaisedButtons()
    {
        HBox box = new HBox();
        box.setSpacing(20);
        
        box.getChildren().add(new Button("BUTTON", ButtonStyle.RAISED, Accent.BASE_MID));
        box.getChildren().add(new Button("ACCENTED", ButtonStyle.RAISED, Accent.PRIMARY_MID));
        Button rtDisabledButton = new Button("DISABLED", ButtonStyle.RAISED, Accent.BASE_MID);
        rtDisabledButton.setDisable(true);
        box.getChildren().add(rtDisabledButton);
        return box;
    }
    
    public Node createJfxRaisedButtons()
    {
        HBox box = new HBox();
        box.setSpacing(20);
        
        JFXButton jfxButton = new JFXButton("BUTTON");
        jfxButton.setStyle("-fx-background-color:WHITE;-fx-text-fill:BLACK");
        jfxButton.getStyleClass().add("button-raised");
        JFXButton jfxAccentedButton = new JFXButton("ACCENTED");
        jfxAccentedButton.getStyleClass().add("button-raised");
        jfxAccentedButton.setStyle("-fx-background-color:#414f63;-fx-text-fill:WHITE");
        JFXButton jfxDisabledButton = new JFXButton("DISABLED");
        jfxDisabledButton.getStyleClass().add("button-raised");
        jfxDisabledButton.setDisable(true);
        
        box.getChildren().add(jfxButton);
        box.getChildren().add(jfxAccentedButton);
        box.getChildren().add(jfxDisabledButton);
        return box;
    }
}
