package test.demo.presentation;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToolbar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;
import test.demo.SampleApp;
import test.demo.abstraction.immutable.DemoTheme;
import test.demo.control.TitledContentPane;
import test.demo.controller.PaneController;
import test.demo.presentation.panes.controls.ComponentsPresentation;
import test.demo.presentation.panes.controls.TextFieldPanePresentation;
import test.demo.presentation.panes.layouts.LayoutsPresentation;
import test.demo.presentation.sidebar.SideBarPresentation;
import test.jfoenix.presentation.panes.controls.ButtonComparison;
import test.jfoenix.presentation.panes.controls.CheckBoxComparison;
import test.jfoenix.presentation.panes.controls.ComboBoxComparison;
import test.jfoenix.presentation.panes.controls.ListViewComparison;
import test.jfoenix.presentation.panes.controls.PickerComparison;
import test.jfoenix.presentation.panes.controls.ProgressBarComparison;
import test.jfoenix.presentation.panes.controls.RadioButtonComparison;
import test.jfoenix.presentation.panes.controls.SliderComparison;
import test.jfoenix.presentation.panes.controls.TableComparison;
import test.jfoenix.presentation.panes.controls.TextFieldComparison;
import test.jfoenix.presentation.panes.controls.ToggleSwitchComparison;

public class MainPresentation extends BorderPane
{
    private SideBarPresentation sidebar;
    private StackPane centerPane;
    private JFXDrawer drawer;
    private IconToggleButton toggleButton;
    private ComboBox<DemoTheme> comboBoxTheme;
    private Label titled;

    private ObservableList<TitledContentPane> panes = FXCollections.observableArrayList();

    /**
     * @param paneController
     */
    public MainPresentation(PaneController paneController)
    {
        JFXToolbar toolBar = new JFXToolbar();

        SvgGlyph arrowRight = new SvgGlyph(SvgFile.ARROW_RIGHT, Color.WHITE, IconSize.SIZE_16);
        SvgGlyph arrowLeft = new SvgGlyph(SvgFile.ARROW_LEFT, Color.WHITE, IconSize.SIZE_16);
        this.toggleButton = new IconToggleButton(arrowLeft, arrowRight);
        this.titled = new Label("Components");
        Label themeLabel = new Label("Theme:");

        this.comboBoxTheme = new ComboBox<DemoTheme>(FXCollections.observableArrayList(DemoTheme.values()));
        this.comboBoxTheme.setValue(DemoTheme.LIGHT);
        toolBar.setLeftItems(this.toggleButton, this.titled);
        toolBar.setRightItems(themeLabel, this.comboBoxTheme);

        this.comboBoxTheme.valueProperty().addListener((ov, oldVal, newVal) ->
        {
            ThemeManager.getInstance().load(newVal.getTheme());
            if (!newVal.getDemoFile().equals(oldVal.getDemoFile()))
            {
                String oldDemoFile = ResourceLoader.loadDemoFile(oldVal.getDemoFile());
                String newDemoFile = ResourceLoader.loadDemoFile(newVal.getDemoFile());
                SampleApp.getStage().getScene().getStylesheets().remove(oldDemoFile);
                SampleApp.getStage().getScene().getStylesheets().add(newDemoFile);
            }
        });
        setTop(toolBar);

        this.panes.add(new TextFieldPanePresentation(paneController));
        this.panes.add(new ComponentsPresentation(paneController));
        this.panes.add(new LayoutsPresentation());
        this.panes.add(new TableComparison(paneController));
        this.panes.add(new ListViewComparison(paneController));
        this.panes.add(new ButtonComparison(paneController));
        this.panes.add(new ComboBoxComparison(paneController));
        this.panes.add(new ToggleSwitchComparison(paneController));
        this.panes.add(new CheckBoxComparison(paneController));
        this.panes.add(new SliderComparison(paneController));
        this.panes.add(new TextFieldComparison(paneController));
        this.panes.add(new RadioButtonComparison(paneController));
        this.panes.add(new PickerComparison(paneController));
        this.panes.add(new ProgressBarComparison(paneController)); 

        this.drawer = new JFXDrawer();
        this.drawer.setDefaultDrawerSize(250);
        this.sidebar = new SideBarPresentation(this.panes);
        this.centerPane = new StackPane();

        if (this.sidebar.getSelectedCard() != null && this.sidebar.getChildren().size() <= 1)
        {
            TitledContentPane content = this.sidebar.getSelectedCard();
            this.centerPane.getChildren().add(0, content.getNodeContent());
            this.titled.setText(content.getTitle());
        }
        this.sidebar.selectedCardProperty().addListener((ov, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                this.centerPane.getChildren().removeIf(e -> panes.contains(e));
                this.centerPane.getChildren().add(0, newVal.getNodeContent());
                this.titled.setText(newVal.getTitle());
            }
        });

        this.drawer.setContent(this.centerPane);
        this.drawer.setSidePane(this.sidebar);

        this.toggleButton.setOnAction((event) ->
        {
            if (this.drawer.isClosed() || this.drawer.isClosing())
            {
                this.drawer.open();
            }
            else
            {
                this.drawer.close();
            }
        });
        this.drawer.setOnDrawerClosed((event) ->
        {
            this.toggleButton.setSelected(!this.drawer.isClosed());
        });

        this.drawer.close();
        setCenter(this.drawer);
    }
}
