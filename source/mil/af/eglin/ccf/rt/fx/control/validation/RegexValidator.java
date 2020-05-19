package mil.af.eglin.ccf.rt.fx.control.validation;

public class RegexValidator implements Validator<String>
{
    private String regex;
    private String message = "";

    public RegexValidator(String regex)
    {
        this.regex = regex;
    }
    
    public RegexValidator(String regex, String errorMessage)
    {
        this.message = errorMessage;
        this.regex = regex;
    }
    
    public String getRegex()
    {
        return this.regex;
    }

    @Override
    public boolean validate(String value)
    {
        return value.matches(regex);
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
