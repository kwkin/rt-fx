package test.demo.presentation.panes.layouts;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;

public class FlowPanePresentation extends SizedTitledCard
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
                SvgGlyph template = new SvgGlyph(SvgFile.SQUARE, IconSize.SIZE_32);
                iconButtonPane.getChildren().add(template);
            }
        }
        descriptionPane.setContent(iconButtonPane);
        return descriptionPane;
    }
}
