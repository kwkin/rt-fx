package mil.af.eglin.ccf.rt.fx.control.style;

public enum ColorPickerStyle
{
    FILLED("filled"),
    FLAT("flat"),
    ICON("icon"),
    ICON2("icon2");
    
    private String cssName;
    
    ColorPickerStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}