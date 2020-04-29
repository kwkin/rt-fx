package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.animations.CachedTransition;
import mil.af.eglin.ccf.rt.fx.control.animations.RtFillTransition;
import mil.af.eglin.ccf.rt.fx.utils.JFXNodeUtils;

public class RtCheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>>
{
    private final StackPane box = new StackPane();
    private final StackPane selectedMark = new StackPane();
    private final StackPane indeterminateMark = new StackPane();
    private final StackPane overallBox = new StackPane();

    private CheckBox checkBox;
    
    // TODO change to RtAnimationTimer
    private Transition transition;
    private Transition indeterminateTransition;
    private RtFillTransition select;

    public RtCheckBoxSkin(CheckBox checkBox)
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

        box.getStyleClass().setAll("box");
        box.getChildren().setAll(indeterminateMark, selectedMark);
        
        overallBox.getChildren().add(box);
        
        checkBox.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            playSelectAnimation(checkBox.isSelected(), true);
        });
        checkBox.indeterminateProperty().addListener((ov, oldVal, newVal) ->
        {
            playIndeterminateAnimation(checkBox.isIndeterminate(), true);
        });
        updateChildren();

        transition = new CheckBoxTransition(selectedMark);
        indeterminateTransition = new CheckBoxTransition(indeterminateMark);
        createFillTransition();
        
        // TODO register change listeners
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (overallBox != null)
        {
            getChildren().add(overallBox);
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
        return Math.max(
                super.computePrefHeight(width - box.prefWidth(-1), topInset, rightInset, bottomInset, leftInset),
                topInset + box.prefHeight(-1) + bottomInset);
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        final javafx.scene.control.CheckBox checkBox = getSkinnable();
        final double boxWidth = snapSize(box.prefWidth(-1));
        final double boxHeight = snapSize(box.prefHeight(-1));
        final double computeWidth = Math.max(checkBox.prefWidth(-1), checkBox.minWidth(-1));
        final double labelWidth = Math.min( computeWidth - boxWidth, w - snapSize(boxWidth));
        final double labelHeight = Math.min(checkBox.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(boxHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + boxWidth, checkBox.getAlignment().getHpos()) + x;
        final double yOffset = Utils.computeYOffset(h, maxHeight, checkBox.getAlignment().getVpos()) + x;

        if (checkBox.isIndeterminate())
        {
            playIndeterminateAnimation(true, false);
        } 
        else if (checkBox.isSelected())
        {
            playSelectAnimation(true, false);
        }

        layoutLabelInArea(xOffset + boxWidth, yOffset, labelWidth, maxHeight, checkBox.getAlignment());
        overallBox.resize(boxWidth, boxHeight);
        positionInArea(overallBox, xOffset, yOffset, boxWidth, maxHeight, 0, checkBox.getAlignment().getHpos(), checkBox.getAlignment().getVpos());

    }

    private void playSelectAnimation(Boolean selection, boolean playAnimation)
    {
        if (selection == null)
        {
            selection = false;
        }
        transition.setRate(selection ? 1 : -1);
        select.setRate(selection ? 1 : -1);
        if (playAnimation)
        {
            transition.play();
            select.play();
        } 
        else
        {
            CornerRadii radii = box.getBackground() == null ? null : box.getBackground().getFills().get(0).getRadii();
            Insets insets = box.getBackground() == null ? null : box.getBackground().getFills().get(0).getInsets();
            if (selection)
            {
                selectedMark.setScaleY(1);
                selectedMark.setScaleX(1);
                selectedMark.setOpacity(1);
                box.setBackground(new Background(new BackgroundFill(getSkinnable().getSelectedColor(), radii, insets)));
                select.playFrom(select.getCycleDuration());
                transition.playFrom(transition.getCycleDuration());
            } 
            else
            {
                selectedMark.setScaleY(0);
                selectedMark.setScaleX(0);
                selectedMark.setOpacity(0);
                box.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, radii, insets)));
                select.playFrom(Duration.ZERO);
                transition.playFrom(Duration.ZERO);
            }
        }
        box.setBorder(new Border(
                new BorderStroke(selection ? getSkinnable().getSelectedColor() : getSkinnable().getUnselectedColor(),
                        BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));
    }

    private void playIndeterminateAnimation(Boolean indeterminate, boolean playAnimation)
    {
        if (indeterminate == null)
        {
            indeterminate = false;
        }
        indeterminateTransition.setRate(indeterminate ? 1 : -1);
        if (playAnimation)
        {
            indeterminateTransition.play();
        } else
        {
            if (indeterminate)
            {
                CornerRadii radii = indeterminateMark.getBackground() == null ? null
                        : indeterminateMark.getBackground().getFills().get(0).getRadii();
                Insets insets = indeterminateMark.getBackground() == null ? null
                        : indeterminateMark.getBackground().getFills().get(0).getInsets();
                indeterminateMark.setOpacity(1);
                indeterminateMark.setScaleY(1);
                indeterminateMark.setScaleX(1);
                indeterminateMark.setBackground(
                        new Background(new BackgroundFill(getSkinnable().getSelectedColor(), radii, insets)));
                indeterminateTransition.playFrom(indeterminateTransition.getCycleDuration());
            } else
            {
                indeterminateMark.setOpacity(0);
                indeterminateMark.setScaleY(0);
                indeterminateMark.setScaleX(0);
                indeterminateTransition.playFrom(Duration.ZERO);
            }
        }

        if (getSkinnable().isSelected())
        {
            playSelectAnimation(!indeterminate, playAnimation);
        }
    }

    private void createFillTransition()
    {
        select = new RtFillTransition(Duration.millis(120), box, Color.TRANSPARENT,
                (Color) getSkinnable().getSelectedColor());
        select.setInterpolator(Interpolator.EASE_OUT);
    }

    private final static class CheckBoxTransition extends CachedTransition
    {
        protected Node mark;

        CheckBoxTransition(Node mark)
        {
            // @formatter:off
            super(null, new Timeline(
                    new KeyFrame(Duration.ZERO, 
                            new KeyValue(mark.opacityProperty(), 0, Interpolator.EASE_OUT),
                            new KeyValue(mark.scaleXProperty(), 0.5, Interpolator.EASE_OUT),
                            new KeyValue(mark.scaleYProperty(), 0.5, Interpolator.EASE_OUT)),
                    new KeyFrame(Duration.millis(400), 
                            new KeyValue(mark.opacityProperty(), 1, Interpolator.EASE_OUT),
                            new KeyValue(mark.scaleXProperty(), 0.5, Interpolator.EASE_OUT),
                            new KeyValue(mark.scaleYProperty(), 0.5, Interpolator.EASE_OUT)),
                    new KeyFrame(Duration.millis(1000), 
                            new KeyValue(mark.scaleXProperty(), 1, Interpolator.EASE_OUT),
                            new KeyValue(mark.scaleYProperty(), 1, Interpolator.EASE_OUT))));
            // @formatter:on
            setCycleDuration(Duration.seconds(0.12));
            setDelay(Duration.seconds(0.05));
            this.mark = mark;
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
            mark.setOpacity(getRate() == 1 ? 1 : 0);
        }
    }
}