package test.demo.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.control.Spinner;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class SpinnerPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Spinners";
    
    public SpinnerPanePresentation(PaneController controller)
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
        Spinner<Integer> primaryLightStepper = new Spinner<Integer>(integerFactory, Accent.PRIMARY_LIGHT);
        SpinnerValueFactory<Double> doubleFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-10.0, 10.0, 2.5);
        Spinner<Double> primaryMidStepper = new Spinner<Double>(doubleFactory, Accent.PRIMARY_MID);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Tier 1", "Tier 2", "Tier 3", "Tier 4", "Tier 5");
        SpinnerValueFactory<String> listFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(items);
        Spinner<String> primaryDarkStepper = new Spinner<String>(listFactory, Accent.PRIMARY_DARK);

        Spinner<Integer> secondaryLightComboBox = new Spinner<Integer>(integerFactory, Accent.SECONDARY_LIGHT);
        Spinner<Double> secondaryMidComboBox = new Spinner<Double>(doubleFactory, Accent.SECONDARY_MID);
        Spinner<String> secondaryDarkComboBox = new Spinner<String>(listFactory, Accent.SECONDARY_DARK);
        
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
