package mil.af.eglin.ccf.rt.fx.control.skins;


import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

public class RtComboBoxSkin<T> extends ComboBoxListViewSkin<T>
{
    private final ComboBox<T> comboBox;
    
    private Node arrowButton;
    private Node listView;
    private StackPane overlayContainer = new StackPane();
    private StackPane inputContainer = new StackPane();
    private StackPane promptContainer = new StackPane();
    private StackPane textContainer = new StackPane();
    private StackPane helperContainer = new StackPane();
    private ValidableContainer<T> errorContainer;

    private Text promptText;
    private Text helperText;
    private PromptInput<ComboBox<T>> linesWrapper;
    
    private Node displayNode;
    
    public RtComboBoxSkin(final ComboBox<T> comboBox)
    {
        super(comboBox);
        this.comboBox = comboBox;
        
        inputContainer.setManaged(false);
        inputContainer.getStyleClass().add("input-container");
        
        overlayContainer.getStyleClass().add("overlay-container");
        overlayContainer.setOpacity(0);

        // TODO dynamically change this
        if (this.comboBox.isEditable())
        {
            linesWrapper = new PromptInput<>(comboBox, overlayContainer, comboBox.promptTextFillProperty(), comboBox.valueProperty(),
                    comboBox.promptTextProperty(), () -> promptText, this.comboBox.focusedProperty(), this.promptContainer);
        }
        else
        {
            linesWrapper = new PromptInput<>(comboBox, overlayContainer, comboBox.promptTextFillProperty(), comboBox.valueProperty(),
                    comboBox.promptTextProperty(), () -> promptText, this.comboBox.showingProperty(), this.promptContainer);
        }
        
        promptContainer.getStyleClass().add("prompt-container");
        promptContainer.setManaged(false);
        
        linesWrapper.init(() -> createPromptText());

        helperContainer.getStyleClass().add("helper-container");

        this.errorContainer = new ValidableContainer<T>(comboBox);
        this.textContainer.getChildren().addAll(helperContainer, errorContainer);
        this.helperContainer.visibleProperty().bind(comboBox.isValidProperty());
        this.errorContainer.visibleProperty().bind(comboBox.isValidProperty().not());

        this.arrowButton = (StackPane)this.comboBox.lookup(".arrow-button");
        this.listView = this.comboBox.lookup(".list-view");
        getChildren().remove(arrowButton);
        getChildren().addAll(inputContainer, overlayContainer, linesWrapper.unfocusedLine, linesWrapper.focusedLine, promptContainer, arrowButton, textContainer);

        updateDisplayArea();
        updateOverlayColor();
        createHelperText();
        updatePopupLocation();
        
        final Node newDisplayNode = getDisplayNode();
        getChildren().remove(newDisplayNode);
        getChildren().add(newDisplayNode);
        
        registerChangeListener(comboBox.labelFloatProperty(), comboBox.labelFloatProperty().getName());
        registerChangeListener(comboBox.focusColorProperty(),  comboBox.focusColorProperty().getName());
        registerChangeListener(comboBox.getOverlayColorProperty(),  comboBox.getOverlayColorProperty().getName());
        registerChangeListener(comboBox.unfocusProperty(),  comboBox.unfocusProperty().getName());
        registerChangeListener(comboBox.helperTextHeightProperty(), comboBox.helperTextHeightProperty().getName());
        registerChangeListener(comboBox.isShowHelperTextProperty(), comboBox.isShowHelperTextProperty().getName());
    }
    
    @Override 
    protected void updateDisplayArea() 
    {
        super.updateDisplayArea();
        if (inputContainer != null)
        {
            final Node newDisplayNode = getDisplayNode();
            if (displayNode != newDisplayNode && newDisplayNode != null)
            {
                if (displayNode != null)
                {
                    inputContainer.getChildren().remove(displayNode);
                }
                getChildren().remove(newDisplayNode);
                getChildren().add(newDisplayNode);
                inputContainer.getChildren().add(newDisplayNode);
                displayNode = newDisplayNode;
            }
        }
    }

    @Override
    protected void handleControlPropertyChanged(String propertyReference)
    {
        super.handleControlPropertyChanged(propertyReference);
        if (comboBox.focusColorProperty().getName().equals(propertyReference))
        {
            linesWrapper.updateFocusColor();
        }
        else if (comboBox.unfocusProperty().getName().equals(propertyReference))
        {
            linesWrapper.updateUnfocusColor();
        }
        else if (comboBox.getOverlayColorProperty().getName().equals(propertyReference))
        {
            updateOverlayColor();
        }
        else if (comboBox.labelFloatProperty().getName().equals(propertyReference))
        {
            this.linesWrapper.updateLabelFloatLayout();
        }
        else if (comboBox.helperTextHeightProperty().getName().equals(propertyReference))
        {
            updatePopupLocation();
        }
        else if (comboBox.isShowHelperTextProperty().getName().equals(propertyReference))
        {
            updatePopupLocation();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);

        double promptTopPadding = this.promptContainer.getPadding().getTop();
        double inputTopPadding = this.inputContainer.getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;

        double inputHeight = this.comboBox.isHelperTextVisible() ? h - this.comboBox.getHelperTextHeight() : h;
        this.linesWrapper.layoutComponents(x, y, w, inputHeight, translateY);
        this.linesWrapper.updateLabelFloatLayout();

        this.inputContainer.resizeRelocate(x, y, w, inputHeight);
        this.promptContainer.resizeRelocate(x, y, w - this.arrowButton.getLayoutBounds().getWidth(), inputHeight);
        this.overlayContainer.resizeRelocate(x, y, w, inputHeight);

        this.textContainer.resizeRelocate(x, inputHeight, w, this.comboBox.getHelperTextHeight());
        
        if (arrowButton != null)
        {
            double graphicWidth = arrowButton.getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth;
            double inputYCenter = y + inputHeight / 2;
            positionInArea(arrowButton, xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
        }
        
        if (displayNode != null) 
        {
            final double inputX = this.inputContainer.snappedLeftInset();
            final double inputY = this.inputContainer.snappedTopInset();
            final double inputW = snapSize(this.inputContainer.getWidth()) - inputX - this.inputContainer.snappedRightInset();
            final double inputH = snapSize(this.inputContainer.getHeight()) - inputY - this.inputContainer.snappedBottomInset();
            if (this.comboBox.isEditable())
            {
                // TODO find a better solution to aligning editable textfields with the floating prompt. 
                displayNode.resizeRelocate(inputX, y, inputW, inputHeight);
                ((javafx.scene.control.TextField)displayNode).setPadding(new Insets(inputY - 1, 0, this.inputContainer.snappedBottomInset() - 1, 0));
            }
            else
            {
                displayNode.resizeRelocate(inputX, inputY, inputW, inputH);
            }
        }
    }
    
    private void createPromptText()
    {
        if (this.promptText != null || !this.linesWrapper.usePromptText.get())
        {
            return;
        }
        // TODO bind font
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(linesWrapper.usePromptText);
        this.promptText.textProperty().bind(this.comboBox.promptTextProperty());
        this.promptText.fillProperty().bind(this.linesWrapper.animatedPromptTextFill);
        this.promptText.getTransforms().add(this.linesWrapper.promptTextScale);
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.promptContainer.getChildren().add(this.promptText);
        
        if (this.comboBox.isFocused() && this.comboBox.isLabelFloat())
        {
            this.linesWrapper.promptTextScale.setX(0.85);
            this.linesWrapper.promptTextScale.setY(0.85);
        }
    }
    
    private void createHelperText()
    {
        if (this.helperText != null || !this.comboBox.getIsShowHelperText())
        {
            return;
        }
        this.helperText = new Text();
        this.helperText.getStyleClass().add("helper-text");
        this.helperText.visibleProperty().bind(this.comboBox.isShowHelperTextProperty());
        this.helperText.textProperty().bind(this.comboBox.helperTextProperty());
        StackPane.setAlignment(this.helperText, Pos.CENTER_LEFT);
        this.helperContainer.getChildren().add(this.helperText);
    }
    
    private void updateOverlayColor()
    {
        CornerRadii radii = this.comboBox.getBackground() == null ? null : this.comboBox.getBackground().getFills().get(0).getRadii(); 
        this.overlayContainer.setBackground(new Background(new BackgroundFill(this.comboBox.getOverlayColor(), radii, Insets.EMPTY)));
    }
    
    private void updatePopupLocation()
    {
        if (this.comboBox.isHelperTextVisible())
        {
            listView.setTranslateY(-1 * this.comboBox.getHelperTextHeight());
        }
    }
}
