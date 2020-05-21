package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

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
    
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}
