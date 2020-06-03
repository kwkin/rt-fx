package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.validation.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

public class RtComboBoxSkin<T> extends ComboBoxListViewSkin<T>
{
    private final ComboBox<T> comboBox;

    private final Node arrowButton;
    private final Node listView;
    private final PromptInput<ComboBox<T>> input;
    private final DescriptionContainer<ComboBox<T>> descriptionContainer;

    private Text promptText;

    public RtComboBoxSkin(final ComboBox<T> comboBox)
    {
        super(comboBox);
        this.comboBox = comboBox;

        ObservableValue<Boolean> toggleFlag = this.comboBox.isEditable() ? this.comboBox.focusedProperty()
                : this.comboBox.showingProperty();
        input = new PromptInput<>(comboBox, comboBox.valueProperty(), comboBox.promptTextFillProperty(),
                comboBox.promptTextProperty(), () -> promptText, toggleFlag);

        input.init(() -> createPromptText());

        this.descriptionContainer = new DescriptionContainer<ComboBox<T>>(comboBox);

        this.arrowButton = (StackPane) this.comboBox.lookup(".arrow-button");
        this.listView = this.comboBox.lookup(".list-view");
        getChildren().remove(arrowButton);
        getChildren().addAll(this.input.getInputContainer(), 
                this.input.getOverlayContainer(), 
                this.input.getUnfocusedLine(), 
                this.input.getFocusedLine(),
                this.input.getPromptContainer(),
                arrowButton,
                this.descriptionContainer);

        updateDisplayArea();
        input.updateOverlayColor(this.comboBox.getOverlayColor());
        updatePopupLocation();

        final Node newDisplayNode = getDisplayNode();
        getChildren().remove(newDisplayNode);
        getChildren().add(newDisplayNode);

        registerChangeListener(comboBox.labelFloatProperty(), comboBox.labelFloatProperty().getName());
        registerChangeListener(comboBox.focusColorProperty(), comboBox.focusColorProperty().getName());
        registerChangeListener(comboBox.getOverlayColorProperty(), comboBox.getOverlayColorProperty().getName());
        registerChangeListener(comboBox.unfocusColorProperty(), comboBox.unfocusColorProperty().getName());
        registerChangeListener(comboBox.helperTextHeightProperty(), comboBox.helperTextHeightProperty().getName());
        registerChangeListener(comboBox.isShowHelperTextProperty(), comboBox.isShowHelperTextProperty().getName());
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
    protected void handleControlPropertyChanged(String propertyReference)
    {
        super.handleControlPropertyChanged(propertyReference);
        if (comboBox.focusColorProperty().getName().equals(propertyReference))
        {
            input.updateFocusColor();
        }
        else if (comboBox.unfocusColorProperty().getName().equals(propertyReference))
        {
            input.updateUnfocusColor();
        }
        else if (comboBox.getOverlayColorProperty().getName().equals(propertyReference))
        {
            input.updateOverlayColor(this.comboBox.getOverlayColor());
        }
        else if (comboBox.labelFloatProperty().getName().equals(propertyReference))
        {
            this.input.updateLabelFloatLayout();
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

        double inputHeight = this.comboBox.isHelperTextVisible() ? h - this.comboBox.getHelperTextHeight() : h;
        double promptTopPadding = this.input.getPromptContainer().getPadding().getTop();
        double inputTopPadding = this.input.getInputContainer().getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;
        this.input.layoutComponents(x, y, w, inputHeight, translateY);
        this.input.updateLabelFloatLayout();

        this.descriptionContainer.resizeRelocate(x, inputHeight, w, this.comboBox.getHelperTextHeight());
        this.input.getPromptContainer().resizeRelocate(x, y, w - this.arrowButton.getLayoutBounds().getWidth(), inputHeight);

        if (arrowButton != null)
        {
            double graphicWidth = arrowButton.getLayoutBounds().getWidth();
            double xPosition = w - graphicWidth;
            double inputYCenter = y + inputHeight / 2;
            positionInArea(arrowButton, xPosition, inputYCenter, graphicWidth, 0, 0, HPos.CENTER, VPos.CENTER);
        }

        Node displayNode = this.input.getDisplayNode();
        if (displayNode != null)
        {
            Region inputContainer = this.input.getInputContainer();
            
            double inputX = inputContainer.snappedLeftInset();
            double inputY = inputContainer.snappedTopInset();
            double inputW = snapSize(inputContainer.getWidth()) - inputX - inputContainer.snappedRightInset();
            double inputH = snapSize(inputContainer.getHeight()) - inputY - inputContainer.snappedBottomInset();
            if (this.comboBox.isEditable())
            {
                // TODO find a better solution to aligning editable textfields with the floating prompt. 
                displayNode.resizeRelocate(inputX, y, inputW, inputHeight);
                ((javafx.scene.control.TextField)displayNode).setPadding(new Insets(inputY - 1, 0, inputContainer.snappedBottomInset() - 1, 0));
            }
            else
            {
                displayNode.resizeRelocate(inputX, inputY, inputW, inputH);
            }
            
        }
    }

    private void createPromptText()
    {
        if (this.promptText != null || !this.input.isUsingPromptTextProperty().get())
        {
            return;
        }
        // TODO bind font
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(input.isUsingPromptTextProperty());
        this.promptText.textProperty().bind(this.comboBox.promptTextProperty());
        this.promptText.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        this.promptText.getTransforms().add(this.input.getPromptTextScale());
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.input.addPromptText(this.promptText);
    }

    private void updatePopupLocation()
    {
        if (this.comboBox.isHelperTextVisible())
        {
            listView.setTranslateY(-1 * this.comboBox.getHelperTextHeight());
        }
    }
}
