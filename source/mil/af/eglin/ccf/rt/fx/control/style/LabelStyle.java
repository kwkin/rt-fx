package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

/**
 * 
 *  A button can have five styles:
 * <ul>
 * <li>SMALL: The smallest text size.</li>
 * <li>NORMAL: The standard text size.</li>
 * <li>LARGE: The second largest text size.</li>
 * <li>TITLE: The largest text size.</li>
 * <li>BLOCK_TITLE: The largest text size with an accented background.</li>
 * </ul>
 */
public enum LabelStyle
{
    SMALL("small"),
    NORMAL("normal"),
    LARGE("large"),
    TITLE("title"),
    BLOCK_TITLE("block-title");
    
    private PseudoClass pseudoClass;
    
    LabelStyle(String cssName)
    {
        this.pseudoClass = PseudoClass.getPseudoClass(cssName);
    }
    
    /**
     * Gets the name of the pseudo class
     * 
     * @return The name of the pseudo class
     */
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}
