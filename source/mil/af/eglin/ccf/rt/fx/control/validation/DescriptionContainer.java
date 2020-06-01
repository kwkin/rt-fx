package mil.af.eglin.ccf.rt.fx.control.validation;

import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.RtDescriptionControl;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;

public class DescriptionContainer<T extends ValidableControl<?> & RtDescriptionControl> extends StackPane
{
    private final StackPane helperContainer = new StackPane();
    private final StackPane errorContainer = new StackPane();
    private Text helperText;
    private Text errorText;
    
    private T control;
    
    public DescriptionContainer(T control)
    {
        this.setManaged(false);
        this.control = control;
        getStyleClass().add("description-container");
        getStyleClass().add("helper-container");

        createHelperText();
        createErrorText();

        Rectangle descriptionClip = new Rectangle();
        descriptionClip.setX(0);
        descriptionClip.setY(0);
        descriptionClip.widthProperty().bind(widthProperty());
        descriptionClip.heightProperty().bind(heightProperty());
        setClip(descriptionClip);
        
        StackPane.setAlignment(this.helperContainer, Pos.CENTER_LEFT);
        StackPane.setAlignment(this.errorContainer, Pos.CENTER_LEFT);
        
        this.helperContainer.visibleProperty().bind(control.isValidProperty());
        this.errorContainer.visibleProperty().bind(control.isValidProperty().not());
        this.errorContainer.getChildren().addAll(helperContainer, errorContainer);
    }
    
    private void createHelperText()
    {
        if (this.helperText != null || !this.control.getIsShowHelperText())
        {
            return;
        }
        this.helperText = new Text();
        this.helperText.getStyleClass().add("helper-text");
        this.helperText.visibleProperty().bind(this.control.isShowHelperTextProperty());
        this.helperText.textProperty().bind(this.control.helperTextProperty());
        StackPane.setAlignment(this.helperText, Pos.CENTER_LEFT);
        this.helperContainer.getChildren().add(this.helperText);
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
        this.errorContainer.getChildren().add(this.errorText);
    }
}
