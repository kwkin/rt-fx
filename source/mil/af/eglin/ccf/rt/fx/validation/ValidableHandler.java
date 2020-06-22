package mil.af.eglin.ccf.rt.fx.validation;

import java.util.StringJoiner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import mil.af.eglin.ccf.rt.fx.control.ValidableControl;

/**
 * A class for handling the validation and error pseudo state of a
 * {@link ValidableControl validable control}.
 * <p>
 * A {@code ValidableHandler} accepts one or more {@link Validator validators}
 * to check for valid input values. If the value is invalid according to one or
 * more validators, then the {@code error} pseudo class is applied and the error
 * text for the first invalid error in the validator list is used. Furthermore,
 * the validation handler enables the manual or automatic validation based upon
 * the specified {@link ValidateCondition validation condition}.
 * 
 * @param <T> the input value type to validate
 */
public class ValidableHandler<T>
{
    private static final PseudoClass ERROR_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("error");

    private ValidableControl<T> control;
    private ValidateCondition validateCondition;
    private ObservableList<Validator<T>> validators = FXCollections.observableArrayList();

    private ChangeListener<T> valueListener;
    private ChangeListener<Boolean> unfocusListener;

    /**
     * Creates a {@code ValidableHandler} for handling the validation and error
     * pseudo state of a validable control.
     * 
     * @param control the control to validate
     */
    public ValidableHandler(ValidableControl<T> control)
    {
        this.control = control;
    }

    /**
     * Creates a {@code ValidableHandler} for handling the validation and error
     * pseudo state of a validable control.
     * 
     * @param control the control to validate
     * @param condition the condition used to trigger a validation check
     */
    public ValidableHandler(ValidableControl<T> control, ValidateCondition condition)
    {
        this.control = control;
        setValidateCondition(condition);
    }

    /**
     * Gets the list of validators
     * 
     * @return the list of validators
     */
    public ObservableList<Validator<T>> getValidators()
    {
        return this.validators;
    }

    /**
     * Sets the condition required to trigger a validation check.
     * 
     * @param validateCondition the condition required to trigger a validation check
     */
    public void setValidateCondition(ValidateCondition validateCondition)
    {
        if (this.validateCondition != validateCondition)
        {
            this.validateCondition = validateCondition;
            switch (validateCondition)
            {
                case CHANGED:
                    if (this.valueListener == null)
                    {
                        this.valueListener = new ValidationListener();
                    }
                    this.control.getObservableValue().addListener(this.valueListener);
                    if (this.unfocusListener != null)
                    {
                        this.control.getControl().focusedProperty().removeListener(this.unfocusListener);
                    }
                    break;
                case UNFOCUS:
                    if (this.unfocusListener == null)
                    {
                        this.unfocusListener = new UnfocusListener();
                    }
                    this.control.getControl().focusedProperty().addListener(this.unfocusListener);
                    if (this.valueListener != null)
                    {
                        this.control.getObservableValue().removeListener(this.valueListener);
                    }
                    break;
                case MANUAL:
                default:
                    if (this.valueListener != null)
                    {
                        this.control.getObservableValue().removeListener(this.valueListener);
                    }
                    if (this.unfocusListener != null)
                    {
                        this.control.getControl().focusedProperty().removeListener(this.unfocusListener);
                    }
                    break;
            }
        }
    }

    /**
     * Gets the condition required to trigger a validation check.
     * 
     * @param validateCondition the condition required to trigger a validation check
     */
    public ValidateCondition getValidateCondition()
    {
        return this.validateCondition;
    }

    /**
     * Checks if the provided value passes the validation conditions
     * 
     * @param value The value to validate
     * @return True if the value is valid
     */
    public boolean validate(T value)
    {
        boolean isValid = true;
        StringJoiner errorMessage = new StringJoiner(". ");
        for (Validator<T> validator : validators)
        {
            if (!validator.validate(value))
            {
                errorMessage.add(validator.getErrorMessage());
                if (isValid)
                {
                    isValid = false;
                }
            }
        }
        this.control.getControl().pseudoClassStateChanged(ERROR_PSEUDOCLASS_STATE, !isValid);
        this.control.setErrorMessage(errorMessage.toString());
        return isValid;
    }

    private class ValidationListener implements ChangeListener<T>
    {
        @Override
        public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue)
        {
            control.setValid(validate(newValue));
        }
    }

    private class UnfocusListener implements ChangeListener<Boolean>
    {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
        {
            if (!newValue)
            {
                control.setValid(validate(control.getObservableValue().getValue()));
            }
        }
    }
}
