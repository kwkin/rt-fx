package test.sample.presentation.panes.layouts;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ScrollPane;
import test.sample.control.TitledContentPane;

public class LayoutsPresentation extends ScrollPane implements TitledContentPane
{
    private final static String TITLE = "Layouts";
    private final JFXMasonryPane masonryPane = new JFXMasonryPane();

    public LayoutsPresentation()
    {
        setFitToWidth(true);
        this.masonryPane.setHSpacing(20);
        this.masonryPane.setVSpacing(20);
        this.masonryPane.setPadding(new Insets(16));
        this.masonryPane.getChildren().setAll(createComponentPanes());
        setContent(this.masonryPane);
    }
    
    private ObservableList<Node> createComponentPanes()
    {
        ObservableList<Node> componentPanes = FXCollections.observableArrayList();
        componentPanes.add(new GridPanePresentation());
        componentPanes.add(new FlowPanePresentation());
        componentPanes.add(new TilePanePresentation());
        componentPanes.add(new HBoxPresentation());
        componentPanes.add(new VBoxPresentation());
        return componentPanes;
    }

    @Override
    public String getTitle()
    {
        return TITLE;
    }

    @Override
    public Node getNodeContent()
    {
        return this;
    }
}
