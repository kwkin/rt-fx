package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ColorPickerSkin;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.ColorPicker;
import mil.af.eglin.ccf.rt.fx.control.validation.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

public class RtColorPickerSkin extends ColorPickerSkin
{
    protected StackPane iconPane;
    private final Node colorPalette;
    private final ColorPicker colorPicker;
    private final PromptInput<ColorPicker> input;
    private final DescriptionContainer<ColorPicker> descriptionContainer;
    private Label label;

    private Text promptText;

    public RtColorPickerSkin(final ColorPicker colorPicker)
    {
        super(colorPicker);
        this.colorPicker = colorPicker;

        ObservableValue<Boolean> toggleFlag = this.colorPicker.showingProperty();
        this.input = new PromptInput<>(colorPicker, colorPicker.valueProperty(), colorPicker.unfocusColorProperty(),
                colorPicker.promptTextProperty(), () -> promptText, toggleFlag);
        this.input.init(() -> createPromptText());
        this.input.updateOverlayColor(this.colorPicker.getOverlayColor());
        this.descriptionContainer = new DescriptionContainer<ColorPicker>(colorPicker);

        this.colorPalette = getPopupContent();
        getChildren().remove(this.arrowButton);
        // @formatter:off
        getChildren().addAll(this.input.getInputContainer(), 
                this.input.getOverlayContainer(),
                this.input.getUnfocusedLine(), 
                this.input.getFocusedLine(), 
                this.input.getPromptContainer(),
                this.arrowButton,
                this.descriptionContainer);
        // @formatter:on
        updateDisplayArea();
        updatePopupLocation();
        
        colorPicker.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>()
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c)
            {
                if (c.next() && c.wasAdded())
                {
                    updateColor(colorPicker.getValue());
                }
            }
        });
        colorPicker.valueProperty().addListener((ov, oldVal, newVal) ->
        {
            Node icon = iconPane == null ? colorPicker.lookup(".picker-color") : iconPane;
            if (icon != null)
            {
                updateColor(colorPicker.getValue());
            }
        });

        registerChangeListener(colorPicker.labelFloatProperty(), colorPicker.labelFloatProperty().getName());
        registerChangeListener(colorPicker.focusColorProperty(), colorPicker.focusColorProperty().getName());
        registerChangeListener(colorPicker.overlayColorProperty(), colorPicker.overlayColorProperty().getName());
        registerChangeListener(colorPicker.unfocusColorProperty(), colorPicker.unfocusColorProperty().getName());
        registerChangeListener(colorPicker.isShowHelperTextProperty(), colorPicker.isShowHelperTextProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (this.colorPicker.focusColorProperty().getName().equals(property))
        {
            this.input.updateFocusColor();
        }
        else if (this.colorPicker.unfocusColorProperty().getName().equals(property))
        {
            this.input.updateUnfocusColor();
        }
        else if (this.colorPicker.overlayColorProperty().getName().equals(property))
        {
            this.input.updateOverlayColor(this.colorPicker.getOverlayColor());
        }
    }

    @Override
    protected void updateDisplayArea()
    {
        super.updateDisplayArea();
        if (this.input != null)
        {
            final Node newDisplayNode = getDisplayNode();
            if (newDisplayNode != null)
            {
                getChildren().remove(newDisplayNode);
                getChildren().add(newDisplayNode);
                this.input.addInput(newDisplayNode);
            }
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        if (this.colorPicker.isHelperTextVisible())
        {
            layoutInArea(this.descriptionContainer, x, y, w, h, -1, HPos.CENTER, VPos.CENTER);
        }
        double inputHeight = this.colorPicker.isHelperTextVisible() ? h - this.descriptionContainer.getHeight() : h;
        double promptTopPadding = this.input.getPromptContainer().getPadding().getTop();
        double inputTopPadding = this.input.getInputContainer().getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;
        this.input.layoutComponents(x, y, w, inputHeight, translateY);
        this.input.updateLabelFloatLayout();

        this.descriptionContainer.resizeRelocate(x, inputHeight, w, this.descriptionContainer.getHeight());
        this.input.getPromptContainer().resizeRelocate(x, y, w - this.arrowButton.getLayoutBounds().getWidth(),
                inputHeight);

        if (arrowButton != null)
        {
            double graphicWidth = arrowButton.getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth;
            double inputYCenter = y + inputHeight / 2;
            positionInArea(arrowButton, xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
        }

        Node displayNode = this.input.getDisplayNode();
        updateDisplayArea();
        if (displayNode != null)
        {
            Region inputContainer = this.input.getInputContainer();

            double inputX = inputContainer.snappedLeftInset();
            double inputY = inputContainer.snappedTopInset();
            double inputW = snapSize(inputContainer.getWidth()) - inputX - inputContainer.snappedRightInset();
            double inputH = snapSize(inputContainer.getHeight()) - inputY - inputContainer.snappedBottomInset();
            if (this.colorPicker.isEditable())
            {
                displayNode.resizeRelocate(inputX, y, inputW, inputHeight);
                ((javafx.scene.control.TextField) displayNode)
                        .setPadding(new Insets(inputY - 1, 0, inputContainer.snappedBottomInset() - 1, 0));
            }
            else
            {
                displayNode.resizeRelocate(inputX, inputY, inputW, inputH);
            }
        }
    }

    private void updateColor(Color color)
    {
        String colorText = color.toString().replaceFirst("0x", "");
        String colorStyle = String.format("-fx-background-color:#%s", colorText);
        if (this.label == null)
        {
            this.label = (Label) colorPicker.lookup(".color-picker-label");
        }
        switch (colorPicker.getButtonStyle())
        {
            case BUTTON:
                this.colorPicker.setStyle(colorStyle);
                if (label != null)
                {
                    if (Utils.computeTextColor(color))
                    {
                        this.label.setTextFill(Color.BLACK);
                    }
                    else
                    {
                        this.label.setTextFill(Color.WHITE);
                    }
                }
                break;
            case COMBO_BOX:
            case ICON:
                if (label != null)
                {
                    Node node = label.getGraphic();
                    if (node != null && node instanceof StackPane)
                    {
                        iconPane = (StackPane) node;
                        iconPane.setStyle(colorStyle);
                    }
                    break;
                }
            default:
                break;

        }
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
        this.promptText.textProperty().bind(this.colorPicker.promptTextProperty());

        this.colorPicker.editableProperty().addListener((ov, oldVal, newVal) ->
        {
            updatePromptTranslation();
        });
        updatePromptTranslation();

        this.promptText.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        this.promptText.getTransforms().add(this.input.getPromptTextScale());
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.input.addPromptText(this.promptText);
    }
    
    private void updatePromptTranslation()
    {
        this.promptText.setTranslateX(this.colorPicker.isEditable() ? 1 : 0);
    }

    private void updatePopupLocation()
    {
        if (this.colorPicker.isHelperTextVisible() && !this.colorPalette.translateYProperty().isBound())
        {
            this.colorPalette.translateYProperty().bind(this.descriptionContainer.heightProperty().multiply(-1));
        }
    }
}
