package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.ArrayList;
import java.util.List;

public class ContainsValidator implements Validator<String>
{
    private List<String> requiredStrings = new ArrayList<>();
    private List<String> forbiddenStrings = new ArrayList<>();
    private String message = "";

    public ContainsValidator(String errorMessage)
    {
        this.message = errorMessage;
    }
    
    public List<String> getRequiredStrings()
    {
        return this.requiredStrings;
    }
    
    public void addRequiredString(String required)
    {
        this.requiredStrings.add(required);
    }
    
    public List<String> getForbiddenString()
    {
        return this.forbiddenStrings;
    }
    
    public void addForbiddenString(String forbidden)
    {
        this.forbiddenStrings.add(forbidden);
    }

    @Override
    public boolean validate(String value)
    {
        boolean isValid = true;
        for (String required : requiredStrings)
        {
            if (!value.contains(required) && isValid)
            {
                isValid = false;
                break;
            }
        }
        
        if (isValid)
        {
            for (String forbidden : forbiddenStrings)
            {
                if (value.contains(forbidden))
                {
                    isValid = false;
                    break;
                }
            }
        }
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
