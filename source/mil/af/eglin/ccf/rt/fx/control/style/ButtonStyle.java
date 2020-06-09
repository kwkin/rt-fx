package mil.af.eglin.ccf.rt.fx.control.style;

// TOOD remove icon enum
public enum ButtonStyle
{
    FLAT("flat"),
    RAISED("raised"),
    ICON("icon");
    
    private String cssName;
    
    ButtonStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
