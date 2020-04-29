package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.TabPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class TabPanePresentation extends TitledCard
{
    private static final String TITLE = "Tabs";
    
    public TabPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createTabPane());
        
        setContent(stackPane);
    }
    
    private Node createTabPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("A tab pane must define tab items. Each tab item can contain content.");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        Tab tab1 = new Tab("Tab 1");
        Button button1 = new Button("Button 1");
        tab1.setContent(button1);
        
        Tab tab2 = new Tab("Tab 2");
        Button tabButton2 = new Button("Tab Button 2");
        tab2.setContent(tabButton2);
        
        Tab disabled = new Tab("Disabled");
        Button disabledButton = new Button("Button 1");
        disabled.setDisable(true);
        tab1.setContent(disabledButton);
        
        tabPane.getTabs().addAll(tab1, tab2, disabled);
        
        descriptionPane.setContent(tabPane);
        
        return descriptionPane;
    }
}