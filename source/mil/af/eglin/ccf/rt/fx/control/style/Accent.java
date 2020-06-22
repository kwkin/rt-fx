package mil.af.eglin.ccf.rt.fx.control.style;

/**
 * An accent defines the standard color scheme used by a component.
 * <p>
 * Each accent is applied as its own CSS style class.
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
     * Gets the CSS style class name for the accent type.
     * 
     * @return The name of the CSS style class
     */
    public String getStyleClassName()
    {
        return this.cssName;
    }
}