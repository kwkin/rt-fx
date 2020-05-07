package test.single.presentation;

import javafx.scene.control.ScrollPane;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import test.demo.controller.PaneController;

public class MainPresentation extends BorderPane
{
    public MainPresentation(PaneController controller)
    {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(new BasicButtonPresentation());
        flowPane.getChildren().add(new BasicToggleButtonPresentation());
        flowPane.getChildren().add(new BasicCheckBoxPresentation());
        flowPane.getChildren().add(new BasicRadioButtonPresentation());
        flowPane.getChildren().add(new BasicSlidersPresentation());
        flowPane.getChildren().add(new BasicTextFieldsPresentation());
        flowPane.getChildren().add(new BasicComboBoxPresentation());
        flowPane.getChildren().add(new BasicTextAreaPresentation());
        flowPane.getChildren().add(new BasicSpinnerPresentation());
        flowPane.getChildren().add(new BasicTableViewPresentation(controller));
        flowPane.getChildren().add(new BasicColorPickerPresentation());
        flowPane.getChildren().add(new BasicProgressBarPresentation());
        flowPane.getChildren().add(new BasicTitledPanePresentation());
        flowPane.getChildren().add(new BasicTabPresentation());
        flowPane.getChildren().add(new BasicListViewPresentation());
        flowPane.getChildren().add(new BasicContextMenuPresentation());
        scrollPane.setContent(flowPane);
        setCenter(scrollPane);
    }
}
