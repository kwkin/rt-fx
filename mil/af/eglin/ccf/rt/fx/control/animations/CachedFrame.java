package mil.af.eglin.ccf.rt.fx.control.animations;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class CachedFrame
{
    private Node node;
    private boolean cache;
    private boolean cachedShape;
    private boolean snapToPixel;
    private CacheHint cacheHint = CacheHint.DEFAULT;
    private AtomicBoolean isCached = new AtomicBoolean(false);

    public CachedFrame(Node node)
    {
        this.node = node;
    }

    public void cache()
    {
        if (!isCached.getAndSet(true))
        {
            this.cache = node.isCache();
            this.cacheHint = node.getCacheHint();
            this.node.setCache(true);
            this.node.setCacheHint(CacheHint.SPEED);
            if (node instanceof Region)
            {
                Region region = (Region) this.node;
                this.cachedShape = region.isCacheShape();
                this.snapToPixel = region.isSnapToPixel();
                region.setCacheShape(true);
                region.setSnapToPixel(true);
            }
        }
    }

    public void restore()
    {
        if (this.isCached.getAndSet(false))
        {
            this.node.setCache(this.cache);
            this.node.setCacheHint(this.cacheHint);
            if (node instanceof Region)
            {
                Region region = (Region) this.node;
                region.setCacheShape(this.cachedShape);
                region.setSnapToPixel(this.snapToPixel);
            }
        }
    }
}