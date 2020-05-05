package test.demo.presentation.panes.layouts;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import test.demo.control.DescriptionPane;
import test.demo.control.TitledCard;
import test.demo.control.TitledCardSize;

public class HBoxPresentation extends TitledCard
{
    private static final String TITLE = "H Box";
    
    public HBoxPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);
        
        setContent(createGridPane());
    }
    
    private Node createGridPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("");

        HBox pane = new HBox();

        int columns = 5;
        for (int columnIndex = 0; columnIndex < columns; columnIndex++)
        {
            SvgIcon template = new SvgIcon(SvgIcons.SQUARE, IconSizes.SIZE_32);
            pane.getChildren().add(template);
        }
        descriptionPane.setContent(pane);
        return descriptionPane;
    }
}
