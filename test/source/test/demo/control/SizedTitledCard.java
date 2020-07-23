package test.demo.control;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.layout.TitledCardPane;

public class SizedTitledCard extends TitledCardPane implements TitledContentPane
{
    private TitledCardSize cardSize = TitledCardSize.SIZE_1x2;
    
    public SizedTitledCard(String title)
    {
        super(title);
        initialize();
    }
    
    public SizedTitledCard(String title, Accent accent)
    {
        super(title, accent);
        initialize();
    }

    public SizedTitledCard(String title, TitledCardSize size)
    {
        super(title);
        this.cardSize = size;
        initialize();
    }
    
    public SizedTitledCard(String title, Accent accent, TitledCardSize size)
    {
        super(title, accent);
        this.cardSize = size;
        initialize();
    }

    @Override
    public Node getNodeContent()
    {
        return this;
    }
    
    private void initialize()
    {
        setPrefWidth(this.cardSize.getWidth());
        setPrefHeight(this.cardSize.getHeight());
        setMinWidth(this.cardSize.getWidth());
        setMinHeight(this.cardSize.getHeight());
        setMaxWidth(this.cardSize.getWidth());
        setMaxHeight(this.cardSize.getHeight());
    }
}
