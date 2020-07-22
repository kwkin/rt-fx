package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class ToggleSwitchComparison extends SizedTitledCard
{
    private static final String TITLE = "Toggle Buttons";

    public ToggleSwitchComparison(PaneController controller)
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

        Label rtLabel = new Label("RT-FX Toggle Switch", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Toggle Switch", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("-Animation");
        descriptionPane.addLine("-Style (dot size, drop shadow)");
        descriptionPane.addLine("-Padding");

        Label rtIconLabel = new Label("RT-FX Icon Toggle Button", LabelStyle.LARGE);
        rtIconLabel.setAlignment(Pos.CENTER);
        rtIconLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxIconLabel = new Label("JFeonix Icon Toggle Button", LabelStyle.LARGE);
        jfxIconLabel.setAlignment(Pos.CENTER);
        jfxIconLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane descriptionIconPane = new DescriptionPane();
        descriptionIconPane.addLine("-Animation");
        descriptionIconPane.addLine("-Highlight icon when selected");
        descriptionIconPane.addLine("-Show glow when selected");
        
        int row = 0;
        javafx.scene.control.ToggleButton fxComponent = new javafx.scene.control.ToggleButton("JavaFX Toggle Button");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,                0, row++, 2, 1);
        grid.add(rtLabel,                 0, row  , 1, 1);
        grid.add(jfxLabel,                1, row++, 1, 1);
        grid.add(createRtToggleSwitch(),  0, row  , 1, 1);
        grid.add(createJfxToggleSwitch(), 1, row++, 1, 1);
        grid.add(descriptionPane,         0, row++, 2, 1);

        grid.add(rtIconLabel,             0, row  , 1, 1);
        grid.add(jfxIconLabel,            1, row++, 1, 1);
        grid.add(createRtIconButton(),    0, row  , 1, 1);
        grid.add(createJfxIconButton(),   1, row++, 1, 1);
        grid.add(descriptionIconPane,     0, row++, 2, 1);
        
        setContent(grid);
    }

    public Node createRtToggleSwitch()
    {
        VBox box = new VBox();
        ToggleSwitch rtUnselected = new ToggleSwitch("Unselected");
        ToggleSwitch rtSelected = new ToggleSwitch("Selected");
        rtSelected.setSelected(true);
        ToggleSwitch rtDisabled = new ToggleSwitch("Unselected");
        rtDisabled.setDisable(true);
        box.getChildren().add(rtUnselected);
        box.getChildren().add(rtSelected);
        box.getChildren().add(rtDisabled);
        return box;
    }
    
    public Node createJfxToggleSwitch()
    {
        VBox box = new VBox();
        JFXToggleButton jfxUnselected = new JFXToggleButton();
        jfxUnselected.setText("Unselected");
        JFXToggleButton jfxSelected = new JFXToggleButton();
        jfxSelected.setText("Selected");
        jfxSelected.setSelected(true);
        JFXToggleButton jfxDisabled = new JFXToggleButton();
        jfxDisabled.setText("Disabled");
        jfxDisabled.setDisable(true);
        box.getChildren().add(jfxUnselected);
        box.getChildren().add(jfxSelected);
        box.getChildren().add(jfxDisabled);
        return box;
    }

    public Node createRtIconButton()
    {
        VBox box = new VBox();
        SvgIcon smilyOn = new SvgIcon(SvgFile.EMOTICON_EXCITED, IconSize.SIZE_32);
        SvgIcon smilyOff = new SvgIcon(SvgFile.EMOTICON_EXCITED_OUTLINE, IconSize.SIZE_32);
        IconToggleButton smiley = new IconToggleButton(smilyOn, smilyOff);
        
        SvgIcon eyeOn = new SvgIcon(SvgFile.EYE, IconSize.SIZE_32);
        SvgIcon eyeOff = new SvgIcon(SvgFile.EYE_OUTLINE, IconSize.SIZE_32);
        IconToggleButton rtPalette = new IconToggleButton(eyeOn, eyeOff);
        rtPalette.setText("Normal");

        SvgIcon mapAlert = new SvgIcon(SvgFile.MAP_MARKER_ALERT, IconSize.SIZE_32);
        SvgIcon mapAlertCircle = new SvgIcon(SvgFile.MAP_MARKER_ALERT_OUTLINE, IconSize.SIZE_32);
        IconToggleButton rtMapAlert = new IconToggleButton(mapAlert, mapAlertCircle, IconToggleButtonStyle.ACCENTED_ICON);
        rtMapAlert.setText("Highlighted");

        SvgIcon shieldAlert = new SvgIcon(SvgFile.SHIELD_ALERT, IconSize.SIZE_32);
        SvgIcon shieldAlertOutline = new SvgIcon(SvgFile.SHIELD_ALERT_OUTLINE, IconSize.SIZE_32);
        IconToggleButton rtToggle = new IconToggleButton(shieldAlert, shieldAlertOutline, IconToggleButtonStyle.GLOWING_ICON);
        rtToggle.setText("Glowing");

        SvgIcon alertCircle = new SvgIcon(SvgFile.ALERT_CIRCLE, IconSize.SIZE_32);
        SvgIcon alertCircleOutline = new SvgIcon(SvgFile.ALERT_CIRCLE_OUTLINE, IconSize.SIZE_32);
        IconToggleButton rtToggleIconGlow = new IconToggleButton(alertCircle, alertCircleOutline, IconToggleButtonStyle.GLOWING_ICON);

        box.getChildren().add(smiley);
        box.getChildren().add(rtPalette);
        box.getChildren().add(rtMapAlert);
        box.getChildren().add(rtToggle);
        box.getChildren().add(rtToggleIconGlow);
        return box;
    }
    
    public Node createJfxIconButton()
    {
        VBox box = new VBox();
        JFXToggleNode jfxUnselected = new JFXToggleNode();
        jfxUnselected.setGraphic(new SvgIcon(SvgFile.MAP_MARKER_ALERT, IconSize.SIZE_32));

        JFXToggleNode jfxGlowing = new JFXToggleNode();
        jfxGlowing.setGraphic(new SvgIcon(SvgFile.SHIELD_ALERT, IconSize.SIZE_32));
        jfxGlowing.setText("Glowing");
        
        box.getChildren().add(jfxUnselected);
        box.getChildren().add(jfxGlowing);
        return box;
    }
}
