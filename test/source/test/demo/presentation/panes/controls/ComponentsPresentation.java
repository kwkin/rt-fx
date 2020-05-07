package test.demo.presentation.panes.controls;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.ScrollPane;
import test.demo.control.TitledContentPane;
import test.demo.controller.PaneController;
import test.demo.presentation.panes.layouts.DialogPanePresentation;

public class ComponentsPresentation extends ScrollPane implements TitledContentPane
{
    private final static String TITLE = "Components";
    private final JFXMasonryPane masonryPane = new JFXMasonryPane();

    private PaneController controller;
    
    public ComponentsPresentation(PaneController controller)
    {
        this.controller = controller;
        
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
        componentPanes.add(new LabelPanePresentation(controller));
        componentPanes.add(new IconPanePresentation(controller));
        componentPanes.add(new ButtonPanePresentation(controller));
        componentPanes.add(new ToggleButtonPanePresentation(controller));
        componentPanes.add(new CheckBoxPanePresentation(controller));
        componentPanes.add(new RadioButtonPanePresentation(controller));
        componentPanes.add(new SliderPanePresentation(controller));
        componentPanes.add(new TableViewPresentation(controller));
        componentPanes.add(new TextFieldPanePresentation(controller));
        componentPanes.add(new ComboBoxPanePresentation(controller));
        componentPanes.add(new TextAreaPanePresentation(controller));
        componentPanes.add(new SpinnerPanePresentation(controller));
        componentPanes.add(new ColorPickerPanePresentation(controller));
        componentPanes.add(new ProgressPanePresentation(controller));
        componentPanes.add(new TitledPanePresentation(controller));
        componentPanes.add(new TabPanePresentation(controller));
        componentPanes.add(new ListViewPanePresentation(controller));
        componentPanes.add(new ContextMenuPanePresentation(controller));
        componentPanes.add(new DialogPanePresentation(controller));
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
