package mil.af.eglin.ccf.rt.fx.control.skins;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import com.sun.javafx.scene.text.HitInfo;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

public class RtTextAreaSkin extends TextAreaSkin
{
    private final TextArea textArea;

    private final PromptInput<TextArea> input;
    private final DescriptionContainer<TextArea> descriptionContainer;

    private Text textNode;
    private Text promptText;
    private ScrollPane scrollPane;

    public RtTextAreaSkin(final TextArea textArea)
    {
        super(textArea);
        this.textArea = textArea;

        this.scrollPane = (ScrollPane) getChildren().get(0);
        this.textNode = (Text)this.scrollPane.getContent().lookup(".text");
        
        this.input = new PromptInput<>(textArea, textArea.textProperty(), this.promptTextFill, 
                textArea.promptTextProperty(), () -> this.promptText, this.textArea.focusedProperty());
        this.input.init(() -> createPromptText());
        this.input.updateOverlayColor(this.textArea.getOverlayColor());
        this.descriptionContainer = new DescriptionContainer<>(textArea);
        
        getChildren().remove(this.scrollPane);
//        this.linesWrapper.addInput(this.scrollPane);
        getChildren().addAll(this.input.getInputContainer(), 
                this.input.getOverlayContainer(), 
                this.scrollPane,
                this.input.getUnfocusedLine(), 
                this.input.getFocusedLine(),
                this.input.getPromptContainer(),
                this.descriptionContainer);
        
        registerChangeListener(textArea.labelFloatProperty(), textArea.labelFloatProperty().getName());
        registerChangeListener(textArea.focusColorProperty(), textArea.focusColorProperty().getName());
        registerChangeListener(textArea.getOverlayColorProperty(), textArea.getOverlayColorProperty().getName());
        registerChangeListener(textArea.unfocusColorProperty(), textArea.unfocusColorProperty().getName());
        registerChangeListener(textArea.isShowHelperTextProperty(), textArea.isShowHelperTextProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference)
    {
        super.handleControlPropertyChanged(propertyReference);
        if (textArea.focusColorProperty().getName().equals(propertyReference))
        {
            input.updateFocusColor();
        }
        else if (textArea.unfocusColorProperty().getName().equals(propertyReference))
        {
            input.updateUnfocusColor();
        }
        else if (textArea.getOverlayColorProperty().getName().equals(propertyReference))
        {
            input.updateOverlayColor(this.textArea.getOverlayColor());
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        if (this.textArea.isHelperTextVisible())
        {
            layoutInArea(this.descriptionContainer, x, y, w, h, -1, HPos.CENTER, VPos.CENTER);
        }

        double inputHeight = this.textArea.isHelperTextVisible() ? h - this.descriptionContainer.getHeight() : h;
        double promptTopPadding = this.input.getPromptContainer().getPadding().getTop();
        double inputTopPadding = this.input.getInputContainer().getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 4;
        this.input.layoutComponents(x, y, w, inputHeight, translateY);
        this.scrollPane.resizeRelocate(x, y, w, inputHeight);
        this.input.updateLabelFloatLayout();

        this.descriptionContainer.resizeRelocate(x, inputHeight, w, this.descriptionContainer.getHeight());
        this.input.getPromptContainer().resizeRelocate(x, y, w, 48);
    }

    private void createPromptText()
    {
        if (this.promptText != null || !this.input.isUsingPromptTextProperty().get())
        {
            return;
        }
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(input.isUsingPromptTextProperty());
        this.promptText.fontProperty().bind(this.textArea.fontProperty());
        this.promptText.textProperty().bind(this.textArea.promptTextProperty());
        this.promptText.translateXProperty().set(1);
        this.promptText.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        this.promptText.getTransforms().add(this.input.getPromptTextScale());
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.input.addPromptText(this.promptText);

        if (this.textArea.isFocused() && this.textArea.isLabelFloat())
        {
            promptText.setTranslateY(-Math.floor(scrollPane.getHeight()));
        }
        try {
            reflectionFieldConsumer("promptNode", field -> {
                Object oldValue = field.get(this);
                if (oldValue != null) {
                    removeHighlight(Arrays.asList(((Node) oldValue)));
                }
                field.set(this, promptText);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private <T> void reflectionFieldConsumer(String fieldName, CheckedConsumer<Field> consumer) {
        Field field = null;
        try {
            field = TextAreaSkin.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            consumer.accept(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private interface CheckedConsumer<T> {
        void accept(T t) throws Exception;
    }
}
