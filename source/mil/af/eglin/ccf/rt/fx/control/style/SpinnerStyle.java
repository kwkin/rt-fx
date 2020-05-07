package mil.af.eglin.ccf.rt.fx.control.style;

public enum SpinnerStyle
{
    FLAT("flat"),
    RAISED("filled");
    
    private String cssName;
    
    SpinnerStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
