package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;

// TODO add style property for line width
//TODO add toggle switch to checkbox in order to allow for an indeterminate state
public class RtToggleSwitchSkin extends ToggleButtonSkin
{
    private final StackPane main;
    private final Circle circle;
    private final Line line;

    private RtAnimationTimer timer;
    private ToggleSwitch toggleSwitch;
    
    public RtToggleSwitchSkin(ToggleSwitch toggleSwitch) 
    {
        super(toggleSwitch);
        
        this.toggleSwitch = toggleSwitch;
        
        double circleRadius = 8;
        
        line = new Line();
        line.getStyleClass().add("line");
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(circleRadius * 2.2);
        line.setEndY(0);
        line.setStrokeWidth(circleRadius * 2.8);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setSmooth(true);

        circle = new Circle();
        circle.getStyleClass().add("circle");
        circle.setCenterX(-circleRadius);
        circle.setCenterY(0);
        circle.setRadius(circleRadius);
        circle.setSmooth(true);

        StackPane circlePane = new StackPane();
        circlePane.getChildren().add(circle);
        circlePane.setPadding(new Insets(circleRadius * 1.5));

        this.main = new StackPane();
        this.main.getChildren().setAll(line, circlePane);

        getSkinnable().selectedProperty().addListener(observable -> 
        {
            if (!toggleSwitch.getIsAnimationDisabled())
            {
                timer.reverseAndContinue();
            }
            else
            {
                updateSelectionState();
            }
        });

        getSkinnable().setGraphic(main);
        
        // @formatter:off
        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(circle.translateXProperty())
                        .setEndValueSupplier(() -> computeTranslation(circleRadius, line))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((ToggleSwitch) getSkinnable()).getIsAnimationDisabled())
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(circle.fillProperty())
                        .setEndValueSupplier(() -> determineCircleColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((ToggleSwitch) getSkinnable()).getIsAnimationDisabled())
                        .build(),
                        
                    RtKeyValue.builder()
                        .setTarget(line.strokeProperty())
                        .setEndValueSupplier(() -> determineLineColor(this.toggleSwitch.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((ToggleSwitch) getSkinnable()).getIsAnimationDisabled())
                        .build())
                .build());
        timer.setCacheNodes(circle, line);
        // @formatter:on
        
        updateSelectionState();
 
        // TODO add change listeners
    }
    
    private void updateSelectionState()
    {
        circle.setTranslateX(computeTranslation(8, line));
        updateColors();
    }
    
    private double computeTranslation(double circleRadius, Line line) 
    {
        double translation = 0;
        if (getSkinnable().isSelected())
        {
            translation = ((line.getLayoutBounds().getWidth() / 2) - circleRadius - 4);
        }
        else
        {
            translation = -((line.getLayoutBounds().getWidth() / 2) - circleRadius - 4);
        }
        return translation;
    }
    
    private Paint determineCircleColor(boolean isSelected)
    {
        return isSelected ? this.toggleSwitch.getSelectedColor() : this.toggleSwitch.getUnselectedColor();
    }
    
    private Paint determineLineColor(boolean isSelected)
    {
        return isSelected ? this.toggleSwitch.getSelectedLineColor() : this.toggleSwitch.getUnselectedLineColor();
    }
    
    private void updateColors()
    {
        if (getSkinnable().isSelected())
        {
            circle.setFill(((ToggleSwitch)getSkinnable()).getSelectedColor());
            line.setStroke(((ToggleSwitch)getSkinnable()).getSelectedLineColor());
        }
        else
        {
            circle.setFill(((ToggleSwitch)getSkinnable()).getUnselectedColor());
            line.setStroke(((ToggleSwitch)getSkinnable()).getUnselectedLineColor());
        }
    }
}
