package mil.af.eglin.ccf.rt.fx.control.style;

/**
 *  A button can have three styles:
 * <ul>
 * <li>RAISED: Appears elevated from the surface. This is typically used for
 * high emphasis actions.</li>
 * <li>FLAT: Appears to be built into the surface. This is typically used to be
 * more subtle and bring more attention to other elements.</li>
 * <li>ICON: Appears to be built into the surface. This is similar to the flat
 * button, but puts more emphasis on the icon instead of the button itself.</li>
 * </ul>
 */
public enum ButtonStyle
{
    FLAT("flat"),
    RAISED("raised"),
    ICON("icon");
    
    private String cssName;
    
    ButtonStyle(String cssName)
    {
        this.cssName = cssName;
    }
    
    public String getCssName()
    {
        return this.cssName;
    }
}
