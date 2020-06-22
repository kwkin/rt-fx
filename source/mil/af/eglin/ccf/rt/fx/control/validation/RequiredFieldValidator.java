package mil.af.eglin.ccf.rt.fx.control.validation;

/**
 * A {@code RequiredFieldValidator} ensures that a string is not null or empty
 * <p>
 * A null or empty string is invalid.
 */
public class RequiredFieldValidator implements Validator<String>
{
    private String message = "";

    /**
     * Creates a {@code RequiredFieldValidator} used to ensure that a string is
     * not null or empty
     */
    public RequiredFieldValidator()
    {
    }

    /**
     * Creates a {@code RequiredFieldValidator} used to ensure that a string is
     * not null or empty
     * 
     * @param errorMessage the message to display for invalid matches
     */
    public RequiredFieldValidator(String errorMessage)
    {
        this.message = errorMessage;
    }

    /**
     * {@inheritDoc}
     */
    public boolean validate(String value)
    {
        boolean isValid = value != null && !"".equals(value);
        return isValid;
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
