package mil.af.eglin.ccf.rt.fx.control.validation;

// TODO add text limit validator
// TODO add number validator
// TODO add regex validator
public interface Validator<T>
{
    public boolean validate(T value);
    
    public String getMessage();
    
    public void setMessage(String message);
}
