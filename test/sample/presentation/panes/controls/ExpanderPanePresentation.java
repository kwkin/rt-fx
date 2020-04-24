package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class ExpanderPanePresentation extends TitledCard
{
    private static final String TITLE = "Titled Pane";
    
    public ExpanderPanePresentation(PaneController controller)
    {
        super(TITLE);

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
        Button primaryLightRaisedButton = new Button("Button 1", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        Button primaryMidRaisedButton = new Button("Button 2", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        Button primaryDarkRaisedButton = new Button("Button 3", ButtonStyle.RAISED, Accent.PRIMARY_DARK);

        TitledPane titledPane = new TitledPane("Titled Pane", vBox);
        vBox.getChildren().add(primaryLightRaisedButton);
        vBox.getChildren().add(primaryMidRaisedButton);
        vBox.getChildren().add(primaryDarkRaisedButton);
        
        titledPane.setContent(vBox);
        descriptionPane.setContent(titledPane);
        
        return descriptionPane;
    }
    
    private Node createAccordion()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("An accordion is composed of several titled panes.");
        
        Accordion accordion = new Accordion();
        
        CheckBox secondaryLightCheckBox = new CheckBox();
        secondaryLightCheckBox.setSelected(true);
        TitledPane titled1 = new TitledPane("Titled 1", secondaryLightCheckBox);
        
        CheckBox secondaryMidCheckBox = new CheckBox("Two State");
        secondaryMidCheckBox.setSelected(false);
        TitledPane titled2 = new TitledPane("Titled 2", secondaryMidCheckBox);
        
        CheckBox secondaryDarkCheckBox = new CheckBox("Three State");
        secondaryDarkCheckBox.setAllowIndeterminate(true);
        secondaryDarkCheckBox.setIndeterminate(true);
        TitledPane titled3 = new TitledPane("Titled 3", secondaryDarkCheckBox);

        accordion.getPanes().add(titled1);
        accordion.getPanes().add(titled2);
        accordion.getPanes().add(titled3);

        descriptionPane.setContent(accordion);
        
        return accordion;
    }
}