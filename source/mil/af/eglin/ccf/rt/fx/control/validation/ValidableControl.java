package mil.af.eglin.ccf.rt.fx.control.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

public interface ValidableControl<T>
{
    public Control getControl();
    
    public boolean validate();
    
    public ReadOnlyBooleanProperty isValidProperty();
    
    public boolean isValid();
    
    public ReadOnlyStringProperty errorMessageProperty();

    public void setErrorMessage(String errorMessage);
    
    public String getErrorMessage();
    
    public ObservableValue<T> getObservable();
    
    boolean setValid(boolean isValid);
}
