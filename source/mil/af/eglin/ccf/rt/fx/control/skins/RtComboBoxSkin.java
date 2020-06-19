package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.ComboBox;
import mil.af.eglin.ccf.rt.fx.control.validation.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.style.PromptInput;

public class RtComboBoxSkin<T> extends ComboBoxListViewSkin<T>
{
    private final ComboBox<T> comboBox;

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
        this.input = new PromptInput<>(comboBox, comboBox.valueProperty(), comboBox.unfocusColorProperty(),
                comboBox.promptTextProperty(), () -> promptText, toggleFlag);
        this.input.init(() -> createPromptText());
        this.input.updateOverlayColor(this.comboBox.getOverlayColor());
        this.descriptionContainer = new DescriptionContainer<ComboBox<T>>(comboBox);

        this.listView = this.comboBox.lookup(".list-view");
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

        final Node newDisplayNode = getDisplayNode();
        getChildren().remove(newDisplayNode);
        getChildren().add(newDisplayNode);

        registerChangeListener(comboBox.labelFloatProperty(), comboBox.labelFloatProperty().getName());
        registerChangeListener(comboBox.focusColorProperty(), comboBox.focusColorProperty().getName());
        registerChangeListener(comboBox.overlayColorProperty(), comboBox.overlayColorProperty().getName());
        registerChangeListener(comboBox.unfocusColorProperty(), comboBox.unfocusColorProperty().getName());
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
        if (this.comboBox.focusColorProperty().getName().equals(propertyReference))
        {
            this.input.updateFocusColor();
        }
        else if (this.comboBox.unfocusColorProperty().getName().equals(propertyReference))
        {
            this.input.updateUnfocusColor();
        }
        else if (this.comboBox.overlayColorProperty().getName().equals(propertyReference))
        {
            this.input.updateOverlayColor(this.comboBox.getOverlayColor());
        }
        else if (this.comboBox.labelFloatProperty().getName().equals(propertyReference))
        {
            this.input.updateLabelFloatLayout();
        }
        else if (this.comboBox.isShowHelperTextProperty().getName().equals(propertyReference))
        {
            updatePopupLocation();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {

        super.layoutChildren(x, y, w, h);
        if (this.comboBox.isHelperTextVisible())
        {
            layoutInArea(this.descriptionContainer, x, y, w, h, -1, HPos.CENTER, VPos.CENTER);
        }
        double inputHeight = this.comboBox.isHelperTextVisible() ? h - this.descriptionContainer.getHeight() : h;
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
        if (displayNode != null)
        {
            Region inputContainer = this.input.getInputContainer();

            double inputX = inputContainer.snappedLeftInset();
            double inputY = inputContainer.snappedTopInset();
            double inputW = snapSize(inputContainer.getWidth()) - inputX - inputContainer.snappedRightInset();
            double inputH = snapSize(inputContainer.getHeight()) - inputY - inputContainer.snappedBottomInset();
            if (this.comboBox.isEditable())
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

    private void createPromptText()
    {
        if (this.promptText != null || !this.input.isUsingPromptTextProperty().get())
        {
            return;
        }
        this.promptText = new Text();
        this.promptText.getStyleClass().add("prompt-text");
        this.promptText.visibleProperty().bind(this.input.isUsingPromptTextProperty());
        this.promptText.fontProperty().bind(getDisplayNodeFont());
        this.promptText.textProperty().bind(this.comboBox.promptTextProperty());
        
        this.comboBox.editableProperty().addListener((ov, oldVal, newVal) -> 
        {
            updatePromptTranslation();
        });
        updatePromptTranslation();
        
        this.promptText.fillProperty().bind(this.input.animatedPromptTextFillProperty());
        this.promptText.getTransforms().add(this.input.getPromptTextScale());
        StackPane.setAlignment(promptText, Pos.CENTER_LEFT);
        this.input.addPromptText(this.promptText);
    }

    private ObjectProperty<Font> getDisplayNodeFont()
    {
        ObjectProperty<Font> fontProperty;
        if (comboBox.isEditable())
        {
            TextField textfield = (TextField)getDisplayNode();
            fontProperty = textfield.fontProperty();
        }
        else
        {
            ListCell<?> displayNode = (ListCell<?>)getDisplayNode();
            fontProperty = displayNode.fontProperty();
        }
        return fontProperty;
    }
    
    private void updatePromptTranslation()
    {
        this.promptText.setTranslateX(this.comboBox.isEditable() ? 1 : 0);
    }

    private void updatePopupLocation()
    {
        if (this.comboBox.isHelperTextVisible() && !this.listView.translateYProperty().isBound())
        {
            this.listView.translateYProperty().bind(this.descriptionContainer.heightProperty().multiply(-1));
        }
    }
}
