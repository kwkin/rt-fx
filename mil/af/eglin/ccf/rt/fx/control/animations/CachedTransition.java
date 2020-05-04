package mil.af.eglin.ccf.rt.fx.control.animations;

import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.util.Duration;

public class CachedTransition extends Transition
{
    protected Node node;
    protected ObjectProperty<Timeline> timeline = new SimpleObjectProperty<>();

    private CachedFrame[] momentos = new CachedFrame[0];

    public CachedTransition(final Node node, final Timeline timeline)
    {
        this.node = node;
        this.timeline.set(timeline);

        this.momentos = node == null ? this.momentos : new CachedFrame[]
        { new CachedFrame(node) };
        initialize();
    }

    protected void beginStart()
    {
        if (this.momentos != null)
        {
            for (CachedFrame momento : this.momentos)
            {
                momento.cache();
            }
        }
    }

    protected void beginStop()
    {
        if (this.momentos != null)
        {
            for (CachedFrame momento : this.momentos)
            {
                momento.restore();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interpolate(double durationSeconds)
    {
        this.timeline.get().playFrom(Duration.seconds(durationSeconds));
        this.timeline.get().stop();
    }

    private void initialize()
    {
        statusProperty().addListener(observable ->
        {
            switch (getStatus())
            {
                case RUNNING:
                    beginStart();
                    break;
                case PAUSED:
                case STOPPED:
                default:
                    beginStop();
                    break;
            }
        });
    }
}