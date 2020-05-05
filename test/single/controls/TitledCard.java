package test.single.controls;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import mil.af.eglin.ccf.rt.fx.layout.CardPane;
import test.demo.control.TitledCardSize;

public class TitledCard extends CardPane
{
    private Label titleLabel;
    private Node content;

    private TitledCardSize cardSize = TitledCardSize.SIZE_1x2;
    
    public TitledCard(String title)
    {
        super();
        this.titleLabel = new Label(title);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        initialize();
    }

    public TitledCard(String title, TitledCardSize size)
    {
        super();
        this.titleLabel = new Label(title);
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
    
    public void initialize()
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