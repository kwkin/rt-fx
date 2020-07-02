package mil.af.eglin.ccf.rt.fx.icons;

/**
 * An {@link mil.af.eglin.ccf.rt.fx.control.Icon Icon} can have three scaling
 * options:
 * <ul>
 * <li>NONE: The icon will not be scaled to fit the parent container. Instead,
 * the icon size will used the preferred height and width.</li>
 * <li>UNIFORM_FILL: The icon will be stretched to fir the width and height of
 * the parent container while preserving aspect ratio. If the parent's height
 * and width is not equal, then the icon will be fitted to the smallest
 * dimension.</li>
 * <li>FILL: The icon will be stretched to fit the width and height of the
 * parent container without preserving aspect ratio.</li>
 * </ul>
 */
public enum IconScale
{
    NONE, UNIFORM_FILL, FILL;
}
