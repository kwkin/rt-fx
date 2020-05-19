package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Validation for checking forbidden substrings
 */
public class ForbiddenTextValidator implements Validator<String>
{
    private List<String> forbiddenStrings = new ArrayList<>();
    private String message = "";

    public ForbiddenTextValidator(List<String> forbiddenStrings)
    {
        this.forbiddenStrings = forbiddenStrings;
    }
    
    public List<String> getForbiddenString()
    {
        return this.forbiddenStrings;
    }
    
    public void addForbiddenString(String forbidden)
    {
        this.forbiddenStrings.add(forbidden);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String value)
    {
        boolean isValid = true;
        
        StringJoiner forbiddenFields = new StringJoiner(", ");
        if (isValid)
        {
            for (String forbidden : forbiddenStrings)
            {
                if (value.contains(forbidden))
                {
                    forbiddenFields.add(forbidden);
                    isValid = false;
                }
            }
        }
        if (!isValid)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Cannot contain ");
            builder.append(forbiddenFields);
        }
        else
        {
            this.message = "";
        }
        return isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage()
    {
        return message;
    }
}
