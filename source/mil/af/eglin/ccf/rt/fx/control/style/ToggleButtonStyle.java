package mil.af.eglin.ccf.rt.fx.control.style;

// TODO this enum class needs to be redesigned since toggle button and toggle switch are different classes
public enum ToggleButtonStyle
{
    RAISED("raised"),
    SWITCH("switch");
    
    private String cssName;
    
    ToggleButtonStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
