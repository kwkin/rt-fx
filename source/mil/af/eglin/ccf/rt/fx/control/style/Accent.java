package mil.af.eglin.ccf.rt.fx.control.style;

public enum Accent
{
    // TODO remove base light and dark
    BASE_DARK("base-dark"),
    BASE_LIGHT("base-light"),
    BASE_MID("base-mid"),
    PRIMARY_DARK("primary-dark"),
    PRIMARY_LIGHT("primary-light"),
    PRIMARY_MID("primary-mid"),
    SECONDARY_DARK("secondary-dark"),
    SECONDARY_LIGHT("secondary-light"),
    SECONDARY_MID("secondary-mid");
    
    private String cssName;
    
    Accent(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
    
    public String getFullAccentName(String cssName)
    {
        return String.format("%s-%s", cssName, getCssName());
    }
}
