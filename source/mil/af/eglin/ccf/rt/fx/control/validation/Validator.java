package mil.af.eglin.ccf.rt.fx.control.validation;

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
