package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

//TODO change select animation to slide from left to right
public class RtCheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>>
{
    private final CheckBox checkBox;
    private final StackPane box = new StackPane();
    private final StackPane coloredBox = new StackPane();
    private final StackPane selectedMark = new StackPane();
    private final StackPane indeterminateMark = new StackPane();
    private final StackPane boxAndMarks = new StackPane();

//    private final StackPane slideTransition = new StackPane();
    
    private RtAnimationTimer selectedTimer;
    private RtAnimationTimer indeterminateTimer;

    public RtCheckBoxSkin(final CheckBox checkBox)
    {
        super(checkBox, new ButtonBehavior<>(checkBox));
        this.checkBox = checkBox;

        this.selectedMark.getStyleClass().setAll("mark");
        this.selectedMark.setOpacity(0);
        this.selectedMark.setScaleX(0);
        this.selectedMark.setScaleY(0);

        this.indeterminateMark.getStyleClass().setAll("indeterminate-mark");
        this.indeterminateMark.setOpacity(0);
        this.indeterminateMark.setScaleX(0);
        this.indeterminateMark.setScaleY(0);

        this.box.getStyleClass().setAll("box");
        this.coloredBox.getStyleClass().setAll("colored-box");
        this.coloredBox.setOpacity(0);
        this.coloredBox.getChildren().addAll(indeterminateMark, selectedMark);
        this.boxAndMarks.getStyleClass().setAll("box-marks");
        this.boxAndMarks.getChildren().addAll(this.box, coloredBox);

        updateChildren();

        // @formatter:off
        selectedTimer = new RtAnimationTimer(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(150))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.coloredBox.opacityProperty())
                            .setEndValueSupplier(() -> computeBoxOpacity())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.selectedMark.opacityProperty())
                            .setEndValueSupplier(() -> computeSelectedOpacity())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.selectedMark.scaleXProperty())
                            .setEndValueSupplier(() -> computeSelectedScale())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.selectedMark.scaleYProperty())
                            .setEndValueSupplier(() -> computeSelectedScale())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build());
        indeterminateTimer = new RtAnimationTimer(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(150))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.coloredBox.opacityProperty())
                            .setEndValueSupplier(() -> computeBoxOpacity())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.indeterminateMark.opacityProperty())
                            .setEndValueSupplier(() -> computeIndeterminateOpacity())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.indeterminateMark.scaleXProperty())
                            .setEndValueSupplier(() -> computeIndeterminateScale())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build(),
                        RtKeyValue.builder()
                            .setTarget(this.indeterminateMark.scaleYProperty())
                            .setEndValueSupplier(() -> computeIndeterminateScale())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build());
        // @formatter:on

        checkBox.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            playSelectAnimation();
        });
        checkBox.indeterminateProperty().addListener((ov, oldVal, newVal) ->
        {
            playIndeterminateAnimation();
        });
        if (checkBox.isIndeterminate())
        {
            indeterminateTimer.applyEndValues();
        }
        else
        {
            selectedTimer.applyEndValues();
        }

        registerChangeListener(checkBox.selectedColorProperty(), checkBox.selectedColorProperty().getName());
        registerChangeListener(checkBox.unselectedColorProperty(), checkBox.unselectedColorProperty().getName());
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (this.boxAndMarks != null)
        {
            getChildren().add(boxAndMarks);
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
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset) + snapSize(box.minWidth(-1));
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return Math.max(super.computeMinHeight(width - box.minWidth(-1), topInset, rightInset, bottomInset, leftInset),
                topInset + box.minHeight(-1) + bottomInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset)
                + snapSize(box.prefWidth(-1));
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labelHeight = super.computePrefHeight(width - box.prefWidth(-1), topInset, rightInset, bottomInset, leftInset);
        double boxHeight = topInset + box.prefHeight(-1) + bottomInset;
        return Math.max(labelHeight, boxHeight);
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
//        final double boxWidth = snapSize(box.prefWidth(-1));
//        final double boxHeight = snapSize(box.prefHeight(-1));
//        final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
//        final double labelWidth = Math.min(computeWidth - boxWidth, w - snapSize(boxWidth));
//        final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
//        final double maxHeight = Math.max(boxHeight, labelHeight);
//        final double xOffset = Utils.computeXOffset(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x;
//        final double yOffset = Utils.computeYOffset(h, maxHeight, checkBox.getAlignment().getVpos()) + x;
//
//        layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
//        boxAndMarks.resize(boxWidth, boxHeight);
//        positionInArea(boxAndMarks, xOffset, yOffset, boxWidth, maxHeight, 0, checkBox.getAlignment().getHpos(),
//                checkBox.getAlignment().getVpos());
        
        final double boxWidth = snapSize(box.prefWidth(-1));
        final double boxHeight = snapSize(box.prefHeight(-1));
        final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
        final double labelWidth = Math.min(computeWidth - boxWidth, w - snapSize(boxWidth));
        final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(boxHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x;
        final double yOffset = Utils.computeYOffset(checkBox.getHeight(), maxHeight, checkBox.getAlignment().getVpos());

        layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
        boxAndMarks.resize(boxWidth, boxHeight);
        positionInArea(boxAndMarks, xOffset, yOffset, boxWidth, maxHeight, 0, checkBox.getAlignment().getHpos(),
                checkBox.getAlignment().getVpos());

    }

    private void playSelectAnimation()
    {
        if (!this.checkBox.getIsAnimationDisabled())
        {
            selectedTimer.start();
        }
        else
        {
            selectedTimer.applyEndValues();
        }

    }

    private void playIndeterminateAnimation()
    {
        if (!this.checkBox.getIsAnimationDisabled())
        {
            indeterminateTimer.start();
        }
        else
        {
            indeterminateTimer.applyEndValues();
        }
    }

    private double computeBoxOpacity()
    {
        double opacity = 0;
        if (this.checkBox.isSelected() || this.checkBox.isIndeterminate())
        {
            opacity = 1;
        }
        else
        {
            opacity = 0;
        }
        return opacity;
    }

    private double computeSelectedOpacity()
    {
        double opacity = 0;
        if (this.checkBox.isSelected() && !this.checkBox.isIndeterminate())
        {
            opacity = 1;
        }
        else
        {
            opacity = 0;
        }
        return opacity;
    }

    private double computeSelectedScale()
    {
        double scale = 0;
        if (this.checkBox.isSelected() && !this.checkBox.isIndeterminate())
        {
            scale = 1;
        }
        else
        {
            scale = 0;
        }
        return scale;
    }

    private double computeIndeterminateOpacity()
    {
        double opacity = 0;
        if (this.checkBox.isIndeterminate())
        {
            opacity = 1;
        }
        else
        {
            opacity = 0;
        }
        return opacity;
    }

    private double computeIndeterminateScale()
    {
        double scale = 0;
        if (this.checkBox.isIndeterminate())
        {
            scale = 1;
        }
        else
        {
            scale = 0;
        }
        return scale;
    }
}