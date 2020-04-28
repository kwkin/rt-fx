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
                                .setAnimateCondition(() -> !((Button) getSkinnable()).getIsAnimationDisabled())
                                .build(),
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
                                    .setTarget(button.effectProperty())
                                    .setEndValueSupplier(() -> determineHoverShadow(button.isHover()))
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !((Button) getSkinnable()).getIsAnimationDisabled())
                                    .build(),
                                RtKeyValue.builder()
                                    .setTarget(hoverBox.opacityProperty())
                                    .setEndValueSupplier(() -> determineHoverOpacity(button.isHover()))
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !((Button) getSkinnable()).getIsAnimationDisabled())
                                    .build())
                            .build());
                hoverTimer.setCacheNodes(hoverBox);
                break;
            default:
                button.setPickOnBounds(true);
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

    private DepthShadow determinedArmedShadow(boolean isArmed) 
    {
        return isArmed ? DepthManager.getInstance().getShadowAt(5) : DepthManager.getInstance().getShadowAt(2);
    }

    private DepthShadow determineHoverShadow(boolean isHover) 
    {
        return isHover ? DepthManager.getInstance().getShadowAt(3) : DepthManager.getInstance().getShadowAt(2);
    }
}
