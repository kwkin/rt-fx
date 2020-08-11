package mil.af.eglin.ccf.rt.fx.control;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import mil.af.eglin.ccf.rt.fx.validation.ValidateCondition;

/**
 * A {@code ValidableControl} allows the value of a component to be validated
 * against one or more {@link Validator validators}.
 * <p>
 * A {@code ValidableControl} can be manually or automatlically validated based
 * upon the specified {@link ValidateCondition validation condition}. When a
 * component value is invalid, the error message is expected to be updated to
 * reflect the reason why the value is invalid.
 * 
 * @param <T> the input value type to validate
 */
public interface ValidableControl<T>
{
    /**
     * Gets the javafx control reference.
     * 
     * @return the javafx control reference
     */
    public Control getValidableControl();

    /**
     * Gets the read only property indicating if the control is valid
     * 
     * @return the property indicating if the control is valid
     */
    public ReadOnlyBooleanProperty isValidProperty();

    /**
     * Determines if the current component value is valid
     * 
     * @return true if the current component value is valid
     */
    public boolean validate();

    /**
     * Gets the value indicating if the control's value is valid
     * 
     * @return True if the value of the control is valid
     */
    public boolean isValid();

    /**
     * Gets the message property indicating why the value is invalid
     * 
     * @return the message property indicating why the value is invalid
     */
    public ReadOnlyStringProperty errorMessageProperty();

    /**
     * Gets the message indicating why the value is invalid.
     * 
     * @return the message indicating why the value is invalid
     */
    public String getErrorMessage();

    /**
     * Sets the validate condition used to trigger when the component is
     * validated
     * 
     * @param validateCondition the validate condition used to trigger when the
     *            component is validated
     */
    public void setValidateCondition(ValidateCondition validateCondition);

    /**
     * Gets the validate condition used to trigger when the component is
     * validated
     * 
     * @return the validate condition used to trigger when the component is
     *         validated
     */
    public ValidateCondition getValidateCondition();

    /**
     * Gets the observable value for the validable control
     * 
     * @return the observable value for the validable control
     */
    public ObservableValue<T> getValidableValue();

    void setErrorMessage(String errorMessage);

    void setValid(boolean isValid);
}
