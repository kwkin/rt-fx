package mil.af.eglin.ccf.rt.fx.control.validation;

public class RequiredValueValidator<T> implements Validator<T>
{
    private String message = "";
    
    public RequiredValueValidator()
    {
    }
    
    public RequiredValueValidator(String errorMessage)
    {
        this.message = errorMessage;
    }

    public boolean validate(T value)
    {
        boolean isValid = value != null;
        return isValid;
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
