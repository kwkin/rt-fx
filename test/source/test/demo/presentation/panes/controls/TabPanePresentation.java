package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.TabPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class TabPanePresentation extends SizedTitledCard
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
        Button button1 = new Button("TAB 1 BUTTON");
        tab1.setContent(button1);
        
        Tab tab2 = new Tab("Tab 2");
        Button tabButton2 = new Button("TAB 2 BUTTON");
        tab2.setContent(tabButton2);
        
        Tab disabled = new Tab("Disabled");
        Button disabledButton = new Button("DISABLED TAB BUTTON");
        disabled.setDisable(true);
        disabled.setContent(disabledButton);
        
        tabPane.getTabs().addAll(tab1, tab2, disabled);
        
        descriptionPane.setContent(tabPane);
        
        return descriptionPane;
    }
}