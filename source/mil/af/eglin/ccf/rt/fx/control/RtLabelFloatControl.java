package mil.af.eglin.ccf.rt.fx.control;

import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.scene.paint.Paint;

public interface RtLabelFloatControl
{
    public StyleableBooleanProperty labelFloatProperty();

    public boolean isLabelFloat();

    public void setLabelFloat(final boolean labelFloat);
    
    public StyleableObjectProperty<Paint> focusColorProperty();
    
    public Paint getFocusColor();

    public void setFocusColor(Paint color);

    public StyleableObjectProperty<Paint> unfocusColorProperty();

    public Paint getUnfocusColor();

    public void setUnfocusColor(Paint color);

    public StyleableBooleanProperty disableAnimationProperty();

    public Boolean isDisableAnimation();

    public void setDisableAnimation(Boolean disabled);
}
