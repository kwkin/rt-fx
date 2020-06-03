package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.StringJoiner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;

public class ValidableHandler<T>
{
    private static final PseudoClass ERROR_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("error");
    
    private ValidableControl<T> control;
    private ValidateCondition validateCondition;
    private ObservableList<Validator<T>> validators = FXCollections.observableArrayList();
    
    private ChangeListener<T> valueListener;
    private ChangeListener<Boolean> unfocusListener;
    
    public ValidableHandler(ValidableControl<T> control)
    {
        this.control = control;
        this.valueListener = new ValidationListener();
        this.unfocusListener = new UnfocusListener();
    }
    
    public ObservableList<Validator<T>> getValidators()
    {
        return this.validators;
    }
    
    public void setValidateCondition(ValidateCondition validateCondition)
    {
        if (this.validateCondition != validateCondition)
        {
            this.validateCondition = validateCondition;
            switch(validateCondition)
            {
                case CHANGED:
                    this.control.getObservable().addListener(valueListener);
                    this.control.getControl().focusedProperty().removeListener(unfocusListener);
                    break;
                case UNFOCUS:
                    this.control.getControl().focusedProperty().addListener(unfocusListener);
                    this.control.getObservable().removeListener(valueListener);
                    break;
                case MANUAL:
                default:
                    this.control.getObservable().removeListener(valueListener);
                    this.control.getControl().focusedProperty().removeListener(unfocusListener);
                    break;
                
            }
        }
    }
    
    public ValidateCondition getValidateCondition()
    {
        return this.validateCondition; 
    }
    
    public boolean validate(T value)
    {
        boolean isValid = true;
        StringJoiner errorMessage = new StringJoiner(". ");
        for (Validator<T> validator : validators)
        {
            if (validator.validate(value))
            {
                errorMessage.add(validator.getErrorMessage());
                if (isValid)
                {
                    isValid = false; 
                }
            }
        }
        control.getControl().pseudoClassStateChanged(ERROR_PSEUDOCLASS_STATE, !isValid);
        control.setErrorMessage(errorMessage.toString());
        return isValid;
    }
    
    public void resetValidation()
    {
        control.getControl().pseudoClassStateChanged(ERROR_PSEUDOCLASS_STATE, false);
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
                control.setValid(validate(control.getObservable().getValue()));
            }
        }
    }
}
