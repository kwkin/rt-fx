package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicTabPresentation extends TitledCard
{
    private static final String TITLE = "Tabs";
    
    public BasicTabPresentation()
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