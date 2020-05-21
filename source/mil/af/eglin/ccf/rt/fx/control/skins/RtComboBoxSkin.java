package mil.af.eglin.ccf.rt.fx.control.skins;


import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private final StackPane overlayContainer = new StackPane();
    private final StackPane inputContainer = new StackPane();
    private final StackPane promptContainer = new StackPane();

    private final StackPane textContainer = new StackPane();
    private final StackPane helperContainer = new StackPane();
    private final ValidableContainer errorContainer;

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

        linesWrapper = new PromptInput<>(comboBox, overlayContainer, comboBox.promptTextFillProperty(), comboBox.valueProperty(),
                comboBox.promptTextProperty(), () -> promptText, this.comboBox.showingProperty());
        
        StackPane clip = new StackPane();
        clip.setBackground(inputContainer.getBackground());
        
        promptContainer.getStyleClass().add("prompt-container");
        linesWrapper.init(() -> createPromptText(), clip);

        helperContainer.getStyleClass().add("helper-container");

        
        this.errorContainer = new ValidableContainer(comboBox);
        this.textContainer.getChildren().addAll(helperContainer, errorContainer);
        this.helperContainer.visibleProperty().bind(comboBox.isValidProperty());
        this.errorContainer.visibleProperty().bind(comboBox.isValidProperty().not());

        StackPane arrows = (StackPane) this.getChildren().get(0);
        getChildren().remove(arrows);
        getChildren().addAll(inputContainer, overlayContainer, linesWrapper.unfocusedLine, linesWrapper.focusedLine, promptContainer, arrows, textContainer);
        
        updateDisplayArea();
        updateOverlayColor();
        createHelperText();
        
        registerChangeListener(comboBox.labelFloatProperty(), comboBox.labelFloatProperty().getName());
        registerChangeListener(comboBox.focusColorProperty(),  comboBox.focusColorProperty().getName());
        registerChangeListener(comboBox.getOverlayColorProperty(),  comboBox.getOverlayColorProperty().getName());
        registerChangeListener(comboBox.unfocusProperty(),  comboBox.unfocusProperty().getName());
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
        this.promptContainer.resizeRelocate(x, y, w, inputHeight);
        this.overlayContainer.resizeRelocate(x, y, w, inputHeight);

        this.textContainer.resizeRelocate(x, inputHeight, w, this.comboBox.getHelperTextHeight());
        
        // TODO attempt to remove this (currently needed because the display node CSS is not applying upon init)
        updateDisplayArea();
        if (displayNode != null) 
        {
            final double inputX = this.inputContainer.snappedLeftInset();
            final double inputY = this.inputContainer.snappedTopInset();
            final double inputW = snapSize(this.inputContainer.getWidth()) - inputX - this.inputContainer.snappedRightInset();
            final double inputH = snapSize(this.inputContainer.getHeight()) - inputY - this.inputContainer.snappedBottomInset();
            displayNode.resizeRelocate(inputX, inputY, inputW, inputH);
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
//            this.promptText.setTranslateY(-Math.floor(this.textPane.getHeight()));
            this.linesWrapper.promptTextScale.setX(0.85);
            this.linesWrapper.promptTextScale.setY(0.85);
        }
//        updatePromptText();
    }
    
//    private void updatePromptText()
//    {
//        try
//        {
//            Field field = TextFieldSkin.class.getDeclaredField("promptNode");
//            field.setAccessible(true);
//            Object oldValue = field.get(this);
//            if (oldValue != null)
//            {
//                textPane.getChildren().remove(oldValue);
//            }
//            field.set(this, promptText);
//        }
//        catch (Exception e)
//        {
//        }
//    }
    
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
}
