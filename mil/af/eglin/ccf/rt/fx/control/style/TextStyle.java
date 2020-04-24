package mil.af.eglin.ccf.rt.fx.control.style;

public enum TextStyle
{
    SMALL("small"),
    NORMAL("normal"),
    LARGE("large"),
    TITLE("title");
    
    private String cssName;
    
    TextStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
