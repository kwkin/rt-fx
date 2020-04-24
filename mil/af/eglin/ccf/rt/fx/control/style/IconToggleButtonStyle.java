package mil.af.eglin.ccf.rt.fx.control.style;

public enum IconToggleButtonStyle
{
    GLOWING("glowing"),
    HIGHLIGHTED("highlighted"),
    NORMAL("normal");
    
    private String cssName;
    
    IconToggleButtonStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
