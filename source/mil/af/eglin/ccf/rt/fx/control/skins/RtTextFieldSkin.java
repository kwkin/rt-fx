package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.RtGlyph;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

import java.lang.reflect.Field;

public class RtTextFieldSkin extends TextFieldSkin
{
    private final TextField textField;
    private final StackPane overlayContainer = new StackPane();
    private final StackPane inputContainer = new StackPane();
    private final StackPane promptContainer = new StackPane();
    private final StackPane graphicContainer = new StackPane();
    
    private Text promptText;
    private Pane textPane;
    private PromptInput linesWrapper;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;
        
        textPane = (Pane) getChildren().get(0);
        getChildren().remove(textPane);
        inputContainer.getStyleClass().add("input-container");
        inputContainer.getChildren().add(textPane);

        overlayContainer.getStyleClass().add("overlay-container");
        overlayContainer.setOpacity(0);
        
        linesWrapper = new PromptInput(textField, overlayContainer, this.promptTextFill, textField.textProperty(),
                textField.promptTextProperty(), () -> promptText);

        promptContainer.getStyleClass().add("prompt-container");
        linesWrapper.init(() -> createPromptNode(), textPane);

        graphicContainer.getStyleClass().add("graphic-container");
        
        updateOverlayColor();
        getChildren().addAll(overlayContainer, linesWrapper.unfocusedLine, linesWrapper.focusedLine, promptContainer, inputContainer);
        RtGlyph glyph = this.textField.getTrailingGlyph();
        if (glyph != null)
        {
            getChildren().add(glyph.getGlyph());
        }

        registerChangeListener(textField.labelFloatProperty(), textField.labelFloatProperty().getName());
        registerChangeListener(textField.focusColorProperty(),  textField.focusColorProperty().getName());
        registerChangeListener(textField.getOverlayColorProperty(),  textField.getOverlayColorProperty().getName());
        registerChangeListener(textField.unfocusProperty(),  textField.unfocusProperty().getName());
        registerChangeListener(textField.trailingGlyphProperty(), textField.trailingGlyphProperty().getName());
        
        // TODO determine why this does not work
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
        else if (textField.getOverlayColorProperty().getName().equals(propertyReference))
        {
            updateOverlayColor();
        }
        else if (textField.labelFloatProperty().getName().equals(propertyReference))
        {
        }
        else if (textField.trailingGlyphProperty().getName().equals(propertyReference))
        {
            this.textField.layout();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        
        double controlHeight = textField.getHeight();
        double controlWidth = textField.getWidth();
        
        double promptTopPadding = this.promptContainer.getPadding().getTop();
        double inputTopPadding = this.inputContainer.getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;
        
        this.linesWrapper.layoutComponents(x, y, w, h, controlHeight, controlWidth, translateY);
        this.linesWrapper.updateLabelFloatLayout();
        
        this.promptContainer.resizeRelocate(0, 0, controlWidth, controlHeight);
        this.overlayContainer.resizeRelocate(0, 0, controlWidth, controlHeight);
        
        RtGlyph graphic = this.textField.getTrailingGlyph();
        if (graphic != null)
        {
            double graphicWidth = graphic.getGlyph().getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth - textField.getTrailingIconGap();
            positionInArea(graphic.getGlyph(), xPosition, controlHeight / 2, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
        }
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
    
    private void updateOverlayColor()
    {
        CornerRadii radii = this.textField.getBackground() == null ? null : this.textField.getBackground().getFills().get(0).getRadii(); 
        this.overlayContainer.setBackground(new Background(new BackgroundFill(this.textField.getOverlayColor(), radii, Insets.EMPTY)));
    }
    
    // TODO override preferred height and width
}