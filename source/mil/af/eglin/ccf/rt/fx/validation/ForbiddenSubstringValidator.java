package mil.af.eglin.ccf.rt.fx.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * A {@code ForbiddenSubstringValidator} checks a string for one or more invalid substrings.
 */
public class ForbiddenSubstringValidator implements Validator<String>
{
    private List<String> forbiddenStrings = new ArrayList<>();
    private String message = "";

    /**
     * Creates a {@code ForbiddenSubstringValidator} for checking a string for one or more invalid substrings
     * 
     * @param forbidden The invalid substrings
     */
    public ForbiddenSubstringValidator(String... forbidden)
    {
        this.forbiddenStrings = Arrays.asList(forbidden);
    }

    /**
     * Creates a {@code ForbiddenSubstringValidator} for checking a string for one or more invalid substrings
     * 
     * @param forbidden The invalid substrings
     */
    public ForbiddenSubstringValidator(List<String> forbidden)
    {
        this.forbiddenStrings = forbidden;
    }

    /**
     * Gets the list of forbidden substrings
     * 
     * @return the list of forbidden substrings
     */
    public List<String> getForbiddenString()
    {
        return this.forbiddenStrings;
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
