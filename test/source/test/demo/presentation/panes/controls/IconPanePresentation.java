package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
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
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.MAGNIFY_MINUS, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.MAGNIFY, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.MAGNIFY_PLUS, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.ARROW_DOWN_DROP_CIRCLE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.UNDO, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.REDO, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHART_LINE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHART_BELL_CURVE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CLOUD_UPLOAD, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CLOUD_DOWNLOAD, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CLOUD_ALERT, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CLOUD_QUESTION, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.PENCIL, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.EYEDROPPER_VARIANT, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.PALETTE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.FILE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.FOLDER, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.FOLDER_PLUS, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.FOLDER_DOWNLOAD, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.FOLDER_UPLOAD, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHECKBOX_MARKED, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHECKBOX_MARKED_CIRCLE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHECKBOX_BLANK_OUTLINE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CHECKBOX_BLANK_CIRCLE_OUTLINE, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.CROSSHAIRS_GPS, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.MAP, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.MAP_MARKER, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.LIGHTBULB, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.LOCK, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.LOCK_OPEN, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.BELL, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.BELL_OFF, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.COG, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.DOTS_HORIZONTAL, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.DOTS_VERTICAL, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.ADD_ANSWER_CARD, IconSize.SIZE_32));
        iconButtonPane.getChildren().add(new SvgGlyph(SvgFile.ADD_ANSWER_PLOT, IconSize.SIZE_32));
        
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }

    private Node createIconSizesButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icons are available in several standard sizes, and each individual icon can be colored.");

        GridPane iconButtonPane = new GridPane();
        HBox size16 = new HBox();
        size16.getChildren().add(new SvgGlyph(SvgFile.COG, Paint.valueOf("#e53935"), IconSize.SIZE_16));
        size16.getChildren().add(new Label("16x16"));

        HBox size24 = new HBox();
        size24.getChildren().add(new SvgGlyph(SvgFile.COG, Paint.valueOf("#ec407a"), IconSize.SIZE_24));
        size24.getChildren().add(new Label("24x24"));

        HBox size32 = new HBox();
        size32.getChildren().add(new SvgGlyph(SvgFile.COG, Paint.valueOf("#7986cb"), IconSize.SIZE_32));
        size32.getChildren().add(new Label("32x32"));

        HBox size48 = new HBox();
        size48.getChildren().add(new SvgGlyph(SvgFile.COG, Paint.valueOf("#2196f3"), IconSize.SIZE_48));
        size48.getChildren().add(new Label("48x48"));

        HBox size64 = new HBox();
        size64.getChildren().add(new SvgGlyph(SvgFile.TACO, Paint.valueOf("#009688"), IconSize.SIZE_64));
        size64.getChildren().add(new Label("64x64"));

        HBox size96 = new HBox();
        size96.getChildren().add(new SvgGlyph(SvgFile.BELL, Paint.valueOf("#4caf50"), IconSize.SIZE_96));
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
