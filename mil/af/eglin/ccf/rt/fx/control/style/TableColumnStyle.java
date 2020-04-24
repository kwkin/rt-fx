package mil.af.eglin.ccf.rt.fx.control.style;

public enum TableColumnStyle
{
    TEXT("text"),
    NUMBER("number");
    
    private String cssName;
    
    TableColumnStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
