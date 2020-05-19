package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

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
    
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}
