package test.sample.presentation.panes.layouts;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.TilePane;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;

public class TilePanePresentation extends TitledCard
{
    private static final String TITLE = "Tile Pane";
    
    public TilePanePresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);
        
        setContent(createGridPane());
    }
    
    private Node createGridPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("");

        TilePane pane = new TilePane();

        int rows = 5;
        int columns = 5;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++)
            {
                SvgIcon template = new SvgIcon(SvgIcons.SQUARE, IconSizes.SIZE_32);
                pane.getChildren().add(template);
            }
        }
        descriptionPane.setContent(pane);
        return descriptionPane;
    }
}
