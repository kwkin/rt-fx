package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

/**
 * A skin for {@link mil.af.eglin.ccf.rt.fx.control.ToggleSwitch toggle switches}
 */
public class RtToggleSwitchSkin extends LabeledSkinBase<ToggleSwitch, ButtonBehavior<ToggleSwitch>>
{
    private final ToggleSwitch toggleSwitch;
    private final StackPane main = new StackPane();
    private final StackPane circlePane = new StackPane();
    private final Circle circle = new Circle();
    private final Line line = new Line();
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimeline stateTimeline;
    private RtAnimationTimeline interactionTimeline;

    /**
     * Creates a {@code RtToggleSwitchSkin} for the provided toggle switch
     * 
     * @param toggleSwitch the toggle switch that will use this skin
     */
    public RtToggleSwitchSkin(final ToggleSwitch toggleSwitch) 
    {
        super(toggleSwitch, new ButtonBehavior<>(toggleSwitch));
        this.toggleSwitch = toggleSwitch;
        
        this.line.getStyleClass().add("line");
        this.line.setStartX(0);
        this.line.setStartY(0);
        this.line.setEndY(0);
        this.line.setStrokeLineCap(StrokeLineCap.ROUND);
        this.line.setSmooth(true);

        this.circle.getStyleClass().add("circle");
        this.circle.setCenterY(0);
        this.circle.setSmooth(true);
        this.circle.setPickOnBounds(false);
        DepthManager.getInstance().setDepth(this.circle, 2);

        this.circlePane.getStyleClass().add("circle-pane");
        
        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        Rectangle slideClip = new Rectangle();
        slideClip.widthProperty().bind(this.circle.radiusProperty().multiply(2));
        slideClip.heightProperty().bind(this.circle.radiusProperty().multiply(2));
        this.stateBox.setClip(slideClip);
        slideClip.setArcWidth(100);
        slideClip.setArcHeight(100);
        slideClip.setSmooth(true);
        updateStateBoxColor();
        this.circlePane.getChildren().addAll(this.circle, this.stateBox);
        
        this.main.getChildren().setAll(this.line, this.circlePane);
        this.toggleSwitch.setGraphic(this.main);
        
        updateChildren();
        updateSizes();

        createAnimation();
        createAnimationListeners();
        this.stateTimeline.applyEndValues();
        
        registerChangeListener(toggleSwitch.selectedColorProperty(), toggleSwitch.selectedColorProperty().getName());
        registerChangeListener(toggleSwitch.unselectedColorProperty(), toggleSwitch.unselectedColorProperty().getName());
        registerChangeListener(toggleSwitch.selectedLineColorProperty(), toggleSwitch.selectedLineColorProperty().getName());
        registerChangeListener(toggleSwitch.unselectedLineColorProperty(), toggleSwitch.unselectedLineColorProperty().getName());
    }
    
    @Override
    protected void handleControlPropertyChanged(String property) 
    {
        super.handleControlPropertyChanged(property);
        if (this.toggleSwitch.selectedColorProperty().getName().equals(property)
                || this.toggleSwitch.unselectedColorProperty().getName().equals(property)
                || this.toggleSwitch.selectedLineColorProperty().getName().equals(property)
                || this.toggleSwitch.unselectedLineColorProperty().getName().equals(property)) 
        {
            updateColors();
        }
        else if (this.toggleSwitch.lineWidthProperty().getName().equals(property) 
                    || this.toggleSwitch.lineLengthProperty().getName().equals(property)
                    || this.toggleSwitch.thumbRadiusProperty().getName().equals(property))
        {
            updateSizes();
        }
    }
    
    private double computeTranslation() 
    {    
        double translation = 0;
        if (!this.toggleSwitch.isIndeterminate())
        {
            double lineWidth = this.toggleSwitch.getLineWidth();
            double lineLength = this.toggleSwitch.getLineLength();
            int direction = getSkinnable().isSelected() ? 1 : -1;
            translation = direction * (lineLength - lineWidth / 2);   
        }
        return translation;
    }
    
    private Paint determineCircleColor(boolean isSelected)
    {
        Paint color;
        if (this.toggleSwitch.isIndeterminate())
        {
            color = this.toggleSwitch.getUnselectedThumbColor();
        }
        else if (this.toggleSwitch.isSelected())
        {
            color = this.toggleSwitch.getSelectedThumbColor();
        }
        else
        {
            color = this.toggleSwitch.getUnselectedThumbColor();
        }
        return color;
    }
    
    private Paint determineLineColor(boolean isSelected)
    {
        Paint color;
        if (this.toggleSwitch.isIndeterminate())
        {
            color = this.toggleSwitch.getUnselectedLineColor();
        }
        else if (this.toggleSwitch.isSelected())
        {
            color = this.toggleSwitch.getSelectedLineColor();
        }
        else
        {
            color = this.toggleSwitch.getUnselectedLineColor();
        }
        return color;
    }
    
    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.toggleSwitch.isArmed())
        {
            opacity = 1;
        }
        else if (this.toggleSwitch.isHover())
        {
            opacity = 0.6;
        }
        return opacity;
    }

    private DepthShadow determineButtonShadow()
    {
        DepthShadow shadow;
        if (this.toggleSwitch.isArmed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (this.toggleSwitch.isHover())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
        }
        else
        {
            shadow = DepthManager.getInstance().getShadowAt(2);
        }
        return shadow;
    }
    
    private void updateColors()
    {
        if (getSkinnable().isSelected())
        {
            this.circle.setFill(this.toggleSwitch.getSelectedThumbColor());
            this.line.setStroke(this.toggleSwitch.getSelectedLineColor());
        }
        else
        {
            this.circle.setFill(this.toggleSwitch.getUnselectedThumbColor());
            this.line.setStroke(this.toggleSwitch.getUnselectedLineColor());
        }
    }
    
    private void updateSizes()
    {
        this.line.setEndX(this.toggleSwitch.getLineLength());
        this.line.setStrokeWidth(this.toggleSwitch.getLineWidth());
        this.circle.setCenterX(-this.toggleSwitch.getThumbRadius());
        this.circle.setRadius(this.toggleSwitch.getThumbRadius());
        this.circlePane.setPadding(new Insets(this.toggleSwitch.getThumbRadius() * 1.5));
    }
    
    private void createAnimation()
    {
        // @formatter:off
        this.stateTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.circlePane.translateXProperty())
                        .setEndValueSupplier(() -> computeTranslation())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !this.toggleSwitch.getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.circle.fillProperty())
                        .setEndValueSupplier(() -> determineCircleColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !this.toggleSwitch.getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.line.strokeProperty())
                        .setEndValueSupplier(() -> determineLineColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
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
                        .setTarget(this.circle.effectProperty())
                        .setEndValueSupplier(() -> determineButtonShadow())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
        this.stateTimeline.setCacheNodes(this.circlePane, this.line);
        this.stateTimeline.setAnimateCondition(() -> !this.toggleSwitch.getIsAnimationDisabled());
        this.interactionTimeline.setAnimateCondition(() -> !this.toggleSwitch.getIsAnimationDisabled());
        // @formatter:on
    }

    private void createAnimationListeners()
    {
        this.toggleSwitch.selectedProperty().addListener(observable -> 
        {
            this.stateTimeline.reverseAndContinue();
        });
        this.toggleSwitch.indeterminateProperty().addListener((ov, oldVal, newVal) ->
        {
            this.stateTimeline.start();
        });
        this.toggleSwitch.armedProperty().addListener((ov, oldVal, newVal) ->
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
        this.toggleSwitch.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.interactionTimeline.start();
        });
    }

    private void updateStateBoxColor()
    {
        CornerRadii radii = this.toggleSwitch.getBackground() == null ? null
                : this.toggleSwitch.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.toggleSwitch.getOverlayColor(), radii, insets)));
    }
}
