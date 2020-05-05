package test.sample.presentation.panes.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ListView;
import mil.af.eglin.ccf.rt.fx.control.style.ListViewStyle;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class ListViewPanePresentation extends TitledCard
{
    private static final String TITLE = "List View";
    
    public ListViewPanePresentation(PaneController controller)
    {
        super(TITLE);

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
        ListView<String> listViewZebra = new ListView<String>(items);
        ListView<String> listView = new ListView<String>(items, ListViewStyle.PLAIN);
        stackPane.getChildren().add(listViewZebra);
        stackPane.getChildren().add(listView);

        descriptionPane.setContent(stackPane);
        
        return descriptionPane;
    }
}