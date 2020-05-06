package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
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
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createIconSizesButtons());
        
        setContent(vBox);
    }
    
    private Node createIcons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("SVG (.svg) files can be loaded as icons using RT-FX. A subset of Google's Material Design icons are readily accessible using RT-FX.");

        TilePane iconButtonPane = new TilePane();
        GridPane.setHgrow(iconButtonPane, Priority.ALWAYS);
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.MAGNIFY_MINUS, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.MAGNIFY, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.MAGNIFY_PLUS, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.ARROW_DOWN_DROP_CIRCLE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.UNDO, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.REDO, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHART_LINE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHART_BELL_CURVE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CLOUD_UPLOAD, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CLOUD_DOWNLOAD, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CLOUD_ALERT, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CLOUD_QUESTION, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.PENCIL, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.EYEDROPPER_VARIANT, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.PALETTE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.FILE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.FOLDER, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.FOLDER_PLUS, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.FOLDER_DOWNLOAD, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.FOLDER_UPLOAD, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHECKBOX_MARKED, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHECKBOX_MARKED_CIRCLE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHECKBOX_BLANK_OUTLINE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CHECKBOX_BLANK_CIRCLE_OUTLINE, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.CROSSHAIRS_GPS, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.MAP, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.MAP_MARKER, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.LIGHTBULB, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.LOCK, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.LOCK_OPEN, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.BELL, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.BELL_OFF, IconSizes.SIZE_32));
        iconButtonPane.getChildren().add(new SvgIcon(SvgIcons.COG, IconSizes.SIZE_32));
        
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }

    private Node createIconSizesButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icons are available in several standard sizes, and each individual icon can be colored.");

        GridPane iconButtonPane = new GridPane();
        HBox size16 = new HBox();
        size16.getChildren().add(new SvgIcon(SvgIcons.COG, Paint.valueOf("#e53935"), IconSizes.SIZE_16));
        size16.getChildren().add(new Label("16x16"));

        HBox size24 = new HBox();
        size24.getChildren().add(new SvgIcon(SvgIcons.COG, Paint.valueOf("#ec407a"), IconSizes.SIZE_24));
        size24.getChildren().add(new Label("24x24"));

        HBox size32 = new HBox();
        size32.getChildren().add(new SvgIcon(SvgIcons.COG, Paint.valueOf("#7986cb"), IconSizes.SIZE_32));
        size32.getChildren().add(new Label("32x32"));

        HBox size48 = new HBox();
        size48.getChildren().add(new SvgIcon(SvgIcons.COG, Paint.valueOf("#2196f3"), IconSizes.SIZE_48));
        size48.getChildren().add(new Label("48x48"));

        HBox size64 = new HBox();
        size64.getChildren().add(new SvgIcon(SvgIcons.TACO, Paint.valueOf("#009688"), IconSizes.SIZE_64));
        size64.getChildren().add(new Label("64x64"));

        HBox size96 = new HBox();
        size96.getChildren().add(new SvgIcon(SvgIcons.BELL, Paint.valueOf("#4caf50"), IconSizes.SIZE_96));
        size96.getChildren().add(new Label("96x96"));

        iconButtonPane.add(size16, 0, 0, 1, 1);
        iconButtonPane.add(size24, 0, 1, 1, 1);
        iconButtonPane.add(size32, 0, 2, 1, 1);
        iconButtonPane.add(size48, 0, 3, 1, 1);
        iconButtonPane.add(size64, 1, 0, 1, 2);
        iconButtonPane.add(size96, 1, 2, 1, 3);
        
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }
}
