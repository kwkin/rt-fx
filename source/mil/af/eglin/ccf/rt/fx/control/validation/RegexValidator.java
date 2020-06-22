package mil.af.eglin.ccf.rt.fx.control.validation;

/**
 * A {@code RegexValidator} requires a string to match a provided regex.
 * <p>
 * A matching string is considered valid.
 *
 * @param <T> The type of value to validate.
 */
public class RegexValidator implements Validator<String>
{
    private String regex;
    private String message = "";

    /**
     * Creates a {@code Validator} for checking if a given string matches the
     * provided regex.
     * 
     * @param regex the regex to match for a valid string
     */
    public RegexValidator(String regex)
    {
        this.regex = regex;
    }

    /**
     * Creates a {@code Validator} for checking if a given string matches the
     * provided regex.
     * 
     * @param regex the regex to match for a valid string
     * @param errorMessage the message to display for invalid matches
     */
    public RegexValidator(String regex, String errorMessage)
    {
        this.message = errorMessage;
        this.regex = regex;
    }

    /**
     * Gets the regex to match for a valid string
     * 
     * @return the regex to match for a valid string
     */
    public String getRegex()
    {
        return this.regex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String value)
    {
        return value.matches(regex);
    }

    /**
     * {@inheritDoc}
     */
    public String getErrorMessage()
    {
        return message;
    }

    /**
     * Sets the message used when an invalid input is evaluated
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
