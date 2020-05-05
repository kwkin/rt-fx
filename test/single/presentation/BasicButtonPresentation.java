package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.Button;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicButtonPresentation extends TitledCard
{
    private static final String TITLE = "Buttons";
    
    public BasicButtonPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        vBox.getChildren().add(createNormalButtons());
        
        setContent(vBox);
    }

    private Node createNormalButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Normal buttons come in several varieties: raised, flat, icon, and icon with text. Here are some raised buttons.");

        VBox buttonVBox = new VBox();
        HBox primaryButtonPane = new HBox();
        Button primaryLightButton = new Button("OK");
        Button primaryMidButton = new Button("CANCEL");
        Button primaryDarkButton = new Button("APPLY");
        primaryButtonPane.getChildren().add(primaryLightButton);
        primaryButtonPane.getChildren().add(primaryMidButton);
        primaryButtonPane.getChildren().add(primaryDarkButton);

        buttonVBox.getChildren().add(primaryButtonPane);
        
        descriptionPane.setContent(buttonVBox);
        
        return descriptionPane;
    }
}
