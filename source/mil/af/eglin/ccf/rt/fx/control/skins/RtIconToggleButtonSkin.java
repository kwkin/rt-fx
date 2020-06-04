package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

public class RtIconToggleButtonSkin extends ToggleButtonSkin
{
    private final static Duration ANIMATION_DURATION = Duration.millis(200);
    
    private final IconToggleButton button;
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimer timer;
    
    public RtIconToggleButtonSkin(final IconToggleButton button)
    {
        super(button);
        this.button = button;
        
        stateBox.getStyleClass().setAll("state-box");
        stateBox.setOpacity(0);
        updateChildren();
        
        createAnimation();
        button.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
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
    protected void updateChildren()
    {
        super.updateChildren();
        if (stateBox != null)
        {
            getChildren().add(0, stateBox);
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        stateBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        layoutLabelInArea(x, y, w, h);
    }
    
    private void updateState()
    {
        if (!button.getIsAnimationDisabled())
        {
            timer.start();
        }
        else
        {
            timer.applyEndValues();
        }
    }
    
    private void createAnimation()
    {
        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(ANIMATION_DURATION)
                .setKeyValues(
                        RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValue(0)
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
    }
}
