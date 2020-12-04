package mil.af.eglin.ccf.rt.fx.utils;

import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;

/**
 * Defines standard depth shadow parameters for representing levels of
 * elevation. The rendered shadow makes the corresponding component appear to
 * have a lower elevation the lower the level is.
 */
public enum Depth
{
    // @formatter:off
    LEVEL0(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0),     0,    0, 0, 0)), 
    LEVEL1(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  1, 0.12, 0, 1)), 
    LEVEL2(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  3, 0.15, 0, 1)), 
    LEVEL3(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  5, 0.18, 0, 2)), 
    LEVEL4(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  7, 0.21, 0, 2)), 
    LEVEL5(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 10, 0.24, 0, 3)), 
    LEVEL6(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 13, 0.27, 0, 3)), 
    LEVEL7(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 16, 0.30, 0, 4)); 
    // @formatter:on

    private DepthShadow shadow;

    Depth(DepthShadow shadow)
    {
        this.shadow = shadow;
    }

    /**
     * Gets the {link DepthShadow depth shadow} at the provided level
     * 
     * @param level the level to get depth shadow
     * @return the {link DepthShadow depth shadow} at the provided level
     * @throws IllegalArgumentException if the provided {@code name} does not
     *             equal any of the standard theme names
     */
    public static DepthShadow getByLevel(int level)
    {
        if (level < 0)
        {
            throw new IllegalArgumentException("Level must be greater than or equal to 0.");
        }
        else if (level >= Depth.values().length)
        {
            throw new IllegalArgumentException(String.format("Level must be less than %d", Depth.values().length));
        }
        return Depth.values()[level].getShadow();
    }

    /**
     * Gets the {link DepthShadow depth shadow}
     * @return the {link DepthShadow depth shadow}
     */
    public DepthShadow getShadow()
    {
        return this.shadow;
    }
}
