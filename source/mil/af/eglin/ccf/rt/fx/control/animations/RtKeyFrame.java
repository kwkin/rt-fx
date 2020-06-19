package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.util.Duration;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

public class RtKeyFrame
{
    private Duration duration;
    private Set<RtKeyValue<?>> keyValues = new CopyOnWriteArraySet<>();
    private Supplier<Boolean> animateCondition = null;
    private Supplier<Boolean> applyCondition = null;

    public RtKeyFrame()
    {
        
    }
    
    public RtKeyFrame(Duration duration, RtKeyValue<?>... keyValues)
    {
        this.duration = duration;
        for (final RtKeyValue<?> keyValue : keyValues)
        {
            if (keyValue != null)
            {
                this.keyValues.add(keyValue);
            }
        }
    }

    public RtKeyFrame(Duration duration, List<RtKeyValue<?>> keyValues)
    {
        this.duration = duration;
        for (final RtKeyValue<?> keyValue : keyValues)
        {
            if (keyValue != null)
            {
                this.keyValues.add(keyValue);
            }
        }
    }

    public Duration getDuration()
    {
        return duration;
    }

    public Set<RtKeyValue<?>> getKeyValues()
    {
        return keyValues;
    }

    public Supplier<Boolean> getAnimateCondition()
    {
        return animateCondition;
    }

    public Supplier<Boolean> getApplyCondition()
    {
        return applyCondition;
    }

    public static RtKeyFrameBuilder builder()
    {
        return new RtKeyFrameBuilder();
    }

    public static final class RtKeyFrameBuilder
    {
        private Duration duration;
        private Set<RtKeyValue<?>> keyValues = new CopyOnWriteArraySet<>();
        private Supplier<Boolean> animateCondition = null;
        private Supplier<Boolean> applyCondition = null;

        private RtKeyFrameBuilder()
        {
        }

        public RtKeyFrameBuilder setDuration(Duration duration)
        {
            this.duration = duration;
            return this;
        }

        public RtKeyFrameBuilder setKeyValues(RtKeyValue<?>... keyValues)
        {
            for (final RtKeyValue<?> keyValue : keyValues)
            {
                if (keyValue != null)
                {
                    this.keyValues.add(keyValue);
                }
            }
            return this;
        }

        public RtKeyFrameBuilder setAnimateCondition(Supplier<Boolean> animateCondition)
        {
            this.animateCondition = animateCondition;
            return this;
        }

        public RtKeyFrameBuilder setApplyCondition(Supplier<Boolean> applyCondition)
        {
            this.applyCondition = applyCondition;
            return this;
        }

        public RtKeyFrame build()
        {
            RtKeyFrame keyFrame = new RtKeyFrame();
            keyFrame.duration = this.duration;
            keyFrame.keyValues = this.keyValues;
            keyFrame.animateCondition = this.animateCondition;
            keyFrame.applyCondition = this.applyCondition;
            return keyFrame;
        }
    }
}