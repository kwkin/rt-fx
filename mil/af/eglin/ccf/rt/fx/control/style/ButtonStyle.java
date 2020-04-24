package mil.af.eglin.ccf.rt.fx.control.style;

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
