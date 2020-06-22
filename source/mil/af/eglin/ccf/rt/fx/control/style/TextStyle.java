package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 *  A {@code mil.af.eglin.ccf.rt.fx.control.Label Label} can have five styles:
 * <ul>
 * <li>SMALL: The smallest text size.</li>
 * <li>NORMAL: The standard text size.</li>
 * <li>LARGE: The second largest text size.</li>
 * <li>TITLE: The largest text size.</li>
 * </ul>
 * <p>
 * Each style type is applied as a pseudo class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.Text
 */
public enum TextStyle
{
    SMALL("small"),
    NORMAL("normal"),
    LARGE("large"),
    TITLE("title");

    private PseudoClass pseudoClass;
    
    TextStyle(String cssName)
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
