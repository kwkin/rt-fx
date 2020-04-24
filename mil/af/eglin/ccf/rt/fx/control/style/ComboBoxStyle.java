package mil.af.eglin.ccf.rt.fx.control.style;

public enum ComboBoxStyle
{
    FILLED("filled"),
    FLAT("flat");
    
    private String cssName;
    
    ComboBoxStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
