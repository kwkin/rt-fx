package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicTitledPanePresentation extends TitledCard
{
    private static final String TITLE = "Titled Pane";
    
    public BasicTitledPanePresentation()
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
        Button primaryLightRaisedButton = new Button("BUTTON 1");
        Button primaryMidRaisedButton = new Button("BUTTON 2");
        Button primaryDarkRaisedButton = new Button("BUTTON 3");

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

        Accordion accordion = new Accordion(titled1, titled2, titled3);
        descriptionPane.setContent(accordion);
        
        return accordion;
    }
}