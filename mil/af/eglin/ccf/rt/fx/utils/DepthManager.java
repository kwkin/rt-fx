package mil.af.eglin.ccf.rt.fx.utils;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;

// TODO add a shadow transition (similar to fill transition)
public class DepthManager
{
    private List<DepthShadow> depth = new ArrayList<>();

    private DepthManager() 
    {
        // @formatter:off
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0),     0,    0, 0, 0));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  1, 0.12, 0, 1));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  3, 0.15, 0, 1));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  5, 0.18, 0, 2));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26),  7, 0.21, 0, 2));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 10, 0.24, 0, 3));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 13, 0.27, 0, 3));
        depth.add(new DepthShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.26), 16, 0.30, 0, 4));
        // @formatter:on
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