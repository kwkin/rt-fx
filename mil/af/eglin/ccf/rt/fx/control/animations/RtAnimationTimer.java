package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.animation.AnimationTimer;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class RtAnimationTimer extends AnimationTimer
{
    private Runnable onFinished = null;
    private Set<AnimationHandler> animationHandlers = new HashSet<>();
    private long startTime = -1;
    private boolean running = false;
    private List<CachedFrame> caches = new ArrayList<>();
    private double totalElapsedMilliseconds;
    private HashMap<RtKeyFrame, AnimationHandler> mutableFrames = new HashMap<>();

    public RtAnimationTimer(RtKeyFrame... keyFrames)
    {
        for (RtKeyFrame keyFrame : keyFrames)
        {
            Duration duration = keyFrame.getDuration();
            Set<RtKeyValue<?>> keyValuesSet = keyFrame.getKeyValues();
            if (!keyValuesSet.isEmpty())
            {
                AnimationHandler handler = new AnimationHandler(duration, keyFrame.getAnimateCondition(),
                        keyFrame.getKeyValues());
                this.animationHandlers.add(handler);
            }
        }
    }

    public void setCacheNodes(Node... nodesToCache)
    {
        this.caches.clear();
        if (nodesToCache != null)
        {
            for (Node node : nodesToCache)
            {
                this.caches.add(new CachedFrame(node));
            }
        }
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public void addKeyFrame(RtKeyFrame keyFrame) throws Exception
    {
        if (!isRunning())
        {
            Duration duration = keyFrame.getDuration();
            final Set<RtKeyValue<?>> keyValuesSet = keyFrame.getKeyValues();
            if (!keyValuesSet.isEmpty())
            {
                final AnimationHandler handler = new AnimationHandler(duration, keyFrame.getAnimateCondition(),
                        keyFrame.getKeyValues());
                this.animationHandlers.add(handler);
                this.mutableFrames.put(keyFrame, handler);
            }
        }
    }

    public void removeKeyFrame(RtKeyFrame keyFrame) throws Exception
    {
        if (!isRunning())
        {
            AnimationHandler handler = this.mutableFrames.get(keyFrame);
            this.animationHandlers.remove(handler);
        }
    }

    public void applyEndValues()
    {
        if (isRunning())
        {
            super.stop();
        }
        for (AnimationHandler handler : this.animationHandlers)
        {
            handler.applyEndValues();
        }
        this.startTime = -1;
    }

    public void setOnFinished(Runnable onFinished)
    {
        this.onFinished = onFinished;
    }

    public void dispose()
    {
        this.caches.clear();
        for (AnimationHandler handler : this.animationHandlers)
        {
            handler.dispose();
        }
        this.animationHandlers.clear();
    }

    public void reverseAndContinue()
    {
        if (isRunning())
        {
            super.stop();
            for (AnimationHandler handler : this.animationHandlers)
            {
                handler.reverse(this.totalElapsedMilliseconds);
            }
            this.startTime = -1;
            super.start();
        } else
        {
            start();
        }
    }

    @Override
    public void handle(long now)
    {
        this.startTime = this.startTime == -1 ? now : this.startTime;
        this.totalElapsedMilliseconds = (now - this.startTime) / 1000000.0;
        boolean stop = true;
        for (AnimationHandler handler : this.animationHandlers)
        {
            handler.animate(this.totalElapsedMilliseconds);
            if (!handler.finished)
            {
                stop = false;
            }
        }
        if (stop)
        {
            this.stop();
        }
    }

    @Override
    public void start()
    {
        super.start();
        this.running = true;
        this.startTime = -1;
        for (AnimationHandler animationHandler : this.animationHandlers)
        {
            animationHandler.init();
        }
        for (CachedFrame cache : this.caches)
        {
            cache.cache();
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.running = false;
        for (AnimationHandler handler : this.animationHandlers)
        {
            handler.clear();
        }
        for (CachedFrame cache : this.caches)
        {
            cache.restore();
        }
        if (this.onFinished != null)
        {
            this.onFinished.run();
        }
    }

    static class AnimationHandler
    {
        private double duration;
        private double currentDuration;
        private Set<RtKeyValue<?>> keyValues;
        private Supplier<Boolean> animationCondition = null;
        private boolean finished = false;

        private HashMap<WritableValue<?>, Object> initialValuesMap = new HashMap<>();
        private HashMap<WritableValue<?>, Object> endValuesMap = new HashMap<>();

        AnimationHandler(Duration duration, Supplier<Boolean> animationCondition, Set<RtKeyValue<?>> keyValues)
        {
            this.duration = duration.toMillis();
            this.currentDuration = this.duration;
            this.keyValues = keyValues;
            this.animationCondition = animationCondition;
        }

        public void init()
        {
            this.finished = this.animationCondition == null ? false : !this.animationCondition.get();
            for (RtKeyValue<?> keyValue : keyValues)
            {
                if (keyValue.getTarget() != null)
                {
                    if (!this.initialValuesMap.containsKey(keyValue.getTarget()))
                    {
                        this.initialValuesMap.put(keyValue.getTarget(), keyValue.getTarget().getValue());
                    }
                    if (!this.endValuesMap.containsKey(keyValue.getTarget()))
                    {
                        this.endValuesMap.put(keyValue.getTarget(), keyValue.getEndValue());
                    }
                }
            }
        }

        void reverse(double now)
        {
            this.finished = this.animationCondition == null ? false : !this.animationCondition.get();
            this.currentDuration = this.duration - (this.currentDuration - now);
            for (RtKeyValue<?> keyValue : this.keyValues)
            {
                WritableValue<?> target = keyValue.getTarget();
                if (target != null)
                {
                    this.initialValuesMap.put(target, target.getValue());
                    this.endValuesMap.put(target, keyValue.getEndValue());
                }
            }
        }

        public void animate(double now)
        {
            if (this.finished)
            {
                return;
            }
            if (now <= this.currentDuration)
            {
                for (RtKeyValue<?> keyValue : this.keyValues)
                {
                    if (keyValue.isValid())
                    {
                        @SuppressWarnings("unchecked")
                        WritableValue<Object> target = (WritableValue<Object>) keyValue.getTarget();
                        Object endValue = this.endValuesMap.get(target);
                        if (endValue != null && target != null && !target.getValue().equals(endValue))
                        {
                            target.setValue(keyValue.getInterpolator().interpolate(this.initialValuesMap.get(target),
                                    endValue, now / this.currentDuration));
                        }
                    }
                }
            } else
            {
                if (!this.finished)
                {
                    this.finished = true;
                    for (RtKeyValue<?> keyValue : this.keyValues)
                    {
                        if (keyValue.isValid())
                        {
                            @SuppressWarnings("unchecked")
                            WritableValue<Object> target = (WritableValue<Object>) keyValue.getTarget();
                            if (target != null)
                            {
                                final Object endValue = keyValue.getEndValue();
                                if (endValue != null)
                                {
                                    target.setValue(endValue);
                                }
                            }
                        }
                    }
                    this.currentDuration = this.duration;
                }
            }
        }

        public void applyEndValues()
        {
            for (RtKeyValue<? extends Object> keyValue : this.keyValues)
            {
                if (keyValue.isValid())
                {
                    @SuppressWarnings("unchecked")
                    WritableValue<Object> target = ((WritableValue<Object>) keyValue.getTarget());
                    if (target != null)
                    {
                        final Object endValue = keyValue.getEndValue();
                        if (endValue != null && !target.getValue().equals(endValue))
                        {
                            target.setValue(endValue);
                        }
                    }
                }
            }
        }

        public void clear()
        {
            this.initialValuesMap.clear();
            this.endValuesMap.clear();
        }

        void dispose()
        {
            clear();
            this.keyValues.clear();
        }
    }
}