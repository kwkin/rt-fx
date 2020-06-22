package mil.af.eglin.ccf.rt.fx.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * A {@code RequiredSubstringValidator} checks a string for all of the required
 * substrings.
 */
public class RequiredSubstringValidator implements Validator<String>
{
    private List<String> requiredStrings = new ArrayList<>();
    private String message = "";

    /**
     * Creates a {@code RequiredSubstringValidator} for checking a string for
     * all of the required substrings.
     * 
     * @param required The substrings required for a valid input
     */
    public RequiredSubstringValidator(String... required)
    {
        this.requiredStrings = Arrays.asList(required);
    }

    /**
     * Creates a {@code RequiredSubstringValidator} for checking a string for
     * all of the required substrings.
     * 
     * @param required The substrings required for a valid input
     */
    public RequiredSubstringValidator(List<String> required)
    {
        this.requiredStrings = required;
    }

    /**
     * Gets the list of required substrings
     * 
     * @return the list of required substrings
     */
    public List<String> getRequiredStrings()
    {
        return this.requiredStrings;
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
            this.message = builder.toString();
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
