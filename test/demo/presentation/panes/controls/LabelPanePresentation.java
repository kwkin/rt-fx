package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class LabelPanePresentation extends TitledCard
{
    private static final String TITLE = "Labels";
    
    public LabelPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);
        
        VBox vBox = new VBox();
        vBox.getChildren().add(createNormalLabels());
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createTitles());
        
        setContent(vBox);
    }
    
    private Node createNormalLabels()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("All RT-FX text components default to Google's Roboto font. Additionally, many of the components can be accented using primary and secondary colors define in a CSS file.");
        
        GridPane normalLabelsGridPane = new GridPane();
        Label mainSmallLabel = new Label("Main Small", LabelStyle.SMALL, Accent.BASE_MID);
        Label mainMidLabel = new Label("Main Mid", LabelStyle.NORMAL, Accent.BASE_MID);
        Label mainLargeLabel = new Label("Main Large", LabelStyle.LARGE, Accent.BASE_MID);
        Label mainTitleLabel = new Label("Main Title", LabelStyle.TITLE, Accent.BASE_MID);

        Label primarySmallLabel = new Label("Prim. Small", LabelStyle.SMALL, Accent.PRIMARY_MID);
        Label primaryMidLabel = new Label("Prim. Mid", LabelStyle.NORMAL, Accent.PRIMARY_MID);
        Label primaryLargeLabel = new Label("Prim. Large", LabelStyle.LARGE, Accent.PRIMARY_MID);
        Label primaryTitleLabel = new Label("Prim. Title", LabelStyle.TITLE, Accent.PRIMARY_MID);

        Label secondarySmallLabel = new Label("Sec. Small", LabelStyle.SMALL, Accent.SECONDARY_MID);
        Label secondaryMidLabel = new Label("Sec. Mid", LabelStyle.NORMAL, Accent.SECONDARY_MID);
        Label secondaryLargeLabel = new Label("Sec. Large", LabelStyle.LARGE, Accent.SECONDARY_MID);
        Label secondaryTitleLabel = new Label("Sec. Title", LabelStyle.TITLE, Accent.SECONDARY_MID);
            
        normalLabelsGridPane.add(mainSmallLabel, 0, 0);
        normalLabelsGridPane.add(mainMidLabel,   0, 1);
        normalLabelsGridPane.add(mainLargeLabel, 0, 2);
        normalLabelsGridPane.add(mainTitleLabel, 0, 3);
        
        normalLabelsGridPane.add(primarySmallLabel, 1, 0);
        normalLabelsGridPane.add(primaryMidLabel,   1, 1);
        normalLabelsGridPane.add(primaryLargeLabel, 1, 2);
        normalLabelsGridPane.add(primaryTitleLabel, 1, 3);
        
        normalLabelsGridPane.add(secondarySmallLabel, 2, 0);
        normalLabelsGridPane.add(secondaryMidLabel,   2, 1);
        normalLabelsGridPane.add(secondaryLargeLabel, 2, 2);
        normalLabelsGridPane.add(secondaryTitleLabel, 2, 3);
        
        descriptionPane.setContent(normalLabelsGridPane);
        
        return descriptionPane;
    }
    
    private Node createTitles()
    {
        VBox titlesGridPane = new VBox();
        Label primaryLightAccentTitle = new Label("Primary Light Title", LabelStyle.BLOCK_TITLE, Accent.PRIMARY_LIGHT);
        Label primaryMidAccentTitle = new Label("Primary Mid Title", LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        Label primaryDarkAccentTitle = new Label("Primary Dark Title", LabelStyle.BLOCK_TITLE, Accent.PRIMARY_DARK);
        Label secondaryLightAccentTitle = new Label("Secondary Light Title", LabelStyle.BLOCK_TITLE, Accent.SECONDARY_LIGHT);
        Label secondaryMidAccentTitle = new Label("Secondary Mid Title", LabelStyle.BLOCK_TITLE, Accent.SECONDARY_MID);
        Label secondaryDarkAccentTitle = new Label("Secondary Dark Title", LabelStyle.BLOCK_TITLE, Accent.SECONDARY_DARK);

        titlesGridPane.getChildren().add(primaryLightAccentTitle);
        titlesGridPane.getChildren().add(primaryMidAccentTitle);
        titlesGridPane.getChildren().add(primaryDarkAccentTitle);
        titlesGridPane.getChildren().add(secondaryLightAccentTitle);
        titlesGridPane.getChildren().add(secondaryMidAccentTitle);
        titlesGridPane.getChildren().add(secondaryDarkAccentTitle);
        
        return titlesGridPane;
    }
}
