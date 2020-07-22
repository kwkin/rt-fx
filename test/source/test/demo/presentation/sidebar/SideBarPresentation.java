package test.demo.presentation.sidebar;

import java.util.HashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.ScrollPane;
import mil.af.eglin.ccf.rt.fx.control.TreeView;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import test.demo.control.TitledContentPane;

// TODO add sidebar as a control
public class SideBarPresentation extends StackPane
{
    private HashMap<TreeItem<String>, TitledContentPane> itemMap = new HashMap<TreeItem<String>, TitledContentPane>();
    private IconToggleButton iconToggleButton1;
    private IconToggleButton iconToggleButton2;
    private ObjectProperty<TitledContentPane> selectedCard = new SimpleObjectProperty<>();
    
    public SideBarPresentation(ObservableList<TitledContentPane> panes)
    {
        ScrollPane scroll = new ScrollPane();
        BorderPane pane = new BorderPane();
        
        TreeItem<String> root = new TreeItem<String> ();
        for (TitledContentPane titledPane : panes)
        {
            TreeItem<String> item = new TreeItem<String> (titledPane.getTitle());   
            itemMap.put(item, titledPane);        
            root.getChildren().add(item);
        }
        panes.addListener(new ListChangeListener<TitledContentPane>()
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends TitledContentPane> c)
            {
                if (c.next() && c.wasAdded())
                {
                    for (TitledContentPane pane : c.getAddedSubList())
                    {
                        TreeItem<String> item = new TreeItem<String>(pane.getTitle());   
                        itemMap.put(item, pane);
                        root.getChildren().add(item);
                    }
                }
                else if (c.wasRemoved())
                {
                    for (TitledContentPane pane : c.getRemoved())
                    {
                        root.getChildren().remove(itemMap.remove(pane));
                    }
                }
            }
        });
        TreeView<String> tree = new TreeView<String>(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> 
        {
            TitledContentPane titledPane = this.itemMap.get(newVal);
            this.selectedCard.set(titledPane);
        });
        tree.getSelectionModel().select(root.getChildren().get(0));
        this.selectedCard.set(this.itemMap.get(tree.getSelectionModel().getSelectedItem()));
        scroll.setFitToHeight(true);
        pane.setCenter(tree);
        
        scroll.setContent(pane);
        
//        Button noPush = new Button("DO NOT PRESS");
//        noPush.setStyle("-fx-background-color:#f53a18;-fx-text-fill:WHITE");
//        StackPane.setAlignment(noPush, Pos.BOTTOM_CENTER);
        
//        getChildren().addAll(scroll, noPush);
        getChildren().addAll(scroll);
    }
    
    public ReadOnlyObjectProperty<TitledContentPane> selectedCardProperty()
    {
        return this.selectedCard;
    }
    
    public TitledContentPane getSelectedCard()
    {
        return this.selectedCard.getValue();
    }
    
    public IconToggleButton getIconToggleButton()
    {
        return iconToggleButton1;
    }
    
    public IconToggleButton getIconToggleButton2()
    {
        return iconToggleButton2;
    }
}
