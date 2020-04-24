package test.sample.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.Spinner;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class SpinnerPanePresentation extends TitledCard
{
    private static final String TITLE = "Spinners";
    
    public SpinnerPanePresentation(PaneController controller)
    {
        super(TITLE);

        VBox stackPane = new VBox();

        stackPane.getChildren().add(createSpinnerPane());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createAccentSpinnerPane());
        
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
    
    private Node createAccentSpinnerPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Spinners can also be accented.");

        GridPane spinnerGridPane = new GridPane();
        SpinnerValueFactory<Integer> integerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-10, 10, -5);
        Spinner<Integer> primaryAccentLightStepper = new Spinner<Integer>(integerFactory, Accent.PRIMARY_MID);
        SpinnerValueFactory<Double> doubleFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-10.0, 10.0, -5.75);
        Spinner<Double> primaryAccentMidStepper = new Spinner<Double>(doubleFactory, Accent.PRIMARY_MID);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Tier 1", "Tier 2", "Tier 3", "Tier 4", "Tier 5");
        SpinnerValueFactory<String> listFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(items);
        listFactory.setValue("Tier 3");
        Spinner<String> primaryAccentDarkStepper = new Spinner<String>(listFactory, Accent.PRIMARY_MID);

        Spinner<Integer> secondaryAccentLightComboBox = new Spinner<Integer>(integerFactory, Accent.SECONDARY_LIGHT);
        Spinner<Double> secondaryAccentMidComboBox = new Spinner<Double>(doubleFactory, Accent.SECONDARY_MID);
        Spinner<String> secondaryAccentDarkComboBox = new Spinner<String>(listFactory, Accent.SECONDARY_DARK);
        
        spinnerGridPane.add(primaryAccentLightStepper, 0, 0);
        spinnerGridPane.add(primaryAccentMidStepper, 0, 1);
        spinnerGridPane.add(primaryAccentDarkStepper, 0, 2);
        
        spinnerGridPane.add(secondaryAccentLightComboBox, 1, 0);
        spinnerGridPane.add(secondaryAccentMidComboBox, 1, 1);
        spinnerGridPane.add(secondaryAccentDarkComboBox, 1, 2);

        descriptionPane.setContent(spinnerGridPane);
        
        return descriptionPane;
    }
}
