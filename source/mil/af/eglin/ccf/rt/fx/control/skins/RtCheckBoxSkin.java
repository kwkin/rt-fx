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
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
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

    private RtAnimationTimer unselectedTimer;
    private RtAnimationTimer selectedTimer;
    private RtAnimationTimer indeterminateToSelectedTimer;
    private RtAnimationTimer stateTimer;
    
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
        
        updateChildren();
        createAnimation();
        
        if (this.checkBox.isIndeterminate() || this.checkBox.isSelected())
        {
            selectedTimer.applyEndValues();
        }

        checkBox.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            playSelectAnimation();
        });
        checkBox.indeterminateProperty().addListener((ov, oldVal, newVal) ->
        {
            playIndeterminateAnimation();
        });
        checkBox.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            playStateAnimation();
        });
        checkBox.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            playStateAnimation();
        });

        registerChangeListener(checkBox.selectedColorProperty(), checkBox.selectedColorProperty().getName());
        registerChangeListener(checkBox.unselectedColorProperty(), checkBox.unselectedColorProperty().getName());
        registerChangeListener(checkBox.getOverlayColorProperty(), checkBox.getOverlayColorProperty().getName());
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (this.boxAndMarks != null)
        {
            getChildren().add(this.boxAndMarks);
        }
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (checkBox.selectedColorProperty().getName().equals(property))
        {
            // TODO implement
        }
        else if (checkBox.unselectedColorProperty().getName().equals(property))
        {
            // TODO implement
        }
        else if (checkBox.getOverlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(boxAndMarks.minWidth(-1));
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return Math.max(super.computeMinHeight(width - boxAndMarks.minWidth(-1), topInset, rightInset, bottomInset, leftInset),
                topInset + boxAndMarks.minHeight(-1) + bottomInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset)
                + snapSize(boxAndMarks.prefWidth(-1));
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labelHeight = super.computePrefHeight(width - boxAndMarks.prefWidth(-1), topInset, rightInset, bottomInset, leftInset);
        double boxHeight = topInset + boxAndMarks.prefHeight(-1) + bottomInset;
        return Math.max(labelHeight, boxHeight);
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        final double boxWidth = snapSize(boxAndMarks.prefWidth(-1));
        final double boxHeight = snapSize(boxAndMarks.prefHeight(-1));
        final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
        final double labelWidth = Math.min(computeWidth - boxWidth, w - snapSize(boxWidth));
        final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(boxHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x;
        final double yOffset = Utils.computeYOffset(checkBox.getHeight(), maxHeight, checkBox.getAlignment().getVpos());

        layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
        boxAndMarks.resize(boxWidth, boxHeight);
        positionInArea(boxAndMarks, xOffset, yOffset, boxWidth, boxHeight, 0, checkBox.getAlignment().getHpos(),
                checkBox.getAlignment().getVpos());

    }

    private void playSelectAnimation()
    {
        if (!this.checkBox.isIndeterminate())
        {
            if (!this.checkBox.getIsAnimationDisabled())
            {
                if (this.checkBox.isSelected())
                {
                    this.selectedTimer.start();
                }
                else
                {
                    this.unselectedTimer.start();
                }
            }
            else
            {
                if (this.checkBox.isSelected())
                {
                    this.selectedTimer.applyEndValues();
                }
                else
                {
                    this.unselectedTimer.applyEndValues();
                }
            }
        }
    }

    private void playIndeterminateAnimation()
    {
        if (!this.checkBox.getIsAnimationDisabled())
        {
            if (this.checkBox.isIndeterminate())
            {
                this.selectedTimer.start();
            }
            else
            {
                this.indeterminateToSelectedTimer.start();
            }
        }
        else
        {
            if (this.checkBox.isIndeterminate())
            {
                this.selectedTimer.applyEndValues();
            }
            else
            {
                this.indeterminateToSelectedTimer.applyEndValues();
            }
        }
    }

    private void playStateAnimation()
    {
        if (!this.checkBox.getIsAnimationDisabled())
        {
            this.stateTimer.start();
        }
        else
        {
            this.stateTimer.applyEndValues();
        }
    }

    private StackPane computeMark()
    {
        StackPane mark = null;
        if (this.checkBox.isIndeterminate())
        {
            mark = this.indeterminateMark;
        }
        else if (this.checkBox.isSelected())
        {
            mark = this.selectedMark;
        }
        return mark;
    }
    
    private void createAnimation()
    {
        // @formatter:off 
        unselectedTimer = new RtAnimationTimer( 
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
        selectedTimer = new RtAnimationTimer(
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
        indeterminateToSelectedTimer = new RtAnimationTimer(
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
        stateTimer = new RtAnimationTimer(
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
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.checkBox.getBackground() == null ? null : this.checkBox.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.checkBox.getOverlayColor(), radii, insets)));
    }
}