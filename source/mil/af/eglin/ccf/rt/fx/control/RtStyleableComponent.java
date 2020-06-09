package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;

/**
 * A real-time styleable component defines standard a
 * {@link mil.af.eglin.ccf.rt.fx.control.style.Accent Accent} and a unique CSS
 * name.
 * 
 * @author kwkin
 */
public interface RtStyleableComponent
{
    /**
     * Gets the {@code Accent} associated with this component.
     * <p>
     * An accent defines the standard color scheme used by the component.
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
}
