package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Accordion;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class TitledPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Titled Pane";
    
    public TitledPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        stackPane.getChildren().add(createTitledPane());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createAccordion());
        
        setContent(stackPane);
    }
    
    private Node createTitledPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("A titled pane is a panel with a titled that can be opened and closed.");
        
        VBox vBox = new VBox();
        Button secondaryMidRaisedButton = new Button("BUTTON 1", ButtonStyle.RAISED, Accent.SECONDARY_MID);

        TitledPane titledPane = new TitledPane("Titled Pane", vBox);
        vBox.getChildren().add(secondaryMidRaisedButton);
        
        titledPane.setContent(vBox);
        descriptionPane.setContent(titledPane);
        
        return descriptionPane;
    }
    
    private Node createAccordion()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("An accordion is composed of several titled panes.");
        
        
        CheckBox secondaryLightCheckBox = new CheckBox();
        secondaryLightCheckBox.setSelected(true);
        TitledPane titled1 = new TitledPane("Titled 1", secondaryLightCheckBox);
        
        CheckBox secondaryMidCheckBox = new CheckBox("Two State");
        secondaryMidCheckBox.setSelected(false);
        TitledPane titled2 = new TitledPane("Titled 2", secondaryMidCheckBox);

        Accordion accordion = new Accordion(titled1, titled2);
        descriptionPane.setContent(accordion);
        
        return accordion;
    }
}