package mil.af.eglin.ccf.rt.fx.validation;

import java.util.function.Function;

/**
 * A {@code FunctionValidator} checks a value using the provided function.
 * <p>
 * The provided function must accept a parameter of type {@code T} and must
 * return a boolean.
 *
 * @param <T> The type of value to validate.
 */
public class FunctionValidator<T> implements Validator<T>
{
    private Function<T, Boolean> function;
    private String message = "";

    /**
     * Creates a {@code FunctionValidator} for checking a value using the
     * provided function.
     * 
     * @param function a function that accepts a parameter of type {@code T} and
     *            must return a boolean.
     */
    public FunctionValidator(Function<T, Boolean> function)
    {
        this.function = function;
    }

    /**
     * Creates a {@code FunctionValidator} for checking a value using the
     * provided function.
     * 
     * @param function a function that accepts a parameter of type {@code T} and
     *            must return a boolean.
     * @param errorMessage the message to display when the function returns
     *            false.
     */
    public FunctionValidator(Function<T, Boolean> function, String errorMessage)
    {
        this.function = function;
        this.message = errorMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(T value)
    {
        return !this.function.apply(value);
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
    public void setErrorMessage(String message)
    {
        this.message = message;
    }
}
