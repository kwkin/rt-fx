package mil.af.eglin.ccf.rt.fx.control.style;

public enum TextFieldStyle
{
    FILLED("filled"),
    FLAT("flat");
    
    private String cssName;
    
    TextFieldStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
