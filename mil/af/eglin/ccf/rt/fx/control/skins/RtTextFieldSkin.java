package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.style.PromptLinesWrapper;

import java.lang.reflect.Field;

public class RtTextFieldSkin extends TextFieldSkin
{
    private TextField textField;

    private StackPane promptTextContainer = new StackPane();
    private Text promptText;
    private Pane textPane;

    private PromptLinesWrapper linesWrapper;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;
        textPane = (Pane) this.getChildren().get(0);

        linesWrapper = new PromptLinesWrapper(textField, this.promptTextFill, textField.textProperty(),
                textField.promptTextProperty(), () -> promptText);

        promptTextContainer.getStyleClass().add("prompt-text-container");
        linesWrapper.init(() -> createPromptNode(), textPane);

        
        getChildren().addAll(linesWrapper.focusedLine, promptTextContainer, linesWrapper.promptContainer);

        registerChangeListener(textField.labelFloatProperty(), "LABEL_FLOAT");
        registerChangeListener(textField.focusColorProperty(), "FOCUS_COLOR");
        registerChangeListener(textField.unFocusColorProperty(), "UNFOCUS_COLOR");
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference)
    {
        if ("FOCUS_COLOR".equals(propertyReference))
        {
            linesWrapper.updateFocusColor();
        }
        else if ("LABEL_FLOAT".equals(propertyReference))
        {
            
        }
        else
        {
            super.handleControlPropertyChanged(propertyReference);
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        double height = textField.getHeight();
        double width = textField.getWidth();
        
        this.linesWrapper.layoutLines(x, y, w, h, height, width, height - h - 8);
        this.linesWrapper.updateLabelFloatLayout();
        
        this.promptTextContainer.resizeRelocate(x, 0, w, height);
        this.promptText.resizeRelocate(0, y + 2, w, h);
    }

    private void createPromptNode()
    {
        if (this.promptText != null || !this.linesWrapper.usePromptText.get())
        {
            return;
        }
        this.promptText = new Text();
        this.promptText.getStyleClass().add("text");
        this.promptText.setManaged(false);
        this.promptText.visibleProperty().bind(linesWrapper.usePromptText);
        this.promptText.fontProperty().bind(this.textField.fontProperty());
        this.promptText.textProperty().bind(this.textField.promptTextProperty());
        this.promptText.fillProperty().bind(this.linesWrapper.animatedPromptTextFill);
        this.promptText.setTranslateX(1);
        this.promptText.getTransforms().add(this.linesWrapper.promptTextScale);
        this.promptTextContainer.getChildren().add(this.promptText);
        
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