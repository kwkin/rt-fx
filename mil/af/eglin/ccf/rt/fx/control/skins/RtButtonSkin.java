package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ButtonSkin;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.animations.CachedTransition;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;

public class RtButtonSkin extends ButtonSkin
{
    private final static Duration ANIMATION_DURATION = Duration.millis(200);
    private final StackPane stateBox = new StackPane();

    private Button button;
    private CachedTransition normalTransition;
    private CachedTransition armedTransition;
    private CachedTransition hoverTransition;

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

        // TODO use property's name rather than hardcoding the name
        registerChangeListener(button.getOverlayColorProperty(), button.getOverlayColorProperty().getName());
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
            if (button.isArmed())
            {
                armedTransition.play();
            }
            else if (button.isHover())
            {
                hoverTransition.play();
            }
            else
            {
                normalTransition.play();
            }
        }
        else
        {
            if (button.isArmed())
            {
                armedTransition.playFrom(ANIMATION_DURATION);
            }
            else if (button.isHover())
            {
                hoverTransition.playFrom(ANIMATION_DURATION);
            }
            else
            {
                normalTransition.playFrom(ANIMATION_DURATION);
            }
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
                normalTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(2), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT))));
                armedTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(5), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 1, Interpolator.EASE_OUT))));
                hoverTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(3), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 0.4, Interpolator.EASE_OUT))));
                // @formatter:on
                break;
            default:
                button.setPickOnBounds(true);
                // @formatter:off
                normalTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION, new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT))));
                armedTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION, new KeyValue(this.stateBox.opacityProperty(), 1, Interpolator.EASE_OUT))));
                hoverTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(ANIMATION_DURATION, new KeyValue(this.stateBox.opacityProperty(), 0.4, Interpolator.EASE_OUT))));
                // @formatter:on
                break;
        }
        normalTransition.setCycle(ANIMATION_DURATION);
        armedTransition.setCycle(ANIMATION_DURATION);
        hoverTransition.setCycle(ANIMATION_DURATION);
    }
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.stateBox.getBackground() == null ? null : this.stateBox.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }
}
