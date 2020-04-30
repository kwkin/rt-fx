package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

// TODO change armed to selected
public class RtToggleButtonSkin extends ToggleButtonSkin
{
    private final StackPane stateBox = new StackPane();

    private ToggleButton button;
    private RtAnimationTimer timer;
    
    public RtToggleButtonSkin(ToggleButton button)
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

        // TODO generate shadow automatically
        button.setPickOnBounds(false);
        DepthManager.getInstance().setDepth(button, 2);
        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(button.effectProperty())
                        .setEndValueSupplier(() -> determineShadow())
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
        if (button.isArmed())
        {
            opacity = 1;
        }
        else if (button.isHover() && button.isSelected())
        {
            opacity = 0.8;
        }
        else if (button.isSelected())
        {
            opacity = 0.6;
        }
        else if (button.isHover())
        {
            opacity = 0.4;
        }
        return opacity;
    }

    private DepthShadow determineShadow() 
    {
        DepthShadow shadow;
        if (button.isArmed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (button.isHover() && button.isSelected())
        {
            shadow = DepthManager.getInstance().getShadowAt(4);
        }
        else if (button.isSelected())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
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
