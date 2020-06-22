package mil.af.eglin.ccf.rt.fx.control.validation;

/**
 * A {@code MaxCharLimitValidator} is used to ensure a string exceeds the
 * provided character limit.
 */
public class MinCharLimitValidator implements Validator<String>
{
    private int min;
    private String message = "";

    /**
     * Creates a {@code MaxCharLimitValidator} used to ensure a string exceeds
     * the provided character limit.
     * 
     * @param min the min number of characters allowed for a valid input
     */
    public MinCharLimitValidator(int min)
    {
        this.min = min;
    }

    /**
     * Creates a {@code MaxCharLimitValidator} used to ensure a string exceeds
     * the provided character limit.
     * 
     * @param min the min number of characters allowed for a valid input
     * @param errorMessage the message to display for invalid matches
     */
    public MinCharLimitValidator(int min, String errorMessage)
    {
        this.min = min;
        this.message = errorMessage;
    }

    /**
     * Gets the min number of characters allowed for a valid input
     * 
     * @return the min number of characters allowed for a valid input
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(String value)
    {
        return value.length() >= min;
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
