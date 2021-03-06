package test.demo.presentation.panes.layouts;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;

public class GridPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Grid Pane";
    private GridPane iconButtonPane;
    
    public GridPanePresentation()
    {
        super(TITLE, TitledCardSize.SIZE_1x1);

        Node pane = createGridPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(pane);
        vBox.getChildren().add(new TitledSeparator("TITLED"));

        setContent(vBox);
    }
    
    private Node createGridPane()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("");

        iconButtonPane = new GridPane();

        int rows = 5;
        int columns = 5;
        for (int rowIndex = 0; rowIndex < rows; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++)
            {
                SvgIcon template = new SvgIcon(SvgFile.SQUARE, IconSize.SIZE_32);
                template.setPadding(new Insets(0));
                iconButtonPane.add(template, rowIndex, columnIndex);
            }
        }
        descriptionPane.setContent(iconButtonPane);
        return iconButtonPane;
    }
}
