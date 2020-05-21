package mil.af.eglin.ccf.rt.fx.control.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Validation for checking required substrings
 */
public class ContainsTextValidator implements Validator<String>
{
    private List<String> requiredStrings = new ArrayList<>();
    private String message = "";

    public ContainsTextValidator(List<String> requiredStrings)
    {
        this.requiredStrings = requiredStrings;
    }
    
    public List<String> getRequiredStrings()
    {
        return this.requiredStrings;
    }
    
    public void addRequiredString(String required)
    {
        this.requiredStrings.add(required);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String value)
    {
        boolean isValid = true;
        
        StringJoiner missingFields = new StringJoiner(", ");
        for (String required : requiredStrings)
        {
            if (!value.contains(required))
            {
                missingFields.add(required);
                isValid = false;
            }
        }
        if (!isValid)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Missing ");
            builder.append(missingFields);
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
