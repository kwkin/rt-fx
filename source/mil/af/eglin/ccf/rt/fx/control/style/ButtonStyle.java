package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 * A {@code mil.af.eglin.ccf.rt.fx.control.Button Button} can have three styles:
 * <ul>
 * <li>RAISED: Raised buttons appear elevated from the surface. This is
 * typically used for high emphasis actions.</li>
 * <li>FLAT: Flat buttons appear to be built into the surface. This is typically
 * used to be more subtle and bring more attention to other elements.</li>
 * <li>ICON: Icon buttons appear to be built into the surface. This is similar
 * to the flat button, but puts more emphasis on the icon instead of the button
 * itself.</li>
 * </ul>
 * <p>
 * Each style type is applied as a pseudo class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.Button
 */
public enum ButtonStyle
{
    FLAT("flat"), 
    RAISED("raised"), 
    ICON("icon");

    private PseudoClass pseudoClass;

    ButtonStyle(String cssName)
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
