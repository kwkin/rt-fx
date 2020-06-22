package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 * A {@code mil.af.eglin.ccf.rt.fx.control.ListView ListView} can have two
 * styles:
 * <ul>
 * <li>PLAIN: Each row will have the same color and no border.</li>
 * <li>BUTTON: Rows will alternate colors.</li>
 * </ul>
 * <p>
 * Each style type is applied as a pseudo class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.ListView
 */
public enum ListViewStyle
{
    SOLID("solid"), 
    STRIPPED("striped");

    private PseudoClass pseudoClass;

    ListViewStyle(String cssName)
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
