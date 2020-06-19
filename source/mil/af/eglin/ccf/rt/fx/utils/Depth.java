package mil.af.eglin.ccf.rt.fx.utils;

import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;

/**
 * Defines standard depth shadow parameters for representing elevation at differnet levels.
 */
public enum Depth
{
    LEVEL0(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0),     0,    0, 0, 0)), 
    LEVEL1(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  1, 0.12, 0, 1)), 
    LEVEL2(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  3, 0.15, 0, 1)), 
    LEVEL3(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  5, 0.18, 0, 2)), 
    LEVEL4(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  7, 0.21, 0, 2)), 
    LEVEL5(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 10, 0.24, 0, 3)), 
    LEVEL6(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 13, 0.27, 0, 3)), 
    LEVEL7(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 16, 0.30, 0, 4)); 
    
    private DepthShadow shadow;
    
    Depth(DepthShadow shadow)
    {
        this.shadow = shadow;
    }
    
    public DepthShadow getShadow()
    {
        return this.shadow;
    }
}
