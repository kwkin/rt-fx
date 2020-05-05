package test.single.presentation;

import java.text.NumberFormat;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicSlidersPresentation extends TitledCard
{
    private static final String TITLE = "Sliders";
    
    public BasicSlidersPresentation()
    {
        super(TITLE);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createHorizontalSliders());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createVerticalSliders());
        
        setContent(stackPane);
    }
    
    private Node createHorizontalSliders()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Sliders can be oriented horizontally or vertically. Additionally, numbered tick labels can be displayed with the slider.");

        GridPane sliderGridPane = new GridPane();
        ColumnConstraints labelLayoutColumnConstraint = new ColumnConstraints();
        labelLayoutColumnConstraint.setMinWidth(50);
        labelLayoutColumnConstraint.setMaxWidth(50);
        labelLayoutColumnConstraint.setHalignment(HPos.RIGHT);
        labelLayoutColumnConstraint.setFillWidth(true);
        ColumnConstraints sliderColumnConstraint = new ColumnConstraints();
        sliderColumnConstraint.setFillWidth(true);
        sliderColumnConstraint.setHgrow(Priority.ALWAYS);
        
        sliderGridPane.getColumnConstraints().addAll(labelLayoutColumnConstraint, sliderColumnConstraint);
        
        Slider primaryLightSlider = new Slider(-50, 50, -25);
        primaryLightSlider.setShowTickMarks(true);
        Slider primaryMidSlider = new Slider(-50, 50, 25);
        Slider primaryDarkSlider = new Slider(-50, 50, 0);
        primaryDarkSlider.setShowTickMarks(true);
        primaryDarkSlider.setShowTickLabels(true);

        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(0);
        StringConverter<Number> converter = new NumberStringConverter(formatter);

        ColumnConstraints labelColumnConstraint = new ColumnConstraints();
        labelColumnConstraint.setHalignment(HPos.RIGHT);
        labelColumnConstraint.setFillWidth(true);
        
        GridPane primaryLightGridPane = new GridPane();
        primaryLightGridPane.setPadding(new Insets(0));
        primaryLightGridPane.setVgap(0);
        primaryLightGridPane.getColumnConstraints().add(labelColumnConstraint);
        Label primaryLightLabel = new Label(primaryLightSlider.valueProperty().toString());
        primaryLightLabel.textProperty().bindBidirectional(primaryLightSlider.valueProperty(), converter);
        Label primaryLightValueLabel = new Label("Value");
        primaryLightGridPane.add(primaryLightLabel, 0, 0);
        primaryLightGridPane.add(primaryLightValueLabel, 0, 1);

        GridPane primaryMidGridPane = new GridPane();
        primaryMidGridPane.setPadding(new Insets(0));
        primaryMidGridPane.setVgap(0);
        primaryMidGridPane.getColumnConstraints().add(labelColumnConstraint);
        Label primaryMidLabel = new Label(primaryMidSlider.valueProperty().toString());
        primaryMidLabel.textProperty().bindBidirectional(primaryMidSlider.valueProperty(), converter);
        Label primaryMidValueLabel = new Label("Value");
        primaryMidGridPane.add(primaryMidLabel, 0, 0);
        primaryMidGridPane.add(primaryMidValueLabel, 0, 1);

        GridPane primaryDarkGridPane = new GridPane();
        primaryDarkGridPane.setPadding(new Insets(0));
        primaryDarkGridPane.setVgap(0);
        primaryDarkGridPane.getColumnConstraints().add(labelColumnConstraint);
        Label primaryDarkLabel = new Label(primaryDarkSlider.valueProperty().toString());
        primaryDarkLabel.textProperty().bindBidirectional(primaryDarkSlider.valueProperty(), converter);
        Label primaryDarkValueLabel = new Label("Value");
        primaryDarkGridPane.add(primaryDarkLabel, 0, 0);
        primaryDarkGridPane.add(primaryDarkValueLabel, 0, 1);

        sliderGridPane.add(primaryLightGridPane, 0, 0, 1, 2);
        sliderGridPane.add(primaryMidGridPane, 0, 2, 1, 2);
        sliderGridPane.add(primaryDarkGridPane, 0, 4, 1, 2);
        
        sliderGridPane.add(primaryLightSlider, 1, 0, 1, 2);
        sliderGridPane.add(primaryMidSlider, 1, 2, 1, 2);
        sliderGridPane.add(primaryDarkSlider, 1, 4, 1, 2);
        
        descriptionPane.setContent(sliderGridPane);
        
        return descriptionPane;
    }
    
    private Node createVerticalSliders()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Sliders may also be accented with primary or secondary colors.");

        HBox sliderHBox = new HBox();
        Slider secondaryLightSlider = new Slider(-50, 50, -25);
        secondaryLightSlider.setShowTickMarks(true);
        secondaryLightSlider.setOrientation(Orientation.VERTICAL);
        Slider secondaryMidSlider = new Slider(-50, 50, 25);
        secondaryMidSlider.setOrientation(Orientation.VERTICAL);
        Slider secondaryDarkSlider = new Slider(-50, 50, 0);
        secondaryDarkSlider.setShowTickMarks(true);
        secondaryDarkSlider.setShowTickLabels(true);
        secondaryDarkSlider.setOrientation(Orientation.VERTICAL);
        
        sliderHBox.getChildren().add(secondaryLightSlider);
        sliderHBox.getChildren().add(secondaryMidSlider);
        sliderHBox.getChildren().add(secondaryDarkSlider);
        
        descriptionPane.setContent(sliderHBox);
        
        return descriptionPane;
    }
}