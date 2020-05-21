package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;

// TODO combine with toggle button skin
public class RtIconToggleButtonSkin extends ToggleButtonSkin
{
    private final IconToggleButton button;
    private final StackPane stateBox = new StackPane();

    private IconToggleButtonStyle style;
    private RtAnimationTimer timer;
    
    public RtIconToggleButtonSkin(final IconToggleButton button)
    {
        super(button);
        this.button = button;
        this.style = button.getRtStyle();

        stateBox.getStyleClass().setAll("state-box");
        stateBox.setOpacity(0);

        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineOpacity())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
        timer.setCacheNodes(stateBox);

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
        
        updateChildren();
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
            timer.reverseAndContinue();
        }
        else
        {
            timer.applyEndValues();
        }
    }

    private double determineOpacity() 
    {
        double opacity = 0;
        switch(this.style)
        {
            case GLOWING:
                if (this.button.isSelected() || this.button.isArmed())
                {
                    opacity = 1;
                }
                else if (this.button.isHover())
                {
                    opacity = 0.4;
                }
                break;
            default:
                if (button.isArmed())
                {
                    opacity = 1;
                }
                else if (button.isHover())
                {
                    opacity = 0.4;
                }
                break;
            
        }
        return opacity;
    }
}
