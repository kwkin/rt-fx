package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.animation.AnimationTimer;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class RtAnimationTimer extends AnimationTimer
{
    private Runnable onFinished = null;
    private List<AnimationHandler> animationHandlers = new ArrayList<>();
    private long startTime = -1;
    private boolean running = false;
    private List<CachedFrame> caches = new ArrayList<>();
    private double totalElapsedMilliseconds;
    private HashMap<RtKeyFrame, AnimationHandler> mutableFrames = new HashMap<>();

    private int currentHandlerIndex = 0;
    private double lastFrameTime = 0;

    public RtAnimationTimer(RtKeyFrame... keyFrames)
    {
        for (RtKeyFrame keyFrame : keyFrames)
        {
            Duration duration = keyFrame.getDuration();
            Set<RtKeyValue<?>> keyValuesSet = keyFrame.getKeyValues();
            if (!keyValuesSet.isEmpty())
            {
                AnimationHandler handler = new AnimationHandler(duration, keyFrame.getApplyCondition(),
                        keyFrame.getAnimateCondition(), keyFrame.getKeyValues());
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
                final AnimationHandler handler = new AnimationHandler(duration, keyFrame.getApplyCondition(),
                        keyFrame.getAnimateCondition(), keyFrame.getKeyValues());
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

    /**
     * Plays the next animation from the current state.
     */
    public void reverseAndContinue()
    {
        if (isRunning())
        {
            super.stop();
            boolean isPlaying = false;
            for (AnimationHandler handler : this.animationHandlers)
            {
                handler.reverse(this.totalElapsedMilliseconds);
                if (handler.useAnimation())
                {
                    isPlaying = true;
                }
                else
                {
                    handler.applyEndValues();
                }
            }
            this.startTime = -1;
            if (isPlaying)
            {
                super.start();
            }
        } 
        else
        {
            start();
        }
    }
    
    /**
     * Skips to the end of the currently played animation, and begins the next animation.
     */
    public void skipAndContinue()
    {
        if (isRunning())
        {
            super.stop();
            boolean isPlaying = false;
            for (AnimationHandler handler : this.animationHandlers)
            {
                if (handler.useAnimation())
                {
                    isPlaying = true;
                    handler.applyCurrentEndValues();
                }
                else
                {
                    handler.applyEndValues();
                }
            }
            this.startTime = -1;
            if (isPlaying)
            {
                super.start();
            }
        }
        else
        {
            start();
        }
    }
    
    @Override
    public void handle(long now)
    {
        this.startTime = this.startTime == -1 ? now : this.startTime;
        this.totalElapsedMilliseconds = (now - this.startTime) / 1000000.0;
        
        AnimationHandler currentHandler = this.animationHandlers.get(currentHandlerIndex);
        currentHandler.animate(this.totalElapsedMilliseconds - lastFrameTime);

        
        if (totalElapsedMilliseconds - lastFrameTime > currentHandler.duration)
        {
            lastFrameTime += currentHandler.duration;
            currentHandlerIndex++;
            if (currentHandlerIndex >= this.animationHandlers.size())
            {
                lastFrameTime = 0;
                stop();
            }
            else
            {
                this.animationHandlers.get(currentHandlerIndex).init();
            }
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
        this.currentHandlerIndex = 0;
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
        private Supplier<Boolean> applyCondition = null;
        private Supplier<Boolean> animateCondition = null;
        
        private boolean finished = false;
        private boolean useAnimation = true;

        private HashMap<WritableValue<?>, Object> initialValuesMap = new HashMap<>();
        private HashMap<WritableValue<?>, Object> endValuesMap = new HashMap<>();

        AnimationHandler(Duration duration, Supplier<Boolean> applyCondition, Supplier<Boolean> animateCondition, Set<RtKeyValue<?>> keyValues)
        {
            this.duration = duration.toMillis();
            this.currentDuration = this.duration;
            this.keyValues = keyValues;
            this.applyCondition = applyCondition;
            this.animateCondition = animateCondition;
        }

        public void init()
        {
            this.finished = this.applyCondition == null ? false : !this.applyCondition.get();
            this.useAnimation = this.animateCondition == null ? true : this.animateCondition.get();
            
            for (RtKeyValue<?> keyValue : keyValues)
            {
                if (keyValue.getTarget() != null)
                {
                    this.initialValuesMap.put(keyValue.getTarget(), keyValue.getTarget().getValue());
                    this.endValuesMap.put(keyValue.getTarget(), keyValue.getEndValue());
                }
            }
        }
        
        boolean isFinished()
        {
            return this.finished;
        }
        
        boolean useAnimation()
        {
            return this.useAnimation;
        }

        void reverse(double now)
        {
            this.finished = this.applyCondition == null ? false : !this.applyCondition.get();
            this.useAnimation = this.animateCondition == null ? true : this.animateCondition.get();
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
            if (now <= this.currentDuration && this.useAnimation)
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
                            double time = this.currentDuration == 0 ? 0 : now / this.currentDuration;
                            target.setValue(keyValue.getInterpolator().interpolate(this.initialValuesMap.get(target),
                                    endValue, time));
                        }
                    }
                }
            } 
            else
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

        public void applyCurrentEndValues()
        {
            for (RtKeyValue<? extends Object> keyValue : this.keyValues)
            {
                if (keyValue.isValid())
                {
                    @SuppressWarnings("unchecked")
                    WritableValue<Object> target = ((WritableValue<Object>) keyValue.getTarget());
                    if (target != null)
                    {
                        final Object currentEndValue = keyValue.getCurrentEndValue();
                        final Object endValue = keyValue.getEndValue();
                        if (currentEndValue != null)
                        {
                            target.setValue(currentEndValue);
                            this.initialValuesMap.put(target, currentEndValue);
                            this.endValuesMap.put(target, endValue);
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