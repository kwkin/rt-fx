package mil.af.eglin.ccf.rt.fx.validation;

/**
 * A {@code MaxCharLimitValidator} is used to ensure a string does not exceed
 * the provided character limit.
 */
public class MaxCharLimitValidator implements Validator<String>
{
    private int max;
    private String message = "";

    /**
     * Creates a {@code MaxCharLimitValidator} used to prevent a string from
     * exceeding a provided limit.
     * 
     * @param max the max number of characters allowed for a valid input
     */
    public MaxCharLimitValidator(int max)
    {
        this.max = max;
    }

    /**
     * Creates a {@code MaxCharLimitValidator} used to prevent a string from
     * exceeding a provided limit.
     * 
     * @param max the max number of characters allowed for a valid input
     * @param errorMessage the message to display for invalid matches
     */
    public MaxCharLimitValidator(int max, String errorMessage)
    {
        this.max = max;
        this.message = errorMessage;
    }

    /**
     * Gets the max number of characters allowed for a valid input
     * 
     * @return the max number of characters allowed for a valid input
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String value)
    {
        return value.length() <= max;
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
