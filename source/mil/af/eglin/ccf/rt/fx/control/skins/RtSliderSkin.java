package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.SliderSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Slider;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

/**
 * A skin for {@link mil.af.eglin.ccf.rt.fx.control.Slider sliders}
 */
public class RtSliderSkin extends SliderSkin
{
    private final Slider slider;
    private final Circle circleThumb = new Circle();
    private final StackPane track;
    private final StackPane filledTrack;
    private final StackPane thumb;
    private final StackPane stateBox = new StackPane();
    
    private RtAnimationTimeline interactionTimeline;
    private RtAnimationTimeline stateTimeline;

    /**
     * Creates a {@code RtSliderSkin} for the provided slider
     * 
     * @param slider the slider that will use this skin
     */
    public RtSliderSkin(final Slider slider)
    {
        super(slider);
        this.slider = slider;
        
        this.circleThumb.getStyleClass().setAll("circle-thumb");
        this.circleThumb.setRadius(8);
        this.circleThumb.setFill(determineThumbColor());
        
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

        registerChangeListener(slider.thumbColorProperty(), slider.thumbColorProperty().getName());
        registerChangeListener(slider.filledTrackColorProperty(), slider.filledTrackColorProperty().getName());
        registerChangeListener(slider.unfilledTrackColorProperty(), slider.unfilledTrackColorProperty().getName());
        registerChangeListener(slider.overlayColorProperty(), slider.overlayColorProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (this.slider.thumbColorProperty().getName().equals(property))
        {
            updateColors();
        }
        else if (this.slider.filledTrackColorProperty().getName().equals(property))
        {
            updateColors();
        }
        else if (this.slider.unfilledTrackColorProperty().getName().equals(property))
        {
            updateColors();
        }
        else if (this.slider.overlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h)
    {
        super.layoutChildren(x, y, w, h);

        double width, height, layoutX, layoutY;
        boolean isHorizontal = getSkinnable().getOrientation() == Orientation.HORIZONTAL;
        if (isHorizontal)
        {
            width = this.thumb.getLayoutX() - snappedLeftInset();
            height = this.track.getHeight();
            layoutX = this.track.getLayoutX();
            layoutY = this.track.getLayoutY();
        } 
        else
        {
            height = this.track.getLayoutBounds().getMaxY() + this.track.getLayoutY() - this.thumb.getLayoutY() - snappedBottomInset();
            width = this.track.getWidth();
            layoutX = this.track.getLayoutX();
            layoutY = this.thumb.getLayoutY();
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
        if (Double.compare(this.slider.getValue(), this.slider.getMin()) <= 0)
        {
            shadow = DepthManager.getInstance().getShadowAt(0);
        }
        else if (this.slider.isPressed())
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
    
    private Paint determineThumbColor()
    {
        Paint thumbColor;
        if (Double.compare(this.slider.getValue(), this.slider.getMin()) <= 0)
        {
            thumbColor = this.slider.getUnfilledTrackColor();
        }
        else
        {
            thumbColor = this.slider.getThumbColor();
        }
        return thumbColor;
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
        this.stateTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(200))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.circleThumb.fillProperty())
                            .setEndValueSupplier(() -> determineThumbColor())
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
        this.slider.valueChangingProperty().addListener((ov, oldVal, newVal) -> 
        {
            this.stateTimeline.reverseAndContinue();
        });
    }

    private void updateColors()
    {
        this.circleThumb.setFill(determineThumbColor());
        Utils.setBackgroundColor(this.track, this.slider.getUnfilledTrackColor());
        Utils.setBackgroundColor(this.filledTrack, this.slider.getFilledTrackColor());
    }

    private void updateStateBoxColor()
    {
        CornerRadii radii = this.slider.getBackground() == null ? null
                : this.slider.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.slider.getOverlayColor(), radii, insets)));
    }
}
