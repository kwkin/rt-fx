package mil.af.eglin.ccf.rt.fx.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.effect.BlurType;

/**
 * The {@code DepthManager} applies {@link DepthShadow depth shadows} to components.
 */
public class DepthManager
{
    private List<DepthShadow> depth = new ArrayList<>();

    private DepthManager() 
    {
        for (Depth depthLevel : Depth.values())
        {
            depth.add(depthLevel.getShadow());
        }
    }

    private static class InstanceHolder 
    {
        final static DepthManager INSTANCE = new DepthManager();
    }
    
    /**
     * Gets the instance of the {@code DepthManager}
     * 
     * @return the instance of the {@code DepthManager}
     */
    public static DepthManager getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    /**
     * Sets the depth shadow of the provided control to the given level
     * 
     * @param control the control to apply the given level
     * @param level the depth shdow level to apply to the control
     */
    public void setDepth(Node control, int level) 
    {
        level = Math.max(0, level);
        level = Math.min(getLevelCount() - 1, level);
        
        // @formatter:off
        control.setEffect(new DepthShadow(BlurType.GAUSSIAN,
            this.depth.get(level).getColor(),
            this.depth.get(level).getRadius(),
            this.depth.get(level).getSpread(),
            this.depth.get(level).getOffsetX(),
            this.depth.get(level).getOffsetY()));
        // @formatter:on
    }

    /**
     * Gets the number of standard elevation levels
     * 
     * @return the number of standard elevation levels
     */
    public int getLevelCount() 
    {
        return this.depth.size();
    }

    /**
     * Gets the depth shadow at the provided level
     * 
     * @param level the level to get the depth shadow at
     * @return the depth shadow at the provided level
     */
    public DepthShadow getShadowAt(int level) 
    {
        return this.depth.get(level);
    }
}