package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;

public interface RtComponent
{
    /**
     * Gets the {@code Accent} associated with this component.
     * 
     * @return The accent associated with this component.
     */
    public abstract Accent getAccent();
    
    /**
     * Gets CSS class name defining the style of the real-time component.
     * 
     * @return The name of the CSS class for the real-time component
     */
    public abstract String getRtStyleCssName();
    
    /**
     * 
     * Gets CSS class name defining the accent colors of the real-time component.
     * 
     * @return The name of the CSS class for the accent colors
     */
    public abstract String getRtAccentCssName();
}
