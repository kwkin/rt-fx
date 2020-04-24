package mil.af.eglin.ccf.rt.fx.control.style;

public enum LabelStyle
{
    SMALL("small"),
    NORMAL("normal"),
    LARGE("large"),
    TITLE("title"),
    BLOCK_TITLE("block-title");
    
    private String cssName;
    
    LabelStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
