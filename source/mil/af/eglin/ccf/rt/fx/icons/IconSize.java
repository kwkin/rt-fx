package mil.af.eglin.ccf.rt.fx.icons;

/**
 * An {@link mil.af.eglin.ccf.rt.fx.control.Icon Icon} has several standard icon
 * sizes. The {@code IconSize} contains a single integer value used to represent
 * the width and height of the icon.
 */
public enum IconSize
{
    SIZE_16(16), SIZE_24(24), SIZE_32(32), SIZE_48(48), SIZE_64(64), SIZE_96(96);

    private int iconSize;

    private IconSize(int size)
    {
        this.iconSize = size;
    }

    /**
     * Gets the value used for the width and height of the icon
     * 
     * @return the value used for the width and height of the icon
     */
    public int getIconSize()
    {
        return this.iconSize;
    }
}
