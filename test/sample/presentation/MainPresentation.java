package test.sample.presentation;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToolbar;
import com.sun.javafx.css.StyleManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.style.Theme;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;
import test.jfeonix.presentation.panes.controls.CheckBoxComparison;
import test.jfeonix.presentation.panes.controls.ComboBoxComparison;
import test.jfeonix.presentation.panes.controls.ListViewComparison;
import test.jfeonix.presentation.panes.controls.PickerComparison;
import test.jfeonix.presentation.panes.controls.ProgressBarComparison;
import test.jfeonix.presentation.panes.controls.RadioButtonComparison;
import test.jfeonix.presentation.panes.controls.SliderComparison;
import test.jfeonix.presentation.panes.controls.TableComparison;
import test.jfeonix.presentation.panes.controls.TextFieldComparison;
import test.jfeonix.presentation.panes.controls.ToggleSwitchComparison;
import test.jfeonix.presentation.panes.controls.ButtonComparison;
import test.sample.control.TitledContentPane;
import test.sample.controller.PaneController;
import test.sample.presentation.panes.controls.ComponentsPresentation;
import test.sample.presentation.panes.layouts.LayoutsPresentation;
import test.sample.presentation.sidebar.SideBarPresentation;

public class MainPresentation extends BorderPane
{
    private SideBarPresentation sidebar;
    private StackPane centerPane;
    private JFXDrawer drawer;
    private IconToggleButton toggleButton;
    private IconToggleButton themeToggle;;
    private Label titled;

    private ObservableList<TitledContentPane> panes = FXCollections.observableArrayList();

    /**
     * @param paneController
     */
    public MainPresentation(PaneController paneController)
    {
        JFXToolbar toolBar = new JFXToolbar();

        SvgIcon arrowRight = new SvgIcon(SvgIcons.ARROW_RIGHT, Color.WHITE, IconSizes.SIZE_16);
        SvgIcon arrowLeft = new SvgIcon(SvgIcons.ARROW_LEFT, Color.WHITE, IconSizes.SIZE_16);
        this.toggleButton = new IconToggleButton(arrowLeft, arrowRight);
        SvgIcon bright7 = new SvgIcon(SvgIcons.BRIGHTNESS_7, Color.WHITE, IconSizes.SIZE_16);
        SvgIcon bright5 = new SvgIcon(SvgIcons.BRIGHTNESS_5, Color.WHITE, IconSizes.SIZE_16);
        this.themeToggle = new IconToggleButton(bright7, bright5);
        this.titled = new Label("Components");
        toolBar.setLeftItems(this.toggleButton, this.titled);
        toolBar.setRightItems(this.themeToggle);
        this.themeToggle.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (newVal)
            {
                ThemeManager.load(Theme.LIGHT);
            }
            else
            {
                ThemeManager.load(Theme.LIGHT);
            }
        });
        setTop(toolBar);

        this.panes.add(new ComponentsPresentation(paneController));
        this.panes.add(new TableComparison(paneController));
        this.panes.add(new ListViewComparison(paneController));
        this.panes.add(new LayoutsPresentation());
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
            } else
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
