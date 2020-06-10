
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
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

/**
 * The component skin for action and icon buttons
 */
public class RtButtonSkin extends ButtonSkin
{
    private final static Duration ANIMATION_DURATION = Duration.millis(200);

    private final Button button;
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimeline interactionTimeline;

    public RtButtonSkin(final Button button)
    {
        super(button);
        this.button = button;

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        updateStateBoxColor();

        updateChildren();
        createAnimation();
        createAnimationListeners();

        registerChangeListener(button.overlayColorProperty(), button.overlayColorProperty().getName());
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (button.overlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (this.stateBox != null)
        {
            Node text = getSkinnable().lookup(".text");
            int insertIndex = getChildren().indexOf(text);
            insertIndex = insertIndex == -1 ? getChildren().size() - 1 : insertIndex;
            getChildren().add(insertIndex, this.stateBox);
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        // @formatter:off
        this.stateBox.resizeRelocate(
            this.button.getLayoutBounds().getMinX(),
            this.button.getLayoutBounds().getMinY(),
            this.button.getWidth(), 
            this.button.getHeight());
        // @formatter:on

        layoutLabelInArea(x, y, w, h);
    }

    private void createAnimation()
    {
        // @formatter:off
        switch (button.getButtonStyle())
        {
            case RAISED:
                button.setPickOnBounds(false);
                DepthManager.getInstance().setDepth(button, 2);
                this.interactionTimeline = new RtAnimationTimeline(
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
                break;
            default:
                this.button.setPickOnBounds(true);
                this.interactionTimeline = new RtAnimationTimeline(
                    RtKeyFrame.builder()
                        .setDuration(ANIMATION_DURATION)
                        .setKeyValues(
                                RtKeyValue.builder()
                                .setTarget(this.stateBox.opacityProperty())
                                .setEndValueSupplier(() -> determineStateBoxOpacity())
                                .setInterpolator(Interpolator.EASE_OUT)
                                .build())
                        .build());
                break;
        }
        // @formatter:on
        this.interactionTimeline.setAnimateCondition(() -> !this.button.getIsAnimationDisabled());
    }
    
    private void createAnimationListeners()
    {
        this.button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (oldVal)
            {
                this.interactionTimeline.skipAndContinue();
            }
            else
            {
                this.interactionTimeline.start();
            }
        });
        this.button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.interactionTimeline.start();
        });
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
        CornerRadii radii = this.button.getBackground() == null ? null
                : this.button.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }
}
