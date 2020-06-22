package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.text.HitInfo;

import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.control.RtIcon;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

import java.lang.reflect.Field;

public class RtTextFieldSkin extends TextFieldSkin
{
    private final TextField textField;

    private final PromptInput<TextField> input;
    private final DescriptionContainer<TextField> descriptionContainer;

    private Text textNode;
    private Text promptText;
    private Pane textGroup;
    private Text trailingTextNode;

    public RtTextFieldSkin(final TextField textField)
    {
        super(textField);
        this.textField = textField;

        this.textGroup = (Pane) getChildren().get(0);
        for (Node node : this.textGroup.getChildren())
        {
            if (node instanceof Text)
            {
                this.textNode = (Text) node;
            }
        }

        this.input = new PromptInput<>(textField, textField.textProperty(), this.promptTextFill,
                textField.promptTextProperty(), () -> this.promptText, this.textField.focusedProperty());
        this.input.init(() -> createPromptText(), this.textGroup);
        this.input.updateOverlayColor(this.textField.getOverlayColor());
        this.descriptionContainer = new DescriptionContainer<>(textField);

        updateTrailingIconColor();

        getChildren().remove(this.textGroup);
        this.input.addInput(this.textGroup);
        // @formatter:off
        getChildren().addAll(this.input.getInputContainer(), 
                this.input.getOverlayContainer(), 
                this.input.getUnfocusedLine(), 
                this.input.getFocusedLine(),
                this.input.getPromptContainer(),
                this.descriptionContainer);
        // @formatter:on
        createTrailingText();
        addTrailingIcon();

        registerChangeListener(textField.labelFloatProperty(), textField.labelFloatProperty().getName());
        registerChangeListener(textField.focusColorProperty(), textField.focusColorProperty().getName());
        registerChangeListener(textField.overlayColorProperty(), textField.overlayColorProperty().getName());
        registerChangeListener(textField.unfocusColorProperty(), textField.unfocusColorProperty().getName());
        registerChangeListener(textField.trailingTextProperty(), textField.trailingTextProperty().getName());
        registerChangeListener(textField.trailingIconProperty(), textField.trailingIconProperty().getName());
        registerChangeListener(textField.trailingIconColorProperty(), textField.trailingIconColorProperty().getName());
        registerChangeListener(textField.isShowHelperTextProperty(), textField.isShowHelperTextProperty().getName());
    }

    @Override
    public HitInfo getIndex(double x, double y)
    {
        Pane inputContainer = this.input.getInputContainer();
        Point2D p = new Point2D(x - snappedLeftInset() - inputContainer.getPadding().getLeft(),
                y - snappedTopInset() - (2 * inputContainer.getPadding().getTop()));

        Text text = ((Text) textGroup.getChildren().get(1));
        // Replace with text.hitTest(p) for JavaFX versions 9+
        @SuppressWarnings("deprecation")
        HitInfo hitInfo = text.impl_hitTestChar(translateCaretPosition(p));
        return hitInfo;
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (this.textField.focusColorProperty().getName().equals(property))
        {
            this.input.updateFocusColor();
        }
        else if (this.textField.unfocusColorProperty().getName().equals(property))
        {
            this.input.updateUnfocusColor();
        }
        else if (this.textField.overlayColorProperty().getName().equals(property))
        {
            this.input.updateOverlayColor(this.textField.getOverlayColor());
        }
        else if (this.textField.trailingTextProperty().getName().equals(property))
        {
            this.textField.layout();
        }
        else if (this.textField.trailingIconProperty().getName().equals(property))
        {
            this.textField.layout();
        }
        else if (this.textField.trailingIconColorProperty().getName().equals(property))
        {
            updateTrailingIconColor();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        double inputHeight = this.textField.isHelperTextVisible() ? h - this.descriptionContainer.getHeight() : h;
        double promptTopPadding = this.input.getPromptContainer().getPadding().getTop();
        double inputTopPadding = this.input.getInputContainer().getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;
        this.input.layoutComponents(x, y, w, inputHeight, translateY);
        this.input.updateLabelFloatLayout();

        this.descriptionContainer.resizeRelocate(x, inputHeight, w, this.descriptionContainer.getHeight());

        Pane inputContainer = this.input.getInputContainer();
        String trailingText = textField.getTrailingText();
        RtIcon graphic = this.textField.getTrailingIcon();
        double promptWidth = w;
        if (this.textField.isTrailingVisible())
        {
            if (trailingText != null && !"".equals(trailingText))
            {
                if (!this.getChildren().contains(this.trailingTextNode))
                {
                    createTrailingText();
                }
                Font font = this.textField.getFont();
                double textWidth = Utils.computeTextWidth(font, trailingText, 0);
                double xPosition = w - textWidth - this.textField.getTrailingIconGap();
                double inputYCenter = y + inputHeight / 2;
                positionInArea(this.trailingTextNode, xPosition, inputYCenter, textWidth, 0, 0, HPos.CENTER,
                        VPos.CENTER);

                double graphicLeftGap = textWidth + 2 * textField.getTrailingIconGap();
                double inputRightPadding = graphicLeftGap - inputContainer.getPadding().getRight();
                inputRightPadding = Math.max(inputRightPadding, inputContainer.getPadding().getRight());
                this.input.getInputDisplayContainer().setPadding(new Insets(0, inputRightPadding, 0, 0));

                promptWidth -= graphicLeftGap;
            }
            else if (graphic != null)
            {
                double graphicWidth = graphic.getSize();
                double xPosition = w - graphicWidth - textField.getTrailingIconGap();
                double inputYCenter = y + inputHeight / 2;
                positionInArea(graphic.getNode(), xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER,
                        VPos.CENTER);
                updateTrailingIconColor();

                double graphicLeftGap = graphicWidth + 2 * textField.getTrailingIconGap();
                double inputRightPadding = graphicLeftGap - inputContainer.getPadding().getRight();
                inputRightPadding = Math.max(inputRightPadding, inputContainer.getPadding().getRight());
                this.input.getInputDisplayContainer().setPadding(new Insets(0, inputRightPadding, 0, 0));

                promptWidth -= graphicLeftGap;
            }
        }
        input.getPromptContainer().resizeRelocate(x, y, promptWidth, inputHeight);

        if (textNode != null)
        {
            double textY;
            Bounds textNodeBounds = textNode.getLayoutBounds();
            double ascent = textNode.getBaselineOffset();
            double descent = textNodeBounds.getHeight() - ascent;
            double height = inputHeight - inputContainer.getPadding().getTop()
                    - inputContainer.getPadding().getBottom();

            switch (getSkinnable().getAlignment().getVpos())
            {
                case TOP:
                    textY = ascent;
                    break;

                case CENTER:
                    textY = (ascent + height - descent) / 2;
                    break;

                case BOTTOM:
                default:
                    textY = height - descent;
            }
            textNode.setY(textY);
        }
    }

    private void createTrailingText()
    {
        String trailingText = textField.getTrailingText();
        if (this.trailingTextNode != null || !this.textField.isTrailingVisible())
        {
            return;
        }
        this.trailingTextNode = new Text(trailingText);
        this.trailingTextNode.getStyleClass().add("trailing-text");
        this.trailingTextNode.visibleProperty().bind(this.textField.isTrailingVisibleProperty());
        this.trailingTextNode.fontProperty().bind(this.textField.fontProperty());
        this.trailingTextNode.textProperty().bind(this.textField.trailingTextProperty());
        this.trailingTextNode.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        getChildren().add(this.trailingTextNode);
    }

    private void createPromptText()
    {
        if (this.promptText != null || !this.input.isUsingPromptTextProperty().get())
        {
            return;
        }
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(this.input.isUsingPromptTextProperty());
        this.promptText.fontProperty().bind(this.textField.fontProperty());
        this.promptText.textProperty().bind(this.textField.promptTextProperty());
        this.promptText.translateXProperty().set(1);
        this.promptText.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        this.promptText.getTransforms().add(this.input.getPromptTextScale());
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.input.addPromptText(this.promptText);

        if (this.textField.isFocused() && this.textField.isLabelFloat())
        {
            this.promptText.setTranslateY(-Math.floor(this.textGroup.getHeight()));
        }
        try
        {
            Field field = TextFieldSkin.class.getDeclaredField("promptNode");
            field.setAccessible(true);
            Object oldValue = field.get(this);
            if (oldValue != null)
            {
                this.textGroup.getChildren().remove(oldValue);
            }
            field.set(this, promptText);
        }
        catch (Exception e)
        {
        }
    }

    private void addTrailingIcon()
    {
        RtIcon icon = this.textField.getTrailingIcon();
        if (icon == null || getChildren().contains(icon))
        {
            return;
        }
        Bindings.createBooleanBinding(() -> isTrailingIconVisible(), this.textField.isTrailingVisibleProperty(),
                this.textField.trailingTextProperty(), this.textField.trailingIconProperty());
        getChildren().add(icon.getNode());
    }

    private void updateTrailingIconColor()
    {
        RtIcon graphic = this.textField.getTrailingIcon();
        if (graphic != null && graphic.isColorManaged())
        {
            graphic.setFill(this.textField.getTrailingIconColor());
        }
    }

    private boolean isTrailingIconVisible()
    {
        boolean isShowingText = !this.textField.getTrailingText().isEmpty() && !"".equals(this.textField.getTrailingText());
        return this.textField.isTrailingVisible() && !isShowingText;
    }
}