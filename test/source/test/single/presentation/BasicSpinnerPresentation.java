package test.single.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicSpinnerPresentation extends TitledCard
{
    private static final String TITLE = "Spinners";
    
    public BasicSpinnerPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();

        stackPane.getChildren().add(createSpinnerPane());
        
        setContent(stackPane);
    }

    private Node createSpinnerPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Spinners are numeric text boxes with an increment and decrement button. Various data types can be represented in a numeric text box.");

        GridPane spinnerGridPane = new GridPane();
        SpinnerValueFactory<Integer> integerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-10, 10, 1);
        Spinner<Integer> primaryLightStepper = new Spinner<Integer>(integerFactory);
        SpinnerValueFactory<Double> doubleFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-10.0, 10.0, 2.5);
        Spinner<Double> primaryMidStepper = new Spinner<Double>(doubleFactory);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Tier 1", "Tier 2", "Tier 3", "Tier 4", "Tier 5");
        SpinnerValueFactory<String> listFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(items);
        Spinner<String> primaryDarkStepper = new Spinner<String>(listFactory);

        Spinner<Integer> secondaryLightComboBox = new Spinner<Integer>(integerFactory);
        Spinner<Double> secondaryMidComboBox = new Spinner<Double>(doubleFactory);
        Spinner<String> secondaryDarkComboBox = new Spinner<String>(listFactory);
        
        spinnerGridPane.add(primaryLightStepper, 0, 0);
        spinnerGridPane.add(primaryMidStepper, 0, 1);
        spinnerGridPane.add(primaryDarkStepper, 0, 2);
        
        spinnerGridPane.add(secondaryLightComboBox, 1, 0);
        spinnerGridPane.add(secondaryMidComboBox, 1, 1);
        spinnerGridPane.add(secondaryDarkComboBox, 1, 2);

        descriptionPane.setContent(spinnerGridPane);
        
        return descriptionPane;
    }
}
