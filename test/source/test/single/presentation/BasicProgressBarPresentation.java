package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicProgressBarPresentation extends TitledCard
{
    private static final String TITLE = "Progress Bars";
    
    public BasicProgressBarPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createProgressPane());
        
        setContent(stackPane);
    }
    
    private Node createProgressPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Progress can be indicated in two styles: a bar and a wheel. Each type have an indeterminate state.");
        
        GridPane gridPane = new GridPane();
        
        Label progress20Label = new Label("20% Progress");
        ProgressBar progress20 = new ProgressBar(0.20);
        GridPane.setHgrow(progress20, Priority.ALWAYS);
        Label progress75Label = new Label("75% Progress");
        ProgressBar progress75 = new ProgressBar(0.75);
        Label progress100Label = new Label("100% Progress");
        ProgressBar progress100 = new ProgressBar(1.0);
        
        gridPane.add(progress20Label, 0, 0);
        gridPane.add(progress20, 1, 0);
        gridPane.add(progress75Label, 0, 1);
        gridPane.add(progress75, 1, 1);
        gridPane.add(progress100Label, 0, 2);
        gridPane.add(progress100, 1, 2);
        
        descriptionPane.setContent(gridPane);
        
        return descriptionPane;
    }
}
