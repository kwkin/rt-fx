package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.animation.Interpolator;
import javafx.beans.value.WritableValue;
import java.util.function.Supplier;

public class RtKeyValue<T>
{
    private WritableValue<T> target;
    private Supplier<WritableValue<T>> targetSupplier;
    private T endValue;
    private Supplier<T> endValueSupplier;
    private Interpolator interpolator;
    private Supplier<Boolean> animateCondition;

    private RtKeyValue()
    {
        this.animateCondition = () -> true;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public WritableValue<T> getTarget()
    {
        return target == null ? targetSupplier.get() : target;
    }

    public Supplier<WritableValue<T>> getTargetSupplier()
    {
        return this.targetSupplier;
    }

    public T getEndValue()
    {
        return endValue == null ? endValueSupplier.get() : endValue;
    }

    public Supplier<T> getEndValueSupplier()
    {
        return this.endValueSupplier;
    }

    public Interpolator getInterpolator()
    {
        return interpolator;
    }

    public Supplier<Boolean> getAnimateCondition()
    {
        return animateCondition;
    }

    public boolean isValid()
    {
        return animateCondition == null ? true : animateCondition.get();
    }

    public static final class Builder
    {
        public <T> RtKeyValueBuilder<T> setTarget(WritableValue<T> target)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setTarget(target);
            return builder;
        }

        public <T> RtKeyValueBuilder<T> setTargetSupplier(Supplier<WritableValue<T>> targetSupplier)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setTargetSupplier(targetSupplier);
            return builder;
        }

        public <T> RtKeyValueBuilder<T> setEndValueSupplier(Supplier<T> endValueSupplier)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setEndValueSupplier(endValueSupplier);
            return builder;
        }

        public <T> RtKeyValueBuilder<T> setEndValue(T endValue)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setEndValue(endValue);
            return builder;
        }

        public <T> RtKeyValueBuilder<T> setInterpolator(Interpolator interpolator)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setInterpolator(interpolator);
            return builder;
        }

        public <T> RtKeyValueBuilder<T> setAnimateCondition(Supplier<Boolean> animateCondition)
        {
            RtKeyValueBuilder<T> builder = new RtKeyValueBuilder<>();
            builder.setAnimateCondition(animateCondition);
            return builder;
        }
    }

    public static final class RtKeyValueBuilder<T>
    {

        private WritableValue<T> target;
        private Supplier<WritableValue<T>> targetSupplier;
        private Supplier<T> endValueSupplier;
        private T endValue;
        private Interpolator interpolator = Interpolator.EASE_BOTH;
        private Supplier<Boolean> animateCondition = () -> true;

        private RtKeyValueBuilder()
        {
        }

        public RtKeyValueBuilder<T> setTarget(WritableValue<T> target)
        {
            this.target = target;
            return this;
        }

        public RtKeyValueBuilder<T> setTargetSupplier(Supplier<WritableValue<T>> targetSupplier)
        {
            this.targetSupplier = targetSupplier;
            return this;
        }

        public RtKeyValueBuilder<T> setEndValue(T endValue)
        {
            this.endValue = endValue;
            return this;
        }

        public RtKeyValueBuilder<T> setEndValueSupplier(Supplier<T> endValueSupplier)
        {
            this.endValueSupplier = endValueSupplier;
            return this;
        }

        public RtKeyValueBuilder<T> setAnimateCondition(Supplier<Boolean> animateCondition)
        {
            this.animateCondition = animateCondition;
            return this;
        }

        public RtKeyValueBuilder<T> setInterpolator(Interpolator interpolator)
        {
            this.interpolator = interpolator;
            return this;
        }

        public RtKeyValue<T> build()
        {
            RtKeyValue<T> keyValue = new RtKeyValue<>();
            keyValue.target = this.target;
            keyValue.targetSupplier = this.targetSupplier;
            keyValue.endValue = this.endValue;
            keyValue.endValueSupplier = this.endValueSupplier;
            keyValue.interpolator = this.interpolator;
            keyValue.animateCondition = this.animateCondition;
            return keyValue;
        }
    }
}