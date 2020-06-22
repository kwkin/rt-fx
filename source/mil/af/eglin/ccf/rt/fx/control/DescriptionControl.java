package mil.af.eglin.ccf.rt.fx.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * A component with an optional description
 * <p>
 * Descriptions are provided using an optional helper text. Helper text is an
 * optional field that may be used to convey additional information about the
 * input field. In general helper text should be contained in a single line.
 */
public interface DescriptionControl
{
    /**
     * Returns the helper text property
     * 
     * @return The helper text property.
     */
    public StringProperty helperTextProperty();

    /**
     * Returns the helper text
     * 
     * @return The helper text
     */
    public String getHelperText();

    /**
     * Sets the helper text
     * 
     * @param helperText
     *            The helper text
     */
    public void setHelperText(String helperText);

    /**
     * Returns the helper text visibility property
     * 
     * @return The helper text visibility property
     */
    public BooleanProperty isShowHelperTextProperty();

    /**
     * Returns the helper text visibility
     * 
     * @return True if the helper text is visible
     */
    public boolean getIsShowHelperText();

    /**
     * Sets the whether or not the helper text is visible
     * 
     * @param isShowHelperText
     *            Whether the helper text is visible
     */
    public void setIsShowHelperText(boolean isShowHelperText);
}
