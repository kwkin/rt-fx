package mil.af.eglin.ccf.rt.fx.style;

import javafx.scene.paint.Color;

public class DefaultPalette
{
    private Color accentColor = Color.valueOf("#414f63");
    private Color lightAccentColor = Color.valueOf("#ff8e9b");
    private Color baseColor = Color.valueOf("#dcdcdc");
    private Color lightBaseColor = Color.valueOf("#f6f6f6");
    
    private DefaultPalette()
    {
        
    }

    private static class InstanceHolder 
    {
        final static DefaultPalette INSTANCE = new DefaultPalette();
    }
    
    public static DefaultPalette getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    public Color getAccentColor()
    {
        return accentColor;
    }
    
    public Color getLightAccentColor()
    {
        return lightAccentColor;
    }
    
    public Color getBaseColor()
    {
        return baseColor;
    }
    
    public Color getLightBaseColor()
    {
        return lightBaseColor;
    }
}
