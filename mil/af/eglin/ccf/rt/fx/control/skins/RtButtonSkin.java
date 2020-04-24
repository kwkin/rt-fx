package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.animations.CachedTransition;

public class RtButtonSkin extends ButtonSkin
{
    private final GridPane graphicTextPane = new GridPane();
    private final StackPane armedBox = new StackPane();
    private final StackPane hoverBox = new StackPane();
    
    private ButtonTransition armedTransition;
    private ButtonTransition hoverTransition;
    
    public RtButtonSkin(Button button)
    {
        super(button);

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> playArmedAnimation(true));
        button.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> playArmedAnimation(false));
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> playHoverAnimation(true));
        button.addEventFilter(MouseEvent.MOUSE_EXITED, e -> playHoverAnimation(false));

        armedBox.getStyleClass().setAll("armedBox");
        armedBox.setOpacity(0);
        hoverBox.getStyleClass().setAll("hoverBox");
        hoverBox.setOpacity(0);

        double duration = button.getIsAnimationDisabled() ? 0 : 150;
        armedTransition = new ButtonTransition(armedBox, Duration.millis(duration));
        hoverTransition = new ButtonTransition(hoverBox, Duration.millis(duration));
        
        Node text = getSkinnable().lookup(".text");
        int index = getChildren().indexOf(text);
        index = index == -1 ? getChildren().size() - 1 : index;
        if (hoverBox != null)
        {
            getChildren().add(index, hoverBox);
        }
        if (armedBox != null)
        {
            getChildren().add(index, armedBox);
        }
//        Node graphic = button.getGraphic();
//        Node textComponent = button.lookup(".text");
//        if (graphic != null)
//        {
//            getChildren().remove(graphic);
//            graphicTextPane.add(graphic, 0, 0);
//        }
//        if (textComponent != null)
//        {
//            getChildren().remove(textComponent);
//            graphicTextPane.add(textComponent, 0, 1);
//        }
//        getChildren().add(graphicTextPane);
    }


    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        // @formatter:off
        armedBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        
        hoverBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        // @formatter:on
        
        layoutLabelInArea(x, y, w, h);
    }

    private void playArmedAnimation(boolean isArmed) 
    {
        if (armedTransition != null) 
        {
            if (isArmed)
            {
                armedTransition.setRate(1);
                armedTransition.play();
            }
            else
            {
                armedTransition.setRate(-1);
                armedTransition.playFrom(Duration.ZERO);
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

    private final static class ButtonTransition extends CachedTransition
    {
        protected Node node;

        ButtonTransition(Node node, Duration duration)
        {
            // @formatter:off
            super(null, new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(node.opacityProperty(), 0, Interpolator.EASE_OUT)),
                    new KeyFrame(duration, 
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
