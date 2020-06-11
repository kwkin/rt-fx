package mil.af.eglin.ccf.rt.fx.control.style;

/**
 * An accent defines the standard color scheme used by a component.
 */
public enum Accent
{
    BASE("base"),
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

    /**
     * Gets the CSS class name associated with the accent type.
     * 
     * @return The name of the CSS class for the accent colors
     */
    public String getCssName()
    {
        return this.cssName;
    }
}