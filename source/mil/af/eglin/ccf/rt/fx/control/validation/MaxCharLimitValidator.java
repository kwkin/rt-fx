package mil.af.eglin.ccf.rt.fx.control.validation;

public class MaxCharLimitValidator implements Validator<String>
{
    private int max;
    private String message = "";
    
    public MaxCharLimitValidator(int max)
    {
        this.max = max;
    }
    
    public MaxCharLimitValidator(int max, String errorMessage)
    {
        this.max = max;
        this.message = errorMessage;
    }

    public int getMax()
    {
        return this.max;
    }
    
    public boolean validate(String value)
    {
        return value.length() > max;
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
