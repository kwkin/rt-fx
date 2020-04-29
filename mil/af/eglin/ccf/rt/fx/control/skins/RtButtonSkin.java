package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

public class RtButtonSkin extends ButtonSkin
{
    private final StackPane stateBox = new StackPane();

    // TODO combine armed timer and hover timer
    private Button button;
    private RtAnimationTimer hoverTimer;
    private RtAnimationTimer armedTimer;
    
    public RtButtonSkin(Button button)
    {
        super(button);
        this.button = button;
        
        stateBox.getStyleClass().setAll("state-box");
        stateBox.setOpacity(0);
        
        Node text = getSkinnable().lookup(".text");
        int index = getChildren().indexOf(text);
        index = index == -1 ? getChildren().size() - 1 : index;
        if (stateBox != null)
        {
            getChildren().add(index, stateBox);
        }
        
        switch(button.getButtonStyle())
        {
            case RAISED:
                // TODO generate shadow automatically
                button.setPickOnBounds(false);
                DepthManager.getInstance().setDepth(button, 2);
                armedTimer = new RtAnimationTimer(
                    RtKeyFrame.builder()
                        .setDuration(Duration.millis(100))
                        .setKeyValues(
                            RtKeyValue.builder()
                                .setTarget(button.effectProperty())
                                .setEndValueSupplier(() -> determinedArmedShadow(button.isArmed()))
                                .setInterpolator(Interpolator.EASE_BOTH)
                                .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                .build(),
                            RtKeyValue.builder()
                                .setTarget(stateBox.opacityProperty())
                                .setEndValueSupplier(() -> determineOpacity(button.isArmed()))
                                .setInterpolator(Interpolator.EASE_BOTH)
                                .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                .build())
                        .build());
                armedTimer.setCacheNodes(stateBox);
                
                hoverTimer = new RtAnimationTimer(
                        RtKeyFrame.builder()
                            .setDuration(Duration.millis(100))
                            .setKeyValues(
                                RtKeyValue.builder()
                                    .setTarget(button.effectProperty())
                                    .setEndValueSupplier(() -> determineHoverShadow(button.isHover()))
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                    .build(),
                                RtKeyValue.builder()
                                    .setTarget(stateBox.opacityProperty())
                                    .setEndValueSupplier(() -> determineOpacity(button.isHover()))
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                    .build())
                            .build());
                hoverTimer.setCacheNodes(stateBox);
                break;
            default:
                button.setPickOnBounds(true);
                armedTimer = new RtAnimationTimer(
                    RtKeyFrame.builder()
                        .setDuration(Duration.millis(100))
                        .setKeyValues(
                                RtKeyValue.builder()
                                    .setTarget(stateBox.opacityProperty())
                                    .setEndValueSupplier(() -> determineOpacity(button.isArmed()))
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                    .build())
                            .build());
                    armedTimer.setCacheNodes(stateBox);
                    
                    hoverTimer = new RtAnimationTimer(
                        RtKeyFrame.builder()
                            .setDuration(Duration.millis(100))
                            .setKeyValues(
                                    RtKeyValue.builder()
                                        .setTarget(stateBox.opacityProperty())
                                        .setEndValueSupplier(() -> determineOpacity(button.isHover()))
                                        .setInterpolator(Interpolator.EASE_BOTH)
                                        .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                        .build())
                                .build());
                    armedTimer.setCacheNodes(stateBox);
                break;
            
        }
        
        
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
        stateBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        // @formatter:on
        
        layoutLabelInArea(x, y, w, h);
    }

    private double determineOpacity(boolean isArmed) 
    {
        double opacity = 0;
        if (button.isArmed())
        {
            opacity = 1;
        }
        else if (button.isHover())
        {
            opacity = 0.4;
        }
        return opacity;
    }

    private DepthShadow determinedArmedShadow(boolean isArmed) 
    {
        return isArmed ? DepthManager.getInstance().getShadowAt(5) : DepthManager.getInstance().getShadowAt(2);
    }

    private DepthShadow determineHoverShadow(boolean isHover) 
    {
        return isHover ? DepthManager.getInstance().getShadowAt(3) : DepthManager.getInstance().getShadowAt(2);
    }
}
