package test.sample.control;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.CardPane;

public class TitledCard extends CardPane implements TitledContentPane
{
    private Label titleLabel;
    private Node content;
    
    private TitledCardSize cardSize = TitledCardSize.SIZE_1x2;
    
    public TitledCard(String title)
    {
        super();
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        initialize();
    }
    
    public TitledCard(String title, Accent accent)
    {
        super(accent);
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        initialize();
    }

    public TitledCard(String title, TitledCardSize size)
    {
        super();
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        this.cardSize = size;
        initialize();
    }
    
    public TitledCard(String title, Accent accent, TitledCardSize size)
    {
        super(accent);
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        this.cardSize = size;
        initialize();
    }
    
    public Label getTitleLabel()
    {
        return this.titleLabel;
    }
    
    public void setTitleText(String text)
    {
        this.titleLabel.setText(text);
    }
    
    public void setContent(Node content)
    {
        this.content = content;
        VBox.setVgrow(content, Priority.ALWAYS);
        getChildren().add(this.content);
    }
    
    public Node getContent()
    {
        return this.content;
    }

    @Override
    public String getTitle()
    {
        return this.titleLabel.getText();
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
        getChildren().add(this.titleLabel);
    }
}
