package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

public class RtIconToggleButtonSkin extends ToggleButtonSkin
{
    private final static Duration ANIMATION_DURATION = Duration.millis(200);
    
    private final IconToggleButton button;
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimeline interactionTimeline;
    
    public RtIconToggleButtonSkin(final IconToggleButton button)
    {
        super(button);
        this.button = button;
        
        stateBox.getStyleClass().setAll("state-box");
        stateBox.setOpacity(0);
        updateStateBoxColor();
        
        createAnimation();
        createAnimationListeners();
        updateChildren();

        registerChangeListener(button.getOverlayColorProperty(), button.getOverlayColorProperty().getName());
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
       this. stateBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        layoutLabelInArea(x, y, w, h);
    }
    
    private void createAnimation()
    {
        // @formatter:off
        this.interactionTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(ANIMATION_DURATION)
                .setKeyValues(
                        RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineOpacity())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
        // @formatter:on
        this.interactionTimeline.setAnimateCondition(() -> !this.button.getIsAnimationDisabled());
    }
    
    private void createAnimationListeners()
    {
        this.button.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
        this.button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
        this.button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
    }

    private double determineOpacity() 
    {
        double opacity = 0;
        switch(this.button.getRtStyle())
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
    
    private void updateStateBoxColor()
    {
        CornerRadii radii = this.button.getBackground() == null ? null : this.button.getBackground().getFills().get(0).getRadii(); 
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }
}
