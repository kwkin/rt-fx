package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.CachedTransition;

// TODO change armed to selected
public class RtToggleButtonSkin extends ToggleButtonSkin
{
    private final StackPane selectedBox = new StackPane();
    private final StackPane hoverBox = new StackPane();
    
    private ToggleButtonTransition selectedTransition;
    private ToggleButtonTransition hoverTransition;
    
    public RtToggleButtonSkin(ToggleButton button)
    {
        super(button);

        button.selectedProperty().addListener((ov, oldVal, newVal) -> 
        {
            playSelectedAnimation(newVal);
        });
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> playHoverAnimation(true));
        button.addEventFilter(MouseEvent.MOUSE_EXITED, e -> playHoverAnimation(false));

        selectedBox.getStyleClass().setAll("selectedBox");
        selectedBox.setOpacity(0);
        hoverBox.getStyleClass().setAll("hoverBox");
        hoverBox.setOpacity(0);

        double duration = button.getIsAnimationDisabled() ? 0 : 150;
        selectedTransition = new ToggleButtonTransition(selectedBox, Duration.millis(duration));
        hoverTransition = new ToggleButtonTransition(hoverBox, Duration.millis(duration));

        Node text = button.lookup(".text");
        int index = getChildren().indexOf(text);
        index = index == -1 ? getChildren().size() - 1 : index;
        if (hoverBox != null)
        {
            getChildren().add(index, hoverBox);
        }
        if (selectedBox != null)
        {
            getChildren().add(index, selectedBox);
        }
    }


    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        selectedBox.resizeRelocate(
                getSkinnable().getLayoutBounds().getMinX(),
                getSkinnable().getLayoutBounds().getMinY(),
                getSkinnable().getWidth(), getSkinnable().getHeight());
            
        hoverBox.resizeRelocate(
                getSkinnable().getLayoutBounds().getMinX(),
                getSkinnable().getLayoutBounds().getMinY(),
                getSkinnable().getWidth(), getSkinnable().getHeight());
        
        layoutLabelInArea(x, y, w, h);
    }

    private void playSelectedAnimation(boolean isSelected) 
    {
        if (selectedTransition != null) 
        {
            if (isSelected)
            {
                selectedTransition.setRate(1);
                selectedTransition.play();
            }
            else
            {
                selectedTransition.setRate(-1);
                selectedTransition.playFrom(Duration.ZERO);
            }
        }
    }

    private void playHoverAnimation(boolean isHovered) 
    {
        if (hoverTransition != null) 
        {
            if (isHovered)
            {
                hoverTransition.setRate(1);
                hoverTransition.play();
            }
            else
            {
                hoverTransition.setRate(-1);
                hoverTransition.playFrom(Duration.ZERO);
            }
        }
    }

    private final static class ToggleButtonTransition extends CachedTransition
    {
        protected Node node;

        ToggleButtonTransition(Node node, Duration duration)
        {
            // @formatter:off
            super(null, new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(node.opacityProperty(), 0, Interpolator.EASE_OUT)),
                    new KeyFrame(Duration.millis(500), 
                            new KeyValue(node.opacityProperty(), 1, Interpolator.EASE_OUT))));
            // @formatter:on
            if (duration == Duration.ZERO)
            {
                setCycleDuration(Duration.millis(1));
                setCycleCount(1);
            }
            else
            {
                setCycleDuration(Duration.seconds(0.2));
            }
            setDelay(Duration.seconds(0));
            this.node = node;
        }

        @Override
        protected void beginStart()
        {
            super.beginStart();
        }

        @Override
        protected void beginStop()
        {
            super.beginStop();
            node.setOpacity(getRate() == 1 ? 1 : 0);
        }
    }
}
