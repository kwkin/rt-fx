package mil.af.eglin.ccf.rt.fx.control.style;

public enum TableViewStyle
{
    PLAIN("plain"),
    ZEBRA("zebra");
    
    private String cssName;
    
    TableViewStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
