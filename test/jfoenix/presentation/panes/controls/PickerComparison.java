package test.jfoenix.presentation.panes.controls;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.IconColorPicker;
import mil.af.eglin.ccf.rt.fx.control.IconColorPicker2;
import mil.af.eglin.ccf.rt.fx.control.DatePicker;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class PickerComparison extends TitledCard
{
    private static final String TITLE = "Pickers";

    public PickerComparison(PaneController controller)
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

        Label rtLabel = new Label("RT-FX Color Pickers", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Color Pickers", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane colorDescription = new DescriptionPane();
        colorDescription.addLine("-Style");

        Label rtTimeLabel = new Label("RT-FX Date/Time Pickers", LabelStyle.LARGE);
        rtTimeLabel.setAlignment(Pos.CENTER);
        rtTimeLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxTimeLabel = new Label("JFeonix Date/Time Pickers", LabelStyle.LARGE);
        jfxTimeLabel.setAlignment(Pos.CENTER);
        jfxTimeLabel.setMaxWidth(Double.MAX_VALUE);
        DescriptionPane timeDescription = new DescriptionPane();
        timeDescription.addLine("-Style");
        timeDescription.addLine("-Time Picker");

        int row = 0;
        javafx.scene.control.ColorPicker fxColor = new javafx.scene.control.ColorPicker();
        fxColor.setPromptText("JavaFX ComboBox");
        GridPane.setHalignment(fxColor, HPos.CENTER);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("JavaFX Choice Box");
        javafx.scene.control.DatePicker fxDate = new javafx.scene.control.DatePicker();
        GridPane.setHalignment(fxColor, HPos.CENTER);
        
        grid.add(fxColor,         0, row, 1, 1);
        grid.add(fxDate,   1, row++, 1, 1);
        
        grid.add(rtLabel, 0, row, 1, 1);
        grid.add(jfxLabel, 1, row++, 1, 1);
        grid.add(createRtColorPicker(), 0, row, 1, 1);
        grid.add(createJfxColorPicker(), 1, row++, 1, 1);
        grid.add(colorDescription, 0, row++, 2, 1);

        grid.add(rtTimeLabel, 0, row, 1, 1);
        grid.add(jfxTimeLabel, 1, row++, 1, 1);
        grid.add(createRtDatePicker(), 0, row, 1, 1);
        grid.add(createJfxDatePicker(), 1, row++, 1, 1);
        grid.add(timeDescription, 0, row++, 2, 1);
        setContent(grid);
    }

    public Node createRtColorPicker()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        ColorPicker rtUninitialized = new ColorPicker();
        ColorPicker rtInitialized = new ColorPicker();
        rtInitialized.setValue(Color.RED);

        IconColorPicker rtIconUninitialized = new IconColorPicker();
        IconColorPicker rtIconInitialized = new IconColorPicker();
        rtIconInitialized.setValue(Color.SANDYBROWN);

        IconColorPicker2 rtIcon = new IconColorPicker2();
        rtIcon.setValue(Color.TEAL);
        
        box.getChildren().add(rtUninitialized);
        box.getChildren().add(rtInitialized);
        box.getChildren().add(rtIconUninitialized);
        box.getChildren().add(rtIconInitialized);
        box.getChildren().add(rtIcon);
        return box;
    }

    public Node createJfxColorPicker()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        JFXColorPicker jfxUninitialized = new JFXColorPicker();
        JFXColorPicker jfxInitialized = new JFXColorPicker();
        jfxInitialized.setValue(Color.RED);
        box.getChildren().add(jfxUninitialized);
        box.getChildren().add(jfxInitialized);
        return box;
    }

    public Node createRtDatePicker()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        DatePicker rtDateUninitialized = new DatePicker();
        DatePicker rtDateInitialized = new DatePicker();
        rtDateInitialized.setValue(LocalDate.now());
        box.getChildren().add(rtDateUninitialized);
        box.getChildren().add(rtDateInitialized);
        return box;
    }

    public Node createJfxDatePicker()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        JFXDatePicker jfxDateUninitialized = new JFXDatePicker();
        JFXDatePicker jfxDateInitialized = new JFXDatePicker();
        JFXTimePicker jfxTimeInitialized = new JFXTimePicker();
        jfxTimeInitialized.setValue(LocalTime.now());
        jfxDateInitialized.setValue(LocalDate.now());
        box.getChildren().add(jfxDateUninitialized);
        box.getChildren().add(jfxDateInitialized);
        box.getChildren().add(jfxTimeInitialized);
        return box;
    }
}
