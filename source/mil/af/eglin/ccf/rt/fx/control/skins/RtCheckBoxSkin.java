package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

public class RtCheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>>
{
    private final CheckBox checkBox;
    private final StackPane box = new StackPane();
    private final StackPane coloredBox = new StackPane();
    private final StackPane selectedMark = new StackPane();
    private final StackPane indeterminateMark = new StackPane();
    private final StackPane boxAndMarks = new StackPane();
    private final StackPane stateBox = new StackPane();
    private final StackPane slideTransition = new StackPane();

    private RtAnimationTimeline unselectedTimeline;
    private RtAnimationTimeline selectedTimeline;
    private RtAnimationTimeline indeterminateToSelectedTimeline;
    private RtAnimationTimeline interactionTimeline;
    
    public RtCheckBoxSkin(final CheckBox checkBox)
    {
        super(checkBox, new ButtonBehavior<>(checkBox));
        this.checkBox = checkBox;
        
        this.selectedMark.getStyleClass().setAll("mark");
        this.selectedMark.setOpacity(0);

        this.indeterminateMark.getStyleClass().setAll("indeterminate-mark");
        this.indeterminateMark.setOpacity(0);

        this.box.getStyleClass().setAll("box");
        
        this.coloredBox.getStyleClass().setAll("colored-box");
        this.coloredBox.setOpacity(0);

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        updateStateBoxColor();
        
        this.coloredBox.getChildren().addAll(indeterminateMark, selectedMark, this.slideTransition);
        
        Rectangle slideClip = new Rectangle();
        slideClip.widthProperty().bind(this.coloredBox.widthProperty());
        slideClip.heightProperty().bind(this.coloredBox.heightProperty());
        this.coloredBox.backgroundProperty().addListener((ov, oldVal, newVal) -> 
        {
            CornerRadii radii = CornerRadii.EMPTY;
            if (newVal != null)
            {
                this.slideTransition.backgroundProperty().bind(this.coloredBox.backgroundProperty());
                radii = newVal.getFills().get(0) != null ? newVal.getFills().get(0).getRadii() : CornerRadii.EMPTY;
            }
            slideClip.setArcWidth(radii.getBottomLeftHorizontalRadius() * 2);
            slideClip.setArcHeight(radii.getTopLeftVerticalRadius() * 2);
        });
        this.slideTransition.setOpacity(0);
        this.coloredBox.setClip(slideClip);
        
        this.boxAndMarks.getStyleClass().setAll("box-marks");
        this.boxAndMarks.getChildren().addAll(this.box, this.coloredBox, this.stateBox);
        this.checkBox.setGraphic(this.boxAndMarks);
        
        updateChildren();
        createAnimation();
        createAnimationListeners();
        if (this.checkBox.isIndeterminate() || this.checkBox.isSelected())
        {
            this.selectedTimeline.applyEndValues();
        }
        registerChangeListener(checkBox.selectedColorProperty(), checkBox.selectedColorProperty().getName());
        registerChangeListener(checkBox.unselectedColorProperty(), checkBox.unselectedColorProperty().getName());
        registerChangeListener(checkBox.getOverlayColorProperty(), checkBox.getOverlayColorProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (this.checkBox.selectedColorProperty().getName().equals(property))
        {
            updateColors();
        }
        else if (this.checkBox.unselectedColorProperty().getName().equals(property))
        {
            updateColors();
        }
        else if (this.checkBox.getOverlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
    }

    private StackPane computeMark()
    {
        StackPane mark = null;
        if (this.checkBox.isIndeterminate())
        {
            mark = this.indeterminateMark;
        }
        else
        {
            mark = this.selectedMark;
        }
        return mark;
    }
    
    private void createAnimation()
    {
        // @formatter:off 
        this.unselectedTimeline = new RtAnimationTimeline( 
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.selectedMark.opacityProperty())
                            .setEndValue(0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.indeterminateMark.opacityProperty())
                            .setEndValue(0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build(),
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.coloredBox.opacityProperty())
                            .setEndValue(0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build());
        this.selectedTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.ZERO)
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.slideTransition.translateXProperty())
                            .setEndValue(0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build(),
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.coloredBox.opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.slideTransition.opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTargetSupplier(() -> computeMark().opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build(),
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.slideTransition.translateXProperty())
                            .setEndValue(18)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build());
        this.indeterminateToSelectedTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.ZERO)
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.slideTransition.translateXProperty())
                        .setEndValue(-18)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build(),
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.slideTransition.translateXProperty())
                        .setEndValue(0)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build(),
            RtKeyFrame.builder()
                .setDuration(Duration.ZERO)
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.indeterminateMark.opacityProperty())
                        .setEndValue(0)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.selectedMark.opacityProperty())
                        .setEndValue(1)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build(),
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.slideTransition.translateXProperty())
                        .setEndValue(18)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
        this.interactionTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                            RtKeyValue.builder()
                            .setTarget(this.stateBox.opacityProperty())
                            .setEndValueSupplier(() -> determineStateBoxOpacity())
                            .setInterpolator(Interpolator.EASE_OUT)
                            .build())
                    .build());
        // @formatter:on
        this.unselectedTimeline.setAnimateCondition(() -> !this.checkBox.getIsAnimationDisabled());
        this.selectedTimeline.setAnimateCondition(() -> !this.checkBox.getIsAnimationDisabled());
        this.indeterminateToSelectedTimeline.setAnimateCondition(() -> !this.checkBox.getIsAnimationDisabled());
        this.interactionTimeline.setAnimateCondition(() -> !this.checkBox.getIsAnimationDisabled());
    }
    
    private void createAnimationListeners()
    {
        this.checkBox.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!this.checkBox.isIndeterminate())
            {
                if (this.checkBox.isSelected())
                {
                    this.selectedTimeline.start();
                }
                else
                {
                    this.unselectedTimeline.start();
                }
            }
        });
        this.checkBox.indeterminateProperty().addListener((ov, oldVal, newVal) ->
        {
            if (this.checkBox.isIndeterminate())
            {
                this.selectedTimeline.start();
            }
            else
            {
                this.indeterminateToSelectedTimeline.start();
            }
        });
        this.checkBox.armedProperty().addListener((ov, oldVal, newVal) ->
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
        this.checkBox.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.interactionTimeline.start();
        });
    }
    
    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.checkBox.isArmed())
        {
            opacity = 1;
        }
        else if (this.checkBox.isHover())
        {
            opacity = 0.6;
        }
        return opacity;
    }
    
    private void updateColors()
    {
        Utils.setBackgroundColor(this.coloredBox, this.checkBox.getSelectedColor());
        Utils.setBorderColor(this.box, this.checkBox.getUnselectedColor());
    }
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.checkBox.getBackground() == null ? null : this.checkBox.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.checkBox.getOverlayColor(), radii, insets)));
    }
}