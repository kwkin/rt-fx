package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class RtFillTransition extends Transition
{
    private static final Duration DEFAULT_DURATION = Duration.millis(400);
    private static final Color DEFAULT_TO_VALUE = null;
    private static final Color DEFAULT_FROM_VALUE = null;

    private Color start;
    private Color end;
    private CacheHint oldCacheHint = CacheHint.DEFAULT;
    private boolean oldCache = false;
    private ObjectProperty<Duration> duration;
    private ObjectProperty<Region> region;
    private ObjectProperty<Color> toValue;
    private ObjectProperty<Color> fromValue;
    private CornerRadii radii;
    private Insets insets;

    public RtFillTransition(Duration duration, Region region, Color fromValue, Color toValue)
    {
        this.region = new SimpleObjectProperty<>(this, "region", null);
        this.duration = createDurationProperty();
        this.fromValue = new SimpleObjectProperty<>(this, "fromValue", DEFAULT_FROM_VALUE);
        this.toValue = new SimpleObjectProperty<>(this, "toValue", DEFAULT_TO_VALUE);
        
        setDuration(duration);
        setRegion(region);
        setFromValue(fromValue);
        setToValue(toValue);
        setCycleDuration(duration);
        statusProperty().addListener(new ChangeListener<Status>()
        {
            @Override
            public void changed(ObservableValue<? extends Status> ov, Status t, Status newStatus)
            {
                switch (newStatus)
                {
                    case RUNNING:
                        starting();
                        break;
                    default:
                        stopping();
                        break;
                }
            }
        });
    }

    public RtFillTransition(Duration duration, Color fromValue, Color toValue)
    {
        this(duration, null, fromValue, toValue);
    }

    public RtFillTransition(Duration duration, Region region)
    {
        this(duration, region, null, null);
    }

    public RtFillTransition(Duration duration)
    {
        this(duration, null, null, null);
    }

    public RtFillTransition()
    {
        this(DEFAULT_DURATION, null);
    }
    
    public ObjectProperty<Region> regionProperty()
    {
        return this.region;
    }
    
    public void setRegion(Region value)
    {
        this.region.setValue(value);
    }

    public Region getRegion()
    {
        return this.region.getValue();
    }

    public final ObjectProperty<Duration> durationProperty()
    {
        return this.duration;
    }

    public void setDuration(Duration value)
    {
        this.duration.setValue(value);
    }

    public Duration getDuration()
    {
        return this.duration.getValue();
    }

    public ObjectProperty<Color> fromValueProperty()
    {
        return this.fromValue;
    }

    public void setFromValue(Color value)
    {
        this.fromValue.setValue(value);
    }

    public Color getFromValue()
    {
        return this.fromValue.getValue();
    }

    public ObjectProperty<Color> toValueProperty()
    {
        return this.toValue;
    }

    public void setToValue(Color value)
    {
        this.toValue.set(value);
    }

    public Color getToValue()
    {
        return this.toValue.getValue();
    }

    protected void starting()
    {
        if (this.start == null)
        {
            this.oldCache = this.region.get().isCache();
            this.oldCacheHint = this.region.get().getCacheHint();
            this.start = this.fromValue.get();
            this.end = this.toValue.get();
            if (region.get().getBackground() == null)
            {
                this.radii = null;
                this.insets = null;
            }
            else
            {
                this.radii = this.region.get().getBackground().getFills().get(0).getRadii();
                this.insets = this.region.get().getBackground().getFills().get(0).getInsets();
            }
            this.region.get().setCache(true);
            this.region.get().setCacheHint(CacheHint.SPEED);
        }
    }

    protected void stopping()
    {
        this.region.get().setCache(this.oldCache);
        this.region.get().setCacheHint(this.oldCacheHint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interpolate(double frac)
    {
        if (this.start == null)
        {
            starting();
        }
        Color newColor = this.start.interpolate(this.end, frac);
        if (Color.TRANSPARENT.equals(this.start))
        {
            newColor = new Color(this.end.getRed(), this.end.getGreen(), this.end.getBlue(), newColor.getOpacity());
        }
        this.region.get().setBackground(new Background(new BackgroundFill(newColor, this.radii, this.insets)));
    }
    
    private ObjectProperty<Duration> createDurationProperty()
    {
        ObjectProperty<Duration> durationProperty = new ObjectPropertyBase<Duration>(DEFAULT_DURATION)
        {
            @Override
            public void invalidated()
            {
                try
                {
                    setCycleDuration(getDuration());
                } 
                catch (IllegalArgumentException e)
                {
                    if (isBound())
                    {
                        unbind();
                    }
                    set(getCycleDuration());
                    throw e;
                }
            }

            @Override
            public Object getBean()
            {
                return RtFillTransition.this;
            }

            @Override
            public String getName()
            {
                return "duration";
            }
        };
        return durationProperty;
    }
}