package mil.af.eglin.ccf.rt.fx.control;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtDescriptionContainerSkin;

/**
 * A helper component used to toggle between description and error text defined
 * by another component.
 * <p>
 * The CSS style names for the description and error text are different to allow
 * the text to be styled independently. The description and error text is
 * styleable through the CSS using {@code helper-text} and {@code error-text}.
 *
 * @param <T> A validable component with a description.
 */
public class DescriptionContainer<T extends ValidableControl<?> & DescriptionControl> extends Labeled
{
    private static final String CSS_CLASS = "description-container";

    private T control;

    /**
     * Creates a description container that uses the description and error text
     * from the provided control.
     * 
     * @param control the control defining the description and error text
     */
    public DescriptionContainer(T control)
    {
        this.control = control;
        initialize();
    }

    /**
     * Gets the property used to determine if the connected component is valid.
     * 
     * @return the property used to determine if the connected component is valid.
     */
    public ReadOnlyBooleanProperty isValidProperty()
    {
        return this.control.isValidProperty();
    }

    /**
     * Gets the property used if the connected component is displaying helper text.
     * 
     * @return the property used if the connected component is displaying helper text.
     */
    public ReadOnlyBooleanProperty isShowHelperTextProperty()
    {
        return this.control.helperTextVisibleProperty();
    }

    /**
     * Gets the string property used for displaying the error message.
     * 
     * @return the string property used for displaying the error message.
     */
    public ReadOnlyStringProperty errorMessageProperty()
    {
        return this.control.errorMessageProperty();
    }

    /**
     * Gets the string property used for displaying the helper text.
     * 
     * @return the string property used for displaying the helper text.
     */
    public ReadOnlyStringProperty helperTextProperty()
    {
        return this.control.helperTextProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtDescriptionContainerSkin<>(this);
    }

    private void initialize()
    {
        textProperty().bind(control.helperTextProperty());
        getStyleClass().add(CSS_CLASS);
    }
}
