package mil.af.eglin.ccf.rt.fx.control.validation;

public class RequiredFieldValidator implements  Validator<String>
{
    private String message = "";
    
    public RequiredFieldValidator(String message)
    {
        this.message = message;
    }

    public boolean validate(String value)
    {
        boolean isValid = value != null && "".equals(value);
        return isValid;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
