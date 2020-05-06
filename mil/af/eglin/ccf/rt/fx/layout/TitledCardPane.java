package mil.af.eglin.ccf.rt.fx.layout;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;

public class TitledCardPane extends CardPane
{
    private Label titleLabel;
    private Node content;
    
    public TitledCardPane(String title)
    {
        super();
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, Accent.PRIMARY_MID);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
        initialize();
    }
    
    public TitledCardPane(String title, Accent accent)
    {
        super(accent);
        this.titleLabel = new Label(title, LabelStyle.BLOCK_TITLE, accent);
        this.titleLabel.setTextAlignment(TextAlignment.CENTER);
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

    public String getTitle()
    {
        return this.titleLabel.getText();
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
    
    private void initialize()
    {
        getChildren().add(this.titleLabel);
    }
}
