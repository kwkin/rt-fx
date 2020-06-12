package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.TilePane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class IconPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Icons";
    
    public IconPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);

        VBox vBox = new VBox();
        vBox.getChildren().add(createIcons());
        vBox.getChildren().add(new TitledSeparator("Icon Features"));
        vBox.getChildren().add(createIconSizesButtons());
        
        setContent(vBox);
    }
    
    private Node createIcons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("SVG (.svg) files can be loaded as icons using RT-FX. A subset of Google's Material Design icons are readily accessible using RT-FX.");

        TilePane iconButtonPane = new TilePane();
        iconButtonPane.setHgap(0);
        iconButtonPane.setVgap(0);
        GridPane.setHgrow(iconButtonPane, Priority.ALWAYS);
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.MAGNIFY_MINUS, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.MAGNIFY, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.MAGNIFY_PLUS, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.ARROW_DOWN_DROP_CIRCLE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.UNDO, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.REDO, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHART_LINE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHART_BELL_CURVE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CLOUD_UPLOAD, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CLOUD_DOWNLOAD, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CLOUD_ALERT, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CLOUD_QUESTION, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.PENCIL, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.EYEDROPPER_VARIANT, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.PALETTE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.FILE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.FOLDER, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.FOLDER_PLUS, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.FOLDER_DOWNLOAD, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.FOLDER_UPLOAD, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHECKBOX_MARKED, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHECKBOX_MARKED_CIRCLE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHECKBOX_BLANK_OUTLINE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CHECKBOX_BLANK_CIRCLE_OUTLINE, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.CROSSHAIRS_GPS, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.MAP, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.MAP_MARKER, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.LIGHTBULB, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.LOCK, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.LOCK_OPEN, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.BELL, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.BELL_OFF, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.COG, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.DOTS_HORIZONTAL, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.DOTS_VERTICAL, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.ADD_ANSWER_CARD, IconSize.SIZE_24));
        iconButtonPane.getChildren().add(new SvgIcon(SvgFile.ADD_ANSWER_PLOT, IconSize.SIZE_24));
        
        descriptionPane.setContent(iconButtonPane);
        
        return descriptionPane;
    }

    private Node createIconSizesButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icons are available in several standard sizes, and each individual icon can be colored.");

        FlowPane iconButtonPane = new FlowPane();
        HBox size16 = new HBox();
        size16.getChildren().add(new SvgIcon(SvgFile.COG, Paint.valueOf("#e53935"), IconSize.SIZE_16));
        size16.getChildren().add(new Label("16x16"));

        HBox size24 = new HBox();
        size24.getChildren().add(new SvgIcon(SvgFile.COG, Paint.valueOf("#ec407a"), IconSize.SIZE_24));
        size24.getChildren().add(new Label("24x24"));

        HBox size32 = new HBox();
        size32.getChildren().add(new SvgIcon(SvgFile.COG, Paint.valueOf("#7986cb"), IconSize.SIZE_32));
        size32.getChildren().add(new Label("32x32"));

        HBox size48 = new HBox();
        size48.getChildren().add(new SvgIcon(SvgFile.COG, Paint.valueOf("#2196f3"), IconSize.SIZE_48));
        size48.getChildren().add(new Label("48x48"));

        HBox size64 = new HBox();
        size64.getChildren().add(new SvgIcon(SvgFile.TACO, Paint.valueOf("#009688"), IconSize.SIZE_64));
        size64.getChildren().add(new Label("64x64"));

        HBox size96 = new HBox();
        size96.getChildren().add(new SvgIcon(SvgFile.BELL, Paint.valueOf("#4caf50"), IconSize.SIZE_96));
        size96.getChildren().add(new Label("96x96"));

        iconButtonPane.getChildren().add(size16);
        iconButtonPane.getChildren().add(size24);
        iconButtonPane.getChildren().add(size32);
        iconButtonPane.getChildren().add(size48);
        iconButtonPane.getChildren().add(size64);
        iconButtonPane.getChildren().add(size96);
        
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }
}
