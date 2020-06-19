package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

public enum TableViewStyle
{
    PLAIN("plain"),
    ZEBRA("zebra");

    private PseudoClass pseudoClass;
    
    TableViewStyle(String cssName)
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
