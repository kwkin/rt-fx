package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.animations.CachedTransition;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

// TODO combine armed and hover boxes
public class RtButtonSkin extends ButtonSkin
{
    private final StackPane armedBox = new StackPane();
    private final StackPane hoverBox = new StackPane();
    
    private RtAnimationTimer hoverTimer;
    private RtAnimationTimer armedTimer;
    
    public RtButtonSkin(Button button)
    {
        super(button);
        
        armedBox.getStyleClass().setAll("armedBox");
        armedBox.setOpacity(0);
        hoverBox.getStyleClass().setAll("hoverBox");
        hoverBox.setOpacity(0);
        
        Node text = getSkinnable().lookup(".text");
        int index = getChildren().indexOf(text);
        index = index == -1 ? getChildren().size() - 1 : index;
        if (hoverBox != null)
        {
            getChildren().add(index, hoverBox);
        }
        if (armedBox != null)
        {
            getChildren().add(index, armedBox);
        }
        
        armedTimer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(armedBox.opacityProperty())
                        .setEndValueSupplier(() -> determineArmedOpacity(button.isArmed()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .setAnimateCondition(() -> !((Button) getSkinnable()).getIsAnimationDisabled())
                        .build())
                .build());
        armedTimer.setCacheNodes(armedBox);
        
        hoverTimer = new RtAnimationTimer(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(hoverBox.opacityProperty())
                            .setEndValueSupplier(() -> determineHoverOpacity(button.isHover()))
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> !((Button) getSkinnable()).getIsAnimationDisabled())
                            .build())
                    .build());
        armedTimer.setCacheNodes(hoverBox);
        
        button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!button.getIsAnimationDisabled())
            {
                armedTimer.reverseAndContinue();
            }
            else
            {
                armedTimer.applyEndValues();
            }
        });
        button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!button.getIsAnimationDisabled())
            {
                hoverTimer.reverseAndContinue();
            }
            else
            {
                hoverTimer.applyEndValues();
            }
        });
    }


    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        // @formatter:off
        armedBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        
        hoverBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        // @formatter:on
        
        layoutLabelInArea(x, y, w, h);
    }

    private double determineArmedOpacity(boolean isArmed) 
    {
        return isArmed ? 1 : 0;
    }

    private double determineHoverOpacity(boolean isHover) 
    {
        return isHover ? 1 : 0;
    }
}
