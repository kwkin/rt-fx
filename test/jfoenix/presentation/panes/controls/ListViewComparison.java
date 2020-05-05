package test.jfoenix.presentation.panes.controls;

import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.ListView;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import test.demo.control.DescriptionPane;
import test.demo.control.TitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class ListViewComparison extends TitledCard
{
    private static final String TITLE = "List View";
    private ObservableList<String> items = FXCollections.observableArrayList();
    
    public ListViewComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x2);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        grid.setHgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        int itemCount = 10;
        for (int index = 0; index < itemCount; ++index)
        {
            items.add(String.format("Item %d", index));
        }
        
        ColumnConstraints rightConstraint = new ColumnConstraints();
        rightConstraint.setFillWidth(true);
        rightConstraint.setPercentWidth(50);
        ColumnConstraints leftConstraint = new ColumnConstraints();
        leftConstraint.setFillWidth(true);
        leftConstraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(leftConstraint, leftConstraint);

        DescriptionPane flatDescription = new DescriptionPane();
        flatDescription.addLine("-Animation");
        flatDescription.addLine("-Padding");
        flatDescription.addLine("-Select State");
        flatDescription.addLine("-Scroll Bar");
        Label fxLabel = new Label("JavaFX List View", LabelStyle.LARGE);
        fxLabel.setAlignment(Pos.CENTER);
        fxLabel.setMaxWidth(Double.MAX_VALUE);
        Label rtLabel = new Label("RT-FX List View", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix List View", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        
        int row = 0;
        javafx.scene.control.ListView<String> fxComponent = new javafx.scene.control.ListView<String>(items);
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxLabel,             0, row++, 2, 1);
        grid.add(fxComponent,         0, row++, 2, 1);
        grid.add(rtLabel,             0, row  , 1, 1);
        grid.add(jfxLabel,            1, row++, 1, 1);
        grid.add(createRtListView(),  0, row  , 1, 1);
        grid.add(createJfxListView(), 1, row++, 1, 1);
        grid.add(flatDescription,     0, row++, 2, 1);
        
        setContent(grid);
    }
    
    public Node createRtListView()
    {
        ListView<String> rtListView = new ListView<String>(items);
        return rtListView;
    }
    
    public Node createJfxListView()
    {
        JFXListView<String> jfxListView = new JFXListView<String>();
        jfxListView.setItems(items);
        return jfxListView;
    }
}
