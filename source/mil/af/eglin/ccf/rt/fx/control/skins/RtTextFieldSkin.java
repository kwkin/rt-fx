package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.text.HitInfo;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

import java.lang.reflect.Field;

public class RtTextFieldSkin extends TextFieldSkin
{
    private final TextField textField;
    private final StackPane overlayContainer = new StackPane();
    private final StackPane inputContainer = new StackPane();
    private final StackPane promptContainer = new StackPane();
    
    private final StackPane textContainer = new StackPane();
    private final StackPane helperContainer = new StackPane();
    private final ValidableContainer errorContainer;
    
    private Text promptText;
    private Text helperText;
    private Pane textPane;
    private PromptInput linesWrapper;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;
        
        textPane = (Pane) getChildren().get(0);
        getChildren().remove(textPane);
        inputContainer.setManaged(false);
        inputContainer.getStyleClass().add("input-container");
        inputContainer.getChildren().add(textPane);

        overlayContainer.getStyleClass().add("overlay-container");
        overlayContainer.setOpacity(0);
        
        linesWrapper = new PromptInput(textField, overlayContainer, this.promptTextFill, textField.textProperty(),
                textField.promptTextProperty(), () -> promptText);

        promptContainer.getStyleClass().add("prompt-container");
        linesWrapper.init(() -> createPromptText(), textPane);

        helperContainer.getStyleClass().add("helper-container");

        updateOverlayColor();
        updateTrailingIconColor();
        createHelperText();
        this.errorContainer = new ValidableContainer(this.textField);
        this.textContainer.getChildren().addAll(helperContainer, errorContainer);
        this.helperContainer.visibleProperty().bind(this.textField.isValidProperty());
        this.errorContainer.visibleProperty().bind(this.textField.isValidProperty().not());
        
        getChildren().addAll(inputContainer, overlayContainer, linesWrapper.unfocusedLine, linesWrapper.focusedLine, promptContainer, textContainer);
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
        registerChangeListener(textField.trailingGlyphColorProperty(),  textField.trailingGlyphColorProperty().getName());
    }

    @Override
    public HitInfo getIndex(double x, double y) 
    {
        Point2D p;
        p = new Point2D(x - snappedLeftInset() - this.inputContainer.getPadding().getLeft(),
                        y - snappedTopInset() - (2 * this.inputContainer.getPadding().getTop()));

        Text text = ((Text)textPane.getChildren().get(1));
        // TODO replace with text.hitTest(p) when using future JavaFX versions (9+)
        @SuppressWarnings("deprecation")
        HitInfo hitInfo = text.impl_hitTestChar(translateCaretPosition(p));
        return hitInfo;
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
            // TODO complete this
        }
        else if (textField.trailingGlyphProperty().getName().equals(propertyReference))
        {
            this.textField.layout();
        }
        else if (textField.trailingGlyphColorProperty().getName().equals(propertyReference))
        {
            updateTrailingIconColor();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);

        
        double promptTopPadding = this.promptContainer.getPadding().getTop();
        double inputTopPadding = this.inputContainer.getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;

        double inputHeight = this.textField.getIsShowHelperText() ? h - this.textField.getHelperTextHeight() : h;
        this.linesWrapper.layoutComponents(x, y, w, inputHeight, translateY);
        this.linesWrapper.updateLabelFloatLayout();

        this.inputContainer.resizeRelocate(x, y, w, inputHeight);
        this.promptContainer.resizeRelocate(x, y, w, inputHeight);
        this.overlayContainer.resizeRelocate(x, y, w, inputHeight);

        this.textContainer.resizeRelocate(x, inputHeight, w, this.textField.getHelperTextHeight());
        
        RtGlyph graphic = this.textField.getTrailingGlyph();
        if (graphic != null)
        {
            double graphicWidth = graphic.getGlyph().getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth - textField.getTrailingIconGap();
            double inputYCenter = y + inputHeight / 2;
            positionInArea(graphic.getGlyph(), xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
            updateTrailingIconColor();
        }
    }

    private void createPromptText()
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
            this.linesWrapper.promptTextScale.setX(0.85);
            this.linesWrapper.promptTextScale.setY(0.85);
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
    
    private void createHelperText()
    {
        if (this.helperText != null || !this.textField.getIsShowHelperText())
        {
            return;
        }
        this.helperText = new Text();
        this.helperText.getStyleClass().add("helper-text");
        this.helperText.visibleProperty().bind(this.textField.isShowHelperTextProperty());
        this.helperText.textProperty().bind(this.textField.helperTextProperty());
        StackPane.setAlignment(this.helperText, Pos.CENTER_LEFT);
        this.helperContainer.getChildren().add(this.helperText);
    }
    
    private void updateOverlayColor()
    {
        CornerRadii radii = this.textField.getBackground() == null ? null : this.textField.getBackground().getFills().get(0).getRadii(); 
        this.overlayContainer.setBackground(new Background(new BackgroundFill(this.textField.getOverlayColor(), radii, Insets.EMPTY)));
    }
    
    private void updateTrailingIconColor()
    {
        RtGlyph graphic = this.textField.getTrailingGlyph();
        if (graphic != null && graphic.isGlyphColorManaged())
        {
            graphic.setGlyphFill(this.textField.getTrailingGlyphColor());
        }
    }
}