package mil.af.eglin.ccf.rt.fx.control;

import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.scene.paint.Paint;

/**
 * A component with an optional floating label
 * <p>
 * In general, a floating labels will persist in an input field when a value is
 * entered. For example, a text field without a floating label, but with prompt
 * text will remove the prompt text after an input is entered. However, if the text field
 * used a floating label, the prompt text will hover above the input text and remain visible.  
 */
public interface LabelFloatControl
{
    /**
     * Returns the label float property indicating if the label should float
     * 
     * @return The label float property indicating if the label should float
     */
    public StyleableBooleanProperty labelFloatProperty();

    /**
     * Returns Whether or not the label is floating.
     * 
     * @return True if the label is floating
     */
    public boolean isLabelFloat();

    /**
     * Sets the label float property
     * 
     * @param labelFloat The value to set the label float property
     */
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
