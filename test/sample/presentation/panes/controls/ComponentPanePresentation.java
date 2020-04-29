package test.sample.presentation.panes.controls;

import javafx.scene.control.ScrollPane;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import test.sample.controller.PaneController;

public class ComponentPanePresentation extends ScrollPane
{
    public ComponentPanePresentation(PaneController controller)
    {
        setFitToWidth(true);
        
        FlowPane flowPane = new FlowPane();
        
        LabelPanePresentation labelPane = new LabelPanePresentation(controller);
        ButtonPanePresentation normalButtonPane = new ButtonPanePresentation(controller);
        ToggleButtonPanePresentation specialButtonPane = new ToggleButtonPanePresentation(controller);
        CheckBoxPanePresentation boxesPane = new CheckBoxPanePresentation(controller);
        SliderPanePresentation sliderPane = new SliderPanePresentation(controller);
        TextFieldPanePresentation textBoxPane = new TextFieldPanePresentation(controller);
        ComboBoxPanePresentation comboBoxPane = new ComboBoxPanePresentation(controller);
        TextAreaPanePresentation textAreaPane = new TextAreaPanePresentation(controller);
        SpinnerPanePresentation stepperPane = new SpinnerPanePresentation(controller);
        ColorPickerPanePresentation colorPickerPane = new ColorPickerPanePresentation(controller);
        ProgressPanePresentation progressPane = new ProgressPanePresentation(controller);
        TitledPanePresentation expanderPane = new TitledPanePresentation(controller);
        TabPanePresentation tabPane = new TabPanePresentation(controller);
        ListViewPanePresentation listViewPane = new ListViewPanePresentation(controller);
        ContextMenuPanePresentation contextMenuPane = new ContextMenuPanePresentation(controller);
        TableViewPresentation tableViewPane = new TableViewPresentation(controller);

        flowPane.getChildren().add(labelPane);
        flowPane.getChildren().add(normalButtonPane);
        flowPane.getChildren().add(specialButtonPane);
        flowPane.getChildren().add(boxesPane);
        flowPane.getChildren().add(sliderPane);
        flowPane.getChildren().add(textBoxPane);
        flowPane.getChildren().add(comboBoxPane);
        flowPane.getChildren().add(textAreaPane);
        flowPane.getChildren().add(stepperPane);
        flowPane.getChildren().add(colorPickerPane);
        flowPane.getChildren().add(progressPane);
        flowPane.getChildren().add(expanderPane);
        flowPane.getChildren().add(tabPane);
        flowPane.getChildren().add(listViewPane);
        flowPane.getChildren().add(contextMenuPane);
        flowPane.getChildren().add(tableViewPane);
        
        setContent(flowPane);
    }
}
