package mil.af.eglin.ccf.rt.fx.control.style;

public enum ListViewStyle
{
    PLAIN("plain"),
    ZEBRA("zebra");
    
    private String cssName;
    
    ListViewStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
