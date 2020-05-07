package test.demo.presentation.panes.layouts;

import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;

public class CardPanePresentation extends VBox
{
    public CardPanePresentation()
    {
        SizedTitledCard card1 = new SizedTitledCard("Titled 1", TitledCardSize.SIZE_1x1);
        SizedTitledCard card2 = new SizedTitledCard("Titled 2", TitledCardSize.SIZE_1x1);
        VBox pane = new VBox(24.0, card1, card2);
        this.getChildren().add(pane);
    }
}
