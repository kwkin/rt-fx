package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;

// TODO add toggle switch to checkbox in order to allow for an indeterminate state
public class RtToggleSwitchSkin extends LabeledSkinBase<ToggleSwitch, ButtonBehavior<ToggleSwitch>>
{
    private final ToggleSwitch toggleSwitch;
    private final StackPane main = new StackPane();
    private final StackPane circlePane = new StackPane();
    private final Circle circle = new Circle();
    private final Line line = new Line();

    private RtAnimationTimeline stateTimeline;
    
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

        this.circlePane.getChildren().add(this.circle);
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
    protected void updateChildren()
    {
        super.updateChildren(); 
        if (main != null && line != null && circlePane != null)
        {
            this.main.getChildren().setAll(line, circlePane);
        }
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
    
    private void updateColors()
    {
        if (getSkinnable().isSelected())
        {
            circle.setFill(((ToggleSwitch)getSkinnable()).getSelectedThumbColor());
            line.setStroke(((ToggleSwitch)getSkinnable()).getSelectedLineColor());
        }
        else
        {
            circle.setFill(((ToggleSwitch)getSkinnable()).getUnselectedThumbColor());
            line.setStroke(((ToggleSwitch)getSkinnable()).getUnselectedLineColor());
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
                        .setTarget(this.circle.translateXProperty())
                        .setEndValueSupplier(() -> computeTranslation())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((ToggleSwitch) getSkinnable()).getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.circle.fillProperty())
                        .setEndValueSupplier(() -> determineCircleColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((ToggleSwitch) getSkinnable()).getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.line.strokeProperty())
                        .setEndValueSupplier(() -> determineLineColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
        this.stateTimeline.setCacheNodes(this.circle, this.line);
        this.stateTimeline.setAnimateCondition(() -> !this.toggleSwitch.getIsAnimationDisabled());
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
    }
}
