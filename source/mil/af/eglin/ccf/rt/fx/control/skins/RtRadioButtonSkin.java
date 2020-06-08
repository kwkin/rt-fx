package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.RadioButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

// TODO change usage of shapes (circle) to stack pane
public class RtRadioButtonSkin extends RadioButtonSkin
{
    private final RadioButton radioButton;
    private final Circle radio = new Circle();
    private final Circle dot = new Circle();
    private final StackPane container = new StackPane();
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimeline stateTimeline;
    private RtAnimationTimeline interactionTimeline;

    public RtRadioButtonSkin(final RadioButton radioButton)
    {
        super(radioButton);
        this.radioButton = radioButton;

        double radioRadius = 8;
        this.radio.setRadius(radioRadius);
        this.radio.getStyleClass().setAll("radio");
        this.radio.strokeProperty().setValue(determineColor(this.radioButton.isSelected()));
        this.radio.setStrokeWidth(2);
        this.radio.setFill(Color.TRANSPARENT);
        this.radio.setSmooth(true);

        this.dot.setRadius(radioRadius);
        this.dot.getStyleClass().setAll("dot");
        this.dot.fillProperty().setValue(radioButton.getSelectedColor());
        this.dot.setOpacity(0);
        this.dot.setScaleX(0);
        this.dot.setScaleY(0);
        this.dot.setSmooth(true);

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        Rectangle slideClip = new Rectangle();
        slideClip.widthProperty().bind(this.container.widthProperty());
        slideClip.heightProperty().bind(this.container.heightProperty());
        this.stateBox.setClip(slideClip);
        slideClip.setArcWidth(100);
        slideClip.setArcHeight(100);
        slideClip.setSmooth(true);
        updateStateBoxColor();

        this.container.getStyleClass().add("radio-container");
        this.container.getChildren().addAll(this.radio, this.dot, this.stateBox);
        updateChildren();

        createAnimation();
        createAnimationListeners();
        stateTimeline.applyEndValues();

        registerChangeListener(radioButton.selectedColorProperty(), radioButton.selectedColorProperty().getName());
        registerChangeListener(radioButton.unselectedColorProperty(), radioButton.unselectedColorProperty().getName());
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (container != null)
        {
            removeRadio();
            getChildren().add(container);
        }
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (radioButton.selectedColorProperty().getName().equals(property))
        {
            Paint paint = determineColor(this.radioButton.isSelected());
            if (paint != null)
            {
                radio.setStroke(paint);
            }
            this.dot.setFill(this.radioButton.getSelectedColor());
        }
        else if (radioButton.unselectedColorProperty().getName().equals(property))
        {
            Paint paint = determineColor(this.radioButton.isSelected());
            if (paint != null)
            {
                radio.setStroke(paint);
            }
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double radioWidth = snapSize(radio.prefWidth(-1));
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) + radioWidth;
    }

    private Paint determineColor(boolean isSelected)
    {
        return isSelected ? this.radioButton.getSelectedColor() : this.radioButton.getUnselectedColor();
    }

    private double determineSize(boolean isSelected)
    {
        return isSelected ? 0.55 : 0;
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        final javafx.scene.control.RadioButton radioButton = this.radioButton;
        final double contWidth = snapSize(container.prefWidth(-1));
        final double contHeight = snapSize(container.prefHeight(-1));
        final double computeWidth = Math.max(radioButton.prefWidth(-1), radioButton.minWidth(-1));
        final double width = snapSize(contWidth);
        final double height = snapSize(contHeight);
        final double labelWidth = Math.min(computeWidth - contWidth, w - width);
        final double labelHeight = Math.min(radioButton.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(contHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + contWidth, radioButton.getAlignment().getHpos())
                + x;
        final double yOffset = Utils.computeYOffset(h, maxHeight, radioButton.getAlignment().getVpos()) + x;

        layoutLabelInArea(xOffset + contWidth, yOffset, labelWidth, maxHeight, radioButton.getAlignment());
        this.container.resize(width, height);
        positionInArea(container, xOffset, yOffset, contWidth, maxHeight, 0, radioButton.getAlignment().getHpos(),
                radioButton.getAlignment().getVpos());
    }

    private void playStateAnimation()
    {
        if (!this.radioButton.getIsAnimationDisabled())
        {
            this.interactionTimeline.start();
        }
        else
        {
            this.interactionTimeline.applyEndValues();
        }
    }

    private void removeRadio()
    {
        for (int i = 0; i < getChildren().size(); i++)
        {
            if (getChildren().get(i).getStyleClass().contains("radio"))
            {
                getChildren().remove(i);
                break;
            }
        }
    }
    
    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.radioButton.isArmed())
        {
            opacity = 1;
        }
        else if (this.radioButton.isHover())
        {
            opacity = 0.6;
        }
        return opacity;
    }
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.radioButton.getBackground() == null ? null : this.radioButton.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.radioButton.getOverlayColor(), radii, insets)));
    }
    
    private void createAnimation()
    {
        // @formatter:off
        this.stateTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(150))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.radio.strokeProperty())
                        .setEndValueSupplier(() -> determineColor(this.radioButton.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.opacityProperty())
                        .setEndValueSupplier(() -> this.radioButton.isSelected() ? 1 : 0)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.scaleXProperty())
                        .setEndValueSupplier(() -> determineSize(this.radioButton.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.scaleYProperty())
                        .setEndValueSupplier(() -> determineSize(this.radioButton.isSelected()))
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
        this.stateTimeline.setAnimateCondition(() -> !this.radioButton.getIsAnimationDisabled());
        this.interactionTimeline.setAnimateCondition(() -> !this.radioButton.getIsAnimationDisabled());
    }

    private void createAnimationListeners()
    {
        this.radioButton.selectedProperty().addListener(observable ->
        {
            if (!this.radioButton.getIsAnimationDisabled())
            {
                this.stateTimeline.reverseAndContinue();
            }
            else
            {
                this.stateTimeline.applyEndValues();
            }
        });
        this.radioButton.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            playStateAnimation();
        });
        this.radioButton.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            playStateAnimation();
        });
    }
}
