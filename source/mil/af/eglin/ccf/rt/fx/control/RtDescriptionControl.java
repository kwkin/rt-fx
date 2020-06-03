package mil.af.eglin.ccf.rt.fx.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface RtDescriptionControl
{

    public StringProperty helperTextProperty();

    public String getHelperText();

    public void setHelperText(String helperText);

    public BooleanProperty isShowHelperTextProperty();

    public boolean getIsShowHelperText();

    public void setIsShowHelperText(boolean isShowHelperText);
}
