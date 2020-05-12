package test.demo.presentation.panes.controls;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class SeparatorPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Titled Separators";
    
    public SeparatorPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        VBox vBox = new VBox();
        vBox.getChildren().add(createHorizontalSeparators());
        vBox.getChildren().add(createVerticalSeparators());
        
        setContent(vBox);
    }
    
    private Node createHorizontalSeparators()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Titled separators can be labled with both text and an icon.");

        VBox buttonVBox = new VBox();
        buttonVBox.setSpacing(16);
        
        TitledSeparator primaryDarkTitledSeparator = new TitledSeparator("TITLED", Accent.PRIMARY_DARK);
        TitledSeparator secondaryDarkTitledSeparator = new TitledSeparator("", Accent.SECONDARY_DARK);
        SvgGlyph iconNoTitle = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_16);
        secondaryDarkTitledSeparator.setGraphic(iconNoTitle);
        secondaryDarkTitledSeparator.setContentDisplay(ContentDisplay.LEFT);
        TitledSeparator baseDarkTitledSeparator = new TitledSeparator("WITH ICON", Accent.BASE_DARK);
        SvgGlyph iconTitle = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_16);
        baseDarkTitledSeparator.setGraphic(iconTitle);
        baseDarkTitledSeparator.setContentDisplay(ContentDisplay.LEFT);
        
        buttonVBox.getChildren().add(primaryDarkTitledSeparator);
        buttonVBox.getChildren().add(secondaryDarkTitledSeparator);
        buttonVBox.getChildren().add(baseDarkTitledSeparator);
        
        descriptionPane.setContent(buttonVBox);
        
        return descriptionPane;
    }
    
    private Node createVerticalSeparators()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Titled separators may also be oriented vertically.");

        HBox buttonHBox = new HBox();
        buttonHBox.setMinHeight(80);
        buttonHBox.setSpacing(16);
       

        TitledSeparator primaryDarkTitledSeparator = new TitledSeparator("TITLED", Orientation.VERTICAL, Accent.PRIMARY_DARK);
        primaryDarkTitledSeparator.setPrefWidth(100);
        TitledSeparator secondaryDarkTitledSeparator = new TitledSeparator("", Orientation.VERTICAL, Accent.SECONDARY_DARK);
        secondaryDarkTitledSeparator.setPrefWidth(50);
        SvgGlyph iconNoTitle = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_16);
        secondaryDarkTitledSeparator.setGraphic(iconNoTitle);
        secondaryDarkTitledSeparator.setContentDisplay(ContentDisplay.LEFT);
        TitledSeparator baseDarkTitledSeparator = new TitledSeparator("WITH ICON", Orientation.VERTICAL, Accent.BASE_DARK);
        baseDarkTitledSeparator.setPrefWidth(150);
        SvgGlyph iconTitle = new SvgGlyph(SvgFile.BELL, IconSize.SIZE_16);
        baseDarkTitledSeparator.setGraphic(iconTitle);
        baseDarkTitledSeparator.setContentDisplay(ContentDisplay.LEFT);
        
        buttonHBox.getChildren().add(primaryDarkTitledSeparator);
        buttonHBox.getChildren().add(secondaryDarkTitledSeparator);
        buttonHBox.getChildren().add(baseDarkTitledSeparator);
        
        descriptionPane.setContent(buttonHBox);
        
        return descriptionPane;
    }
}
