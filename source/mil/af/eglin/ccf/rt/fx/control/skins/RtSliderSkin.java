package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.SliderSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Slider;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

// TODO bind colors to rt style properties
public class RtSliderSkin extends SliderSkin
{
    private final Slider slider;
    private final Circle circleThumb = new Circle();
    private final StackPane track;
    private final StackPane filledTrack;
    private final StackPane thumb;
    private final StackPane stateBox = new StackPane();
    
    private RtAnimationTimeline interactionTimeline;

    public RtSliderSkin(final Slider slider)
    {
        super(slider);
        this.slider = slider;
        
        this.circleThumb.getStyleClass().setAll("circle-thumb");
        this.circleThumb.setRadius(8);
        
        this.track = (StackPane) slider.lookup(".track");
        this.thumb = (StackPane) slider.lookup(".thumb");
        this.thumb.setPickOnBounds(false);
        DepthManager.getInstance().setDepth(this.thumb, 2);

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        Rectangle slideClip = new Rectangle();
        slideClip.widthProperty().bind(this.thumb.widthProperty());
        slideClip.heightProperty().bind(this.thumb.heightProperty());
        this.stateBox.setClip(slideClip);
        slideClip.setArcWidth(100);
        slideClip.setArcHeight(100);
        slideClip.setSmooth(true);
        updateStateBoxColor();
        this.thumb.getChildren().addAll(this.circleThumb, this.stateBox);

        this.filledTrack = new StackPane();
        this.filledTrack.getStyleClass().add("colored-track");
        this.filledTrack.setMouseTransparent(true);

        int insertIndex = getChildren().indexOf(this.thumb);
        getChildren().add(insertIndex, this.filledTrack);
        
        slider.setPickOnBounds(false);
        
        createAnimation();
        createAnimationListeners();
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

        this.filledTrack.resizeRelocate(layoutX, layoutY, width, height);
    }
    
    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.slider.isPressed())
        {
            opacity = 1;
        }
        else if (this.slider.isHover())
        {
            opacity = 0.6;
        }
        return opacity;
    }

    private DepthShadow determineButtonShadow()
    {
        DepthShadow shadow;
        if (this.slider.isPressed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (this.slider.isHover())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
        }
        else
        {
            shadow = DepthManager.getInstance().getShadowAt(2);
        }
        return shadow;
    }

    private void createAnimation()
    {
        // @formatter:off
        this.interactionTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(200))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineStateBoxOpacity())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.thumb.effectProperty())
                        .setEndValueSupplier(() -> determineButtonShadow())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
        // @formatter:on
        this.interactionTimeline.setAnimateCondition(() -> !this.slider.getIsAnimationDisabled());
    }

    private void createAnimationListeners()
    {
        this.slider.pressedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (oldVal)
            {
                this.interactionTimeline.skipAndContinue();
            }
            else
            {
                this.interactionTimeline.start();
            }
        });
        this.thumb.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.interactionTimeline.start();
        });
    }

    private void updateStateBoxColor()
    {
        CornerRadii radii = this.slider.getBackground() == null ? null
                : this.slider.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.slider.getOverlayColor(), radii, insets)));
    }
}
