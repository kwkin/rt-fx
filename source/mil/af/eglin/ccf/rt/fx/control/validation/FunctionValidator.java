package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.function.Function;

/**
 *
 * @param <T> The type of value to validate.
 */
public class FunctionValidator<T> implements Validator<T>
{
    private String message = "";
    private Function<T, Boolean> function;
    
    public FunctionValidator(Function<T, Boolean> function)
    {
        this.function = function;
    }
    
    public FunctionValidator(Function<T, Boolean> function, String errorMessage)
    {
        this.function = function;
        this.message = errorMessage;
    }

    public boolean validate(T value)
    {
        return !this.function.apply(value);
    }
    
    public String getErrorMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
