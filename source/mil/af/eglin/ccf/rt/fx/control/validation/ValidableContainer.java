package mil.af.eglin.ccf.rt.fx.control.validation;

import javafx.geometry.Pos;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;

public class ValidableContainer<T> extends StackPane
{
    private ValidableControl<T> control;
    private Text errorText;
    
    public ValidableContainer(ValidableControl<T> control)
    {
        this.control = control;
        getStyleClass().add("error-container");
        
        createErrorText();
    }
    
    public void createErrorText()
    {
        if (this.errorText != null)
        {
            return;
        }
        this.errorText = new Text();
        this.errorText.setManaged(true);
        this.errorText.getStyleClass().add("error-text");
        this.errorText.textProperty().bind(this.control.errorMessageProperty());
        StackPane.setAlignment(this.errorText, Pos.CENTER_LEFT);
        getChildren().add(this.errorText);
    }
}
