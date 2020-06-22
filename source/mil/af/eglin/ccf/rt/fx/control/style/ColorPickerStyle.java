package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 * A {@code mil.af.eglin.ccf.rt.fx.control.ColorPicker ColorPicker} can have
 * three styles:
 * <ul>
 * <li>COMBO_BOX: Combo-boxes color pickers have the same styling features as
 * rt-fx combo-boxes. These color pickers can be styled with floating labels and
 * helper text.</li>
 * <li>BUTTON: Button color pickers are styled similarly to raised buttons, but
 * it's background matches the selected color picker value.</li>
 * <li>ICON: Icon color pickers are styled similarly to icon buttons, except the
 * icon color matches the select color picker value.</li>
 * </ul>
 * <p>
 * Each style type is applied as a pseudo class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.ColorPicker
 */
public enum ColorPickerStyle
{
    COMBO_BOX("combo-box"), 
    BUTTON("button"), 
    ICON("icon");

    private PseudoClass pseudoClass;

    ColorPickerStyle(String cssName)
    {
        this.pseudoClass = PseudoClass.getPseudoClass(cssName);
    }

    /**
     * Gets the name of the pseudo class for the accent type
     * 
     * @return The name of the pseudo class
     */
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}