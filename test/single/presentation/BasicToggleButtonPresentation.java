package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicToggleButtonPresentation extends TitledCard
{
    private static final String TITLE = "Toggle Buttons";
    
    public BasicToggleButtonPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);
        
        VBox stackPane = new VBox();
        stackPane.getChildren().add(createToggleButtons());
        
        setContent(stackPane);
    }
    
    private Node createToggleButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle buttons are available in several varieties: raised, flat, icon, and icon with text. Here are some raised toggle buttons.");

        GridPane toggleButtonGridPane = new GridPane();

        ToggleButton primaryLightToggleButton = new ToggleButton("TOGGLE");
        ToggleButton primaryMidToggleButton = new ToggleButton("TOGGLE");
        ToggleButton primaryDarkToggleButton = new ToggleButton("TOGGLE");

        toggleButtonGridPane.add(primaryLightToggleButton, 0, 0);
        toggleButtonGridPane.add(primaryMidToggleButton, 1, 0);
        toggleButtonGridPane.add(primaryDarkToggleButton, 2, 0);
        
        descriptionPane.setContent(toggleButtonGridPane);
        
        return descriptionPane;
    }
}
