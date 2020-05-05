package test.demo.presentation.panes.layouts;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.TitledCard;
import test.demo.control.TitledCardSize;

public class VBoxPresentation extends TitledCard
{
    private static final String TITLE = "V Box";
    
    public VBoxPresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);
        
        setContent(createGridPane());
    }
    
    private Node createGridPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("");

        VBox pane = new VBox();

        int rows = 5;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++)
        {
            SvgIcon template = new SvgIcon(SvgIcons.SQUARE, IconSizes.SIZE_32);
            pane.getChildren().add(template);
        }
        descriptionPane.setContent(pane);
        return descriptionPane;
    }
}
