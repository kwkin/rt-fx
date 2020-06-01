package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.text.HitInfo;

import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.RtGlyph;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.validation.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

import java.lang.reflect.Field;

// TODO generalize input container, prompt container, prompt text, etc into another class that can be used by combo-box and text area
public class RtTextFieldSkin extends TextFieldSkin
{
    private final TextField textField;

    private final PromptInput<TextField> linesWrapper;
    private final DescriptionContainer<TextField> descriptionContainer;

    private Text promptText;
    private Pane textPane;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;

        textPane = (Pane) getChildren().get(0);
        getChildren().remove(textPane);
        

        linesWrapper = new PromptInput<>(textField, textField.textProperty(), this.promptTextFill, 
                textField.promptTextProperty(), () -> promptText, this.textField.focusedProperty());

        linesWrapper.addInput(textPane);
        
        linesWrapper.init(() -> createPromptText(), textPane);

        linesWrapper.updateOverlayColor(this.textField.getOverlayColor());
        updateTrailingIconColor();
        this.descriptionContainer = new DescriptionContainer<TextField>(textField);

        getChildren().addAll(linesWrapper, descriptionContainer);
        RtGlyph glyph = textField.getTrailingGlyph();
        if (glyph != null)
        {
            getChildren().add(glyph.getGlyph());
        }

        registerChangeListener(textField.labelFloatProperty(), textField.labelFloatProperty().getName());
        registerChangeListener(textField.focusColorProperty(), textField.focusColorProperty().getName());
        registerChangeListener(textField.getOverlayColorProperty(), textField.getOverlayColorProperty().getName());
        registerChangeListener(textField.unfocusProperty(), textField.unfocusProperty().getName());
        registerChangeListener(textField.trailingGlyphProperty(), textField.trailingGlyphProperty().getName());
        registerChangeListener(textField.trailingGlyphColorProperty(),
                textField.trailingGlyphColorProperty().getName());
        registerChangeListener(textField.isShowHelperTextProperty(), textField.isShowHelperTextProperty().getName());
    }

    @Override
    public HitInfo getIndex(double x, double y)
    {
        Point2D p = new Point2D(x - snappedLeftInset() - this.linesWrapper.getInputPadding().getLeft(),
                y - snappedTopInset() - (2 * this.linesWrapper.getInputPadding().getTop()));

        Text text = ((Text) textPane.getChildren().get(1));
        // TODO replace with text.hitTest(p) JavaFX versions 9+
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
            linesWrapper.updateOverlayColor(this.textField.getOverlayColor());
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

        double inputHeight = this.textField.isHelperTextVisible() ? h - this.textField.getHelperTextHeight() : h;
        this.linesWrapper.layoutComponents(x, y, w, inputHeight);
        this.linesWrapper.updateLabelFloatLayout();

        this.descriptionContainer.resizeRelocate(x, inputHeight, w, this.textField.getHelperTextHeight());

        RtGlyph graphic = this.textField.getTrailingGlyph();
        double promptWidth = w;
        if (graphic != null)
        {
            double graphicWidth = graphic.getGlyph().getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth - textField.getTrailingIconGap();
            double inputYCenter = y + inputHeight / 2;
            positionInArea(graphic.getGlyph(), xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
            updateTrailingIconColor();

            double graphicLeftGap = graphicWidth + 2 * textField.getTrailingIconGap();
            double inputRightPadding = graphicLeftGap - this.linesWrapper.getInputPadding().getRight();
            inputRightPadding = Math.max(inputRightPadding, this.linesWrapper.getInputPadding().getRight());
            this.linesWrapper.setInputPadding(inputRightPadding);

            promptWidth -= graphicLeftGap;
        }
        this.linesWrapper.resizePrompt(x, y, promptWidth, inputHeight);
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
        this.linesWrapper.addPromptText(this.promptText);

        if (this.textField.isFocused() && this.textField.isLabelFloat())
        {
            this.promptText.setTranslateY(-Math.floor(this.textPane.getHeight()));
            this.linesWrapper.promptTextScale.setX(0.85);
            this.linesWrapper.promptTextScale.setY(0.85);
        }
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

    private void updateTrailingIconColor()
    {
        RtGlyph graphic = this.textField.getTrailingGlyph();
        if (graphic != null && graphic.isGlyphColorManaged())
        {
            graphic.setGlyphFill(this.textField.getTrailingGlyphColor());
        }
    }
}