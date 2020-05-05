package test.demo.control;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Text;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.TextFlow;

public class DescriptionPane extends GridPane
{
    private Text text;
    private Node content;
    
    public DescriptionPane()
    {
        this("");
        initialize();
    }
    
    public DescriptionPane(String description)
    {
        this.text = new Text(description);
        TextFlow textFlow = new TextFlow(text);
        add(textFlow, 0, 0);
        initialize();
    }
    
    public String getDescription()
    {
        return this.text.getText();
    }
    
    public void setDescription(String description)
    {
        this.text.setText(description);
    }
    
    public void addLine(String line)
    {
        String currentText = this.text.getText();
        currentText += "\n" + line;
        this.text.setText(currentText);
    }
    
    public void setContent(Node content)
    {
        this.content = content;
        add(this.content, 0, 1);
    }
    
    public Node getContent()
    {
        return this.content;
    }
    
    private void initialize()
    {
        setPadding(new Insets(0));
        setHgap(0);
        setVgap(0);
    }
}
