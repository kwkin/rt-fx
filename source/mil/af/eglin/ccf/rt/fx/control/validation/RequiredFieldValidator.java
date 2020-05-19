package mil.af.eglin.ccf.rt.fx.control.validation;

public class RequiredFieldValidator implements Validator<String>
{
    private String message = "";
    
    public RequiredFieldValidator()
    {
    }
    
    public RequiredFieldValidator(String errorMessage)
    {
        this.message = errorMessage;
    }

    public boolean validate(String value)
    {
        boolean isValid = value != null && "".equals(value);
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
