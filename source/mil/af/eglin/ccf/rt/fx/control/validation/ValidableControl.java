package mil.af.eglin.ccf.rt.fx.control.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.control.Control;

public interface ValidableControl
{
    public Control getControl();
    
    public boolean validate();
    
    public ReadOnlyBooleanProperty isValidProperty();
    
    public boolean isValid();
    
    public ReadOnlyStringProperty errorMessageProperty();

    public void setErrorMessage(String errorMessage);
    
    public String getErrorMessage();
}
