package mil.af.eglin.ccf.rt.fx.style;

import javafx.scene.paint.Color;

/**
 * The default palette contains preset colors used for component themes when
 * there is an error loading the standard CSS. Changing values of the default
 * palette will not dynamically updated the 
 */
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

    /**
     * Gets the color used to accent components
     * 
     * @return the color used to accent components
     */
    public Color getAccentColor()
    {
        return accentColor;
    }

    /**
     * Gets the color used to accent components
     * 
     * @return the color used to accent components
     */
    public Color getLightAccentColor()
    {
        return lightAccentColor;
    }

    /**
     * Gets the primary color used to style components
     * 
     * @return the primary color used to style components
     */
    public Color getBaseColor()
    {
        return baseColor;
    }


    /**
     * Gets the primary light color used to style components
     * 
     * @return the primary light color used to style components
     */
    public Color getLightBaseColor()
    {
        return lightBaseColor;
    }
}
