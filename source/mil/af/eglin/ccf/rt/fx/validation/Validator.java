package mil.af.eglin.ccf.rt.fx.validation;

/**
 * A {@code Validator} checks if provided values pass a predefined condition.
 * 
 * @param <T> the input value type to validate
 */
public interface Validator<T>
{
    /**
     * Checks if the provided value passes the validation condition
     * 
     * @param value The value to validate
     * @return True if the value is valid
     */
    public boolean validate(T value);

    /**
     * Gets the message detailing the last validation check
     * 
     * @return The error message
     */
    public String getErrorMessage();
}
