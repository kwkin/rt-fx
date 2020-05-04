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
            if (!button.getIsAnimationDisabled())
            {
                // TODO add cue positions for hover
                armedTransition.setRate(newVal ? 1 : -1);
                armedTransition.play();
            }
            else
            {
                armedTransition.playFrom(newVal ? Duration.ZERO : ANIMATION_DURATION);
            }
        });
        button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!button.getIsAnimationDisabled())
            {
                hoverTransition.setRate(newVal ? 1 : -1);
                hoverTransition.play();
            }
            else
            {
                hoverTransition.playFrom(newVal ? Duration.ZERO : ANIMATION_DURATION);
            }
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

    private void createAnimation()
    {
        switch (button.getButtonStyle())
        {
            case RAISED:
                button.setPickOnBounds(false);
                DepthManager.getInstance().setDepth(button, 2);
                // @formatter:off
                armedTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(Duration.ZERO,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(2), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT)),
                    new KeyFrame(ANIMATION_DURATION,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(5), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 1, Interpolator.EASE_OUT))));
                hoverTransition = new CachedTransition(null, new Timeline(
                    new KeyFrame(Duration.ZERO,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(2), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT)),
                    new KeyFrame(ANIMATION_DURATION,  
                        new KeyValue(button.effectProperty(), DepthManager.getInstance().getShadowAt(3), Interpolator.EASE_OUT), 
                        new KeyValue(this.stateBox.opacityProperty(), 0.4, Interpolator.EASE_OUT))));
                // @formatter:on
                armedTransition.setCycle(ANIMATION_DURATION);
                hoverTransition.setCycle(ANIMATION_DURATION);
                break;
            default:
                button.setPickOnBounds(true);
                // @formatter:off
                armedTransition = new CachedTransition(null, 
                    new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT)),
                        new KeyFrame(ANIMATION_DURATION, new KeyValue(this.stateBox.opacityProperty(), 1, Interpolator.EASE_OUT))));
                hoverTransition = new CachedTransition(null, 
                    new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(this.stateBox.opacityProperty(), 0, Interpolator.EASE_OUT)),
                        new KeyFrame(ANIMATION_DURATION, new KeyValue(this.stateBox.opacityProperty(), 0.4, Interpolator.EASE_OUT))));
                // @formatter:on
                armedTransition.setCycle(ANIMATION_DURATION);
                hoverTransition.setCycle(ANIMATION_DURATION);
                break;
        }
    }
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.stateBox.getBackground() == null ? null : this.stateBox.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }
}
