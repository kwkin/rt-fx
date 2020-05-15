package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.StringJoiner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;

public class ValidableHandler<T>
{
    private static final PseudoClass ERROR_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("error");
    
    private ValidableControl control;
    private ValidateCondition validateCondition;
    private ObservableList<Validator<T>> validators = FXCollections.observableArrayList();
    
    public ValidableHandler(ValidableControl control)
    {
        this.control = control;
    }
    
    public ObservableList<Validator<T>> getValidators()
    {
        return this.validators;
    }
    
    public void setValidateCondition(ValidateCondition validateCondition)
    {
        // TODO add other condition types
        this.validateCondition = validateCondition;
        switch(validateCondition)
        {
            case CHANGED:
                break;
            case UNFOCUS:
//                this.control.focusedProperty().addListener((ov, oldVal, newVal) -> 
//                {
//                    if (!newVal)
//                    {
//                        
//                    }
//                });
                break;
            case MANUAL:
            default:
                break;
            
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
                errorMessage.add(validator.getMessage());
                if (isValid)
                {
                    isValid = false;
                }
            }
        }
        control.getControl().pseudoClassStateChanged(ERROR_PSEUDOCLASS_STATE, isValid);
        control.setErrorMessage(errorMessage.toString());
        return isValid;
    }
    
    public void resetValidation()
    {
        control.getControl().pseudoClassStateChanged(ERROR_PSEUDOCLASS_STATE, false);
    }
}
