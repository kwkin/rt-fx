package test.sample.presentation.panes.layouts;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;

public class FlowPanePresentation extends TitledCard
{
    private static final String TITLE = "Flow Pane";
    
    public FlowPanePresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);
        
        setContent(createGridPane());
    }
    
    private Node createGridPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("");

        FlowPane iconButtonPane = new FlowPane();

        int rows = 5;
        int columns = 5;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++)
            {
                SvgIcon template = new SvgIcon(SvgIcons.SQUARE, IconSizes.SIZE_32);
                iconButtonPane.getChildren().add(template);
            }
        }
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }
}
