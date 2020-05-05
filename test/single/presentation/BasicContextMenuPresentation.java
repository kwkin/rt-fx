package test.single.presentation;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicContextMenuPresentation extends TitledCard
{
    private static final String TITLE = "Context Menu";
    
    public BasicContextMenuPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        
        vBox.getChildren().add(createContextMenu());
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createTooltip());
        
        setContent(vBox);
    }
    
    private Node createContextMenu()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Context menus will be displayed when right clicking the component. The context menu can have multiple items, such as text, shortcut combinations, and images.");

        Button contextMenuButton = new Button("RIGHT CLICK");
        ContextMenu contextMenu = new ContextMenu();
        MenuItem contextMenuItem1 = new MenuItem("Item 1");
        Menu contextMenuItem2 = new Menu("Parent");
        MenuItem subContextMenuItem1 = new MenuItem("Sub Item 1");
        MenuItem subContextMenuItem2 = new MenuItem("Sub Item 1");
        contextMenuItem2.getItems().addAll(subContextMenuItem1, subContextMenuItem2);
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        CheckMenuItem checkMenuItem1 = new CheckMenuItem("Check Item 1");
        CheckMenuItem checkMenuItem2 = new CheckMenuItem("Check Item 2");
        checkMenuItem2.setSelected(true);
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        ToggleGroup primaryButtonGroup = new ToggleGroup();
        RadioMenuItem radioMenuItem1 = new RadioMenuItem("Radio Item 1");
        radioMenuItem1.setToggleGroup(primaryButtonGroup);
        RadioMenuItem radioMenuItem2 = new RadioMenuItem("Radio Item 1");
        radioMenuItem2.setToggleGroup(primaryButtonGroup);
        radioMenuItem2.setSelected(true);
        contextMenu.getItems().addAll(contextMenuItem1, contextMenuItem2, separator1, checkMenuItem1, checkMenuItem2, separator2, radioMenuItem1, radioMenuItem2);
        
        contextMenuButton.setContextMenu(contextMenu);
        
        descriptionPane.setContent(contextMenuButton);
        
        return descriptionPane;
    }
    
    private Node createTooltip()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Tooltips are displayed after hovering over the component for a short amount of time.");

        Button toolTipButton = new Button("HOVER");
        Tooltip toolTip = new Tooltip("This is a tooltip");
        toolTipButton.setTooltip(toolTip);
        
        descriptionPane.setContent(toolTipButton);
        
        return descriptionPane;
    }
}