package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.utils.DepthManager;
import mil.af.eglin.ccf.rt.fx.utils.DepthShadow;

/**
 * A skin for {@link mil.af.eglin.ccf.rt.fx.control.ToggleButton toggle buttons}
 */
public class RtToggleButtonSkin extends ToggleButtonSkin
{
    private final ToggleButton button;
    private final StackPane stateBox = new StackPane();

    private RtAnimationTimeline timer;

    /**
     * Creates a {@code RtToggleButtonSkin} for the provided toggle button
     * 
     * @param button the toggle button that will use this skin
     */
    public RtToggleButtonSkin(final ToggleButton button)
    {
        super(button);
        this.button = button;
        
        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        updateStateBoxColor();
        
        button.setPickOnBounds(false);
        DepthManager.getInstance().setDepth(button, 2);

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
            getChildren().add(insertIndex, stateBox);
        }
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
    
    private void createAnimation()
    {
        // @formatter:off
        this.timer = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(100))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.button.effectProperty())
                        .setEndValueSupplier(() -> determineShadow())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineOpacity())
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
        // @formatter:on
        this.timer.setCacheNodes(this.stateBox);
        this.timer.setAnimateCondition(() -> !this.button.getIsAnimationDisabled());
    }

    private void createAnimationListeners()
    {
        this.button.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            this.timer.start();
        });
        this.button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            this.timer.start();
        });
        this.button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.timer.start();
        });
    }
    
    private double determineOpacity() 
    {
        double opacity = 0;
        if (this.button.isArmed())
        {
            opacity = 1;
        }
        else if (this.button.isHover() && this.button.isSelected())
        {
            opacity = 0.8;
        }
        else if (this.button.isSelected())
        {
            opacity = 0.6;
        }
        else if (this.button.isHover())
        {
            opacity = 0.4;
        }
        return opacity;
    }

    private DepthShadow determineShadow() 
    {
        DepthShadow shadow;
        if (this.button.isArmed())
        {
            shadow = DepthManager.getInstance().getShadowAt(5);
        }
        else if (this.button.isHover() && this.button.isSelected())
        {
            shadow = DepthManager.getInstance().getShadowAt(4);
        }
        else if (this.button.isSelected())
        {
            shadow = DepthManager.getInstance().getShadowAt(3);
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
