package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
    private final static Duration ANIMATION_DURATION = Duration.millis(100);

    private final Button button;
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimer timer;

    public RtButtonSkin(final Button button)
    {
        super(button);
        this.button = button;

        stateBox.getStyleClass().setAll("state-box");
        stateBox.setOpacity(0);

        createAnimation();
        button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
        button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            updateState();
        });
        updateStateBoxColor();
        updateChildren();

        registerChangeListener(button.getOverlayColorProperty(), button.getOverlayColorProperty().getName());
        
    }
    
    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (stateBox != null)
        {
            Node text = getSkinnable().lookup(".text");
            int insertIndex = getChildren().indexOf(text);
            insertIndex = insertIndex == -1 ? getChildren().size() - 1 : insertIndex;
            getChildren().add(insertIndex, stateBox);
        }
    }
    
    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (button.getOverlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
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
            timer.skipAndContinue();
        }
        else
        {
            timer.applyEndValues();
        }
    }
    
    private void createAnimation()
    {
        switch (button.getButtonStyle())
        {
            case RAISED:
                button.setPickOnBounds(false);
                DepthManager.getInstance().setDepth(button, 2);
                // @formatter:off
                timer = new RtAnimationTimer(
                    RtKeyFrame.builder()
                        .setDuration(ANIMATION_DURATION)
                        .setKeyValues(
                            RtKeyValue.builder()
                                .setTarget(this.button.effectProperty())
                                .setEndValueSupplier(() -> determineButtonShadow())
                                .setInterpolator(Interpolator.EASE_OUT)
                                .build(),
                            RtKeyValue.builder()
                                .setTarget(this.stateBox.opacityProperty())
                                .setEndValueSupplier(() -> determineStateBoxOpacity())
                                .setInterpolator(Interpolator.EASE_OUT)
                                .build())
                        .build());
                // @formatter:on
                break;
            default:
                button.setPickOnBounds(true);
                // @formatter:off
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
                // @formatter:on
                break;
        }
    }
    
    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.button.isArmed())
        {
            opacity = 1;
        }
        else if (this.button.isHover())
        {
            opacity = 0.4;
        }
        return opacity;
    }
    
    private DepthShadow determineButtonShadow()
    {
        DepthShadow shadow;
        if (this.button.isArmed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (this.button.isHover())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
        }
        else
        {
            shadow = DepthManager.getInstance().getShadowAt(2);
        }
        return shadow;
    }
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.button.getBackground() == null ? null : this.button.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }
}
