package test.single.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.TitledCardSize;
import test.single.controls.DescriptionPane;
import test.single.controls.TitledCard;

public class BasicListViewPresentation extends TitledCard
{
    private static final String TITLE = "List View";
    
    public BasicListViewPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox stackPane = new VBox();
        
        stackPane.getChildren().add(createListView());
        
        setContent(stackPane);
    }
    
    private Node createListView()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("List boxes can contain several items. These items can include text, images, or really anything.");

        VBox stackPane = new VBox();
        stackPane.setSpacing(25);
        
        ObservableList<String> items = FXCollections.observableArrayList();
        int itemCount = 10;
        items.add("This is a ridiculously long list view entry used in order to show the horizontal scroll bar.");
        for (int index = 0; index < itemCount; ++index)
        {
            items.add(String.format("Item %d", index));
        }
        ListView<String> listView = new ListView<String>(items);
        stackPane.getChildren().add(listView);

        descriptionPane.setContent(stackPane);
        
        return descriptionPane;
    }
}