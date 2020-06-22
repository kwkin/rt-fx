package mil.af.eglin.ccf.rt.fx.validation;

/**
 * A {@code RequiredValueValidator} ensures that a value is not null.
 * <p>
 * A null or empty string is invalid.
 */
public class RequiredValueValidator<T> implements Validator<T>
{
    private String message = "";

    /**
     * Creates a {@code RequiredFieldValidator} used to ensure that a value is
     * not null
     */
    public RequiredValueValidator()
    {
    }

    /**
     * Creates a {@code RequiredFieldValidator} used to ensure that a value is
     * not null
     */
    public RequiredValueValidator(String errorMessage)
    {
        this.message = errorMessage;
    }

    /**
     * {@inheritDoc}
     */
    public boolean validate(T value)
    {
        return value != null;
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
