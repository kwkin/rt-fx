package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.style.PromptLinesWrapper;

import java.lang.reflect.Field;

public class RtTextFieldSkin extends TextFieldSkin
{
    private TextField textField;

    private StackPane inputContainer = new StackPane();
    private StackPane promptContainer = new StackPane();
    private Text promptText;
    private Pane textPane;

    private PromptLinesWrapper linesWrapper;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;
        
        textPane = (Pane) getChildren().get(0);
        getChildren().remove(textPane);
        inputContainer.getStyleClass().add("input-container");
        inputContainer.getChildren().add(textPane);

        linesWrapper = new PromptLinesWrapper(textField, this.promptTextFill, textField.textProperty(),
                textField.promptTextProperty(), () -> promptText);

        promptContainer.getStyleClass().add("prompt-container");
        linesWrapper.init(() -> createPromptNode(), textPane);

        getChildren().addAll(linesWrapper.unfocusedLine, linesWrapper.focusedLine, promptContainer, inputContainer);

        registerChangeListener(textField.labelFloatProperty(), textField.labelFloatProperty().getName());
        registerChangeListener(textField.focusColorProperty(),  textField.focusColorProperty().getName());
        registerChangeListener(textField.unfocusProperty(),  textField.unfocusProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference)
    {
        super.handleControlPropertyChanged(propertyReference);
        if (textField.focusColorProperty().getName().equals(propertyReference))
        {
            linesWrapper.updateFocusColor();
        }
        else if (textField.unfocusProperty().getName().equals(propertyReference))
        {
            linesWrapper.updateUnfocusColor();
        }
        else if (textField.labelFloatProperty().getName().equals(propertyReference))
        {
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        
        double controlHeight = textField.getHeight();
        double controlWidth = textField.getWidth();
        
        double translateY = (controlHeight / 2) - x;
        
        this.linesWrapper.layoutComponents(x, y, w, h, controlHeight, controlWidth, translateY);
        this.linesWrapper.updateLabelFloatLayout();
        
        this.promptContainer.resizeRelocate(0, 0, controlWidth, controlHeight);
    }

    private void createPromptNode()
    {
        if (this.promptText != null || !this.linesWrapper.usePromptText.get())
        {
            return;
        }
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(linesWrapper.usePromptText);
        this.promptText.fontProperty().bind(this.textField.fontProperty());
        this.promptText.textProperty().bind(this.textField.promptTextProperty());
        this.promptText.translateXProperty().set(1);
        this.promptText.fillProperty().bind(this.linesWrapper.animatedPromptTextFill);
        this.promptText.getTransforms().add(this.linesWrapper.promptTextScale);
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.promptContainer.getChildren().add(this.promptText);
        
        if (this.textField.isFocused() && this.textField.isLabelFloat())
        {
            this.promptText.setTranslateY(-Math.floor(this.textPane.getHeight()));
            this.linesWrapper.promptTextScale.setX(0.7);
            this.linesWrapper.promptTextScale.setY(0.7);
        }
        updatePromptText();
    }
    
    private void updatePromptText()
    {
        try
        {
            Field field = TextFieldSkin.class.getDeclaredField("promptNode");
            field.setAccessible(true);
            Object oldValue = field.get(this);
            if (oldValue != null)
            {
                textPane.getChildren().remove(oldValue);
            }
            field.set(this, promptText);
        }
        catch (Exception e)
        {
        }
    }
}