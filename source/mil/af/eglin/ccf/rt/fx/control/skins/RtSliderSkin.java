package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.SliderSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Orientation;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Slider;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

// TODO bind colors to rt style properties
public class RtSliderSkin extends SliderSkin
{
    private Slider slider;
    
    private Circle stateThumb = new Circle();
    private Circle circleThumb = new Circle();
    
    private StackPane coloredTrack;
    private StackPane thumb;
    private StackPane track;
    private RtAnimationTimer timer;

    public RtSliderSkin(final Slider slider)
    {
        super(slider);
        this.slider = slider;

        stateThumb.getStyleClass().setAll("state-thumb");
        stateThumb.setRadius(0);
        circleThumb.getStyleClass().setAll("circle-thumb");
        circleThumb.setRadius(8);
        
        track = (StackPane) getSkinnable().lookup(".track");
        thumb = (StackPane) getSkinnable().lookup(".thumb");
        thumb.getChildren().addAll(stateThumb, circleThumb);

        coloredTrack = new StackPane();
        coloredTrack.getStyleClass().add("colored-track");
        coloredTrack.setMouseTransparent(true);


        slider.setPickOnBounds(false);
        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(circleThumb.radiusProperty())
                        .setEndValueSupplier(() -> determineThumbRadius())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !slider.getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(stateThumb.radiusProperty())
                        .setEndValueSupplier(() -> determineStateRadius())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !slider.getIsAnimationDisabled())
                        .build())
                .build());
        timer.setCacheNodes(stateThumb);

        
        thumb.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
        slider.pressedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
        
        getChildren().add(getChildren().indexOf(thumb), coloredTrack);
//        getChildren().add(getChildren().indexOf(thumb), stateThumb);
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h)
    {
        super.layoutChildren(x, y, w, h);

        double width, height, layoutX, layoutY;
        boolean isHorizontal = getSkinnable().getOrientation() == Orientation.HORIZONTAL;
        if (isHorizontal)
        {
            width = thumb.getLayoutX() - snappedLeftInset();
            height = track.getHeight();
            layoutX = track.getLayoutX();
            layoutY = track.getLayoutY();
        } 
        else
        {
            height = track.getLayoutBounds().getMaxY() + track.getLayoutY() - thumb.getLayoutY() - snappedBottomInset();
            width = track.getWidth();
            layoutX = track.getLayoutX();
            layoutY = thumb.getLayoutY();
        }
        
        stateThumb.setCenterX(thumb.getLayoutX() + thumb.getWidth() / 2);
        stateThumb.setCenterY(thumb.getLayoutY() + thumb.getHeight() / 2);

        coloredTrack.resizeRelocate(layoutX, layoutY, width, height);
    }
    
    private void updateState()
    {
        if (!slider.getIsAnimationDisabled())
        {
            timer.reverseAndContinue();
        }
        else
        {
            timer.applyEndValues();
        }
    }

    private double determineStateRadius() 
    {
        double radius = 0;
        if (slider.isPressed() || thumb.isHover())
        {
            radius = 12;
        }
        return radius;
    }

    private double determineThumbRadius() 
    {
        double radius = 8;
        if (slider.isPressed())
        {
            radius = 9;
        }
        return radius;
    }
}