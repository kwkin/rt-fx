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

    private Button button;
    private RtAnimationTimer timer;
    
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
        
        this.timer = createAnimation();
        button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
        button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        // @formatter:off
        stateBox.resizeRelocate(
            button.getLayoutBounds().getMinX(),
            button.getLayoutBounds().getMinY(),
            button.getWidth(), 
            button.getHeight());
        // @formatter:on
        
        layoutLabelInArea(x, y, w, h);
    }
    
    private void updateState()
    {
        if (!button.getIsAnimationDisabled())
        {
            timer.reverseAndContinue();
        }
        else
        {
            timer.applyEndValues();
        }
    }
    
    private RtAnimationTimer createAnimation()
    {
        RtAnimationTimer timer;
        switch(button.getButtonStyle())
        {
            case RAISED:
                button.setPickOnBounds(false);
                DepthManager.getInstance().setDepth(button, 2);
                // @formatter:off
                timer = new RtAnimationTimer(
                    RtKeyFrame.builder()
                        .setDuration(Duration.millis(100))
                        .setKeyValues(
                            RtKeyValue.builder()
                                .setTarget(button.effectProperty())
                                .setEndValueSupplier(() -> determinedShadow())
                                .setInterpolator(Interpolator.EASE_BOTH)
                                .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                .build(),
                            RtKeyValue.builder()
                                .setTarget(stateBox.opacityProperty())
                                .setEndValueSupplier(() -> determineOpacity())
                                .setInterpolator(Interpolator.EASE_BOTH)
                                .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                .build())
                        .build());
                timer.setCacheNodes(stateBox);
                // @formatter:on
                break;
            default:
                button.setPickOnBounds(true);
                timer = new RtAnimationTimer(
                    RtKeyFrame.builder()
                        .setDuration(Duration.millis(100))
                        .setKeyValues(
                                RtKeyValue.builder()
                                    .setTarget(stateBox.opacityProperty())
                                    .setEndValueSupplier(() -> determineOpacity())
                                    .setInterpolator(Interpolator.EASE_BOTH)
                                    .setAnimateCondition(() -> !button.getIsAnimationDisabled())
                                    .build())
                            .build());
                timer.setCacheNodes(stateBox);
                break;    
        }
        return timer;
    }

    private double determineOpacity() 
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

    private DepthShadow determinedShadow() 
    {
        DepthShadow shadow;
        if (button.isArmed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (button.isHover())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
        }
        else
        {
            shadow = DepthManager.getInstance().getShadowAt(2);
        }
        return shadow;
    }
}
