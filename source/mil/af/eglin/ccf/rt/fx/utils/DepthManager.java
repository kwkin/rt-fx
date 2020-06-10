package mil.af.eglin.ccf.rt.fx.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.effect.BlurType;

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
    
    public static DepthManager getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    public void setDepth(Node control, int level) 
    {
        level = Math.max(0, level);
        level = Math.min(getLevels() - 1, level);
        
        // @formatter:off
        control.setEffect(new DepthShadow(BlurType.GAUSSIAN,
            depth.get(level).getColor(),
            depth.get(level).getRadius(),
            depth.get(level).getSpread(),
            depth.get(level).getOffsetX(),
            depth.get(level).getOffsetY()));
        // @formatter:on
    }

    public int getLevels() 
    {
        return depth.size();
    }

    public DepthShadow getShadowAt(int level) 
    {
        return depth.get(level);
    }
}