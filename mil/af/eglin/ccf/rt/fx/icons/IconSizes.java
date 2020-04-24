package mil.af.eglin.ccf.rt.fx.icons;

public enum IconSizes
{
    SIZE_16(16),
    SIZE_24(24),
    SIZE_32(32),
    SIZE_48(48),
    SIZE_64(64),
    SIZE_96(96);
    
    private int iconSize;
    
    private IconSizes(int size)
    {
        this.iconSize = size;
    }
    
    public int getIconSize()
    {
        return this.iconSize;
    }
}
