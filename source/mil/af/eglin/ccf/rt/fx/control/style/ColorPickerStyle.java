package mil.af.eglin.ccf.rt.fx.control.style;

import javafx.css.PseudoClass;

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
     * Gets the name of the pseudo class
     * 
     * @return The name of the pseudo class
     */
    public PseudoClass getPseudoClass()
    {
        return this.pseudoClass;
    }
}