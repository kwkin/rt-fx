package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 * A {@link mil.af.eglin.ccf.rt.fx.control.ToggleButton ToggleButton} can have
 * four styles:
 * <ul>
 * <li>RAISED: Appears elevated from the surface.</li>
 * <li>ICON: Appears to place an icon directly on the surface.</li>
 * <li>ACCENTED_ICON: Similar to the icon, but also changes the fill color of
 * the icon when selected.</li>
 * <li>GLOWING_ICON: Similar to the accented icon, but also adds a overlay
 * around the icon..</li>
 * </ul>
 * <p>
 * Each style type is applied as a pseudo class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.ToggleButton
 * @see mil.af.eglin.ccf.rt.fx.control.IconToggleButton
 */
public enum ToggleButtonStyle
{
    ACCENTED_ICON("accented-icon"), 
    GLOWING_ICON("glowing-icon"), 
    ICON("icon"), 
    RAISED("raised");

    private PseudoClass pseudoClass;

    ToggleButtonStyle(String cssName)
    {
        this.pseudoClass = PseudoClass.getPseudoClass(cssName);
    }

    /**
     * Gets the name of the pseudo class for the accent type
     * 
     * @return the name of the pseudo class
     */
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}
