package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ColorPickerSkin;

import javafx.animation.Interpolator;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.ColorPickerIcon;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

public class RtColorPickerIconSkin extends ColorPickerSkin
{
    protected StackPane iconPane;
    private final ColorPickerIcon colorPicker;
    private final StackPane stateBox = new StackPane();
    private Label label;
    
    private RtAnimationTimeline interactionTimeline;
    
    public RtColorPickerIconSkin(final ColorPickerIcon colorPicker)
    {
        super(colorPicker);
        this.colorPicker = colorPicker;

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        updateStateBoxColor();
        
        if (this.stateBox != null)
        {
            Node text = getSkinnable().lookup(".text");
            int insertIndex = getChildren().indexOf(text);
            insertIndex = insertIndex == -1 ? getChildren().size() - 1 : insertIndex;
            this.stateBox.setManaged(false);
            getChildren().add(insertIndex, this.stateBox);
        }
        createAnimation();
        createAnimationListeners();

        colorPicker.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() 
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c)
            {
                if (c.next() && c.wasAdded())
                {
                    updateColor(colorPicker.getValue());
                }
            }
        });
        colorPicker.valueProperty().addListener((ov, oldVal, newVal) ->
        {
            Node icon = iconPane == null ?  colorPicker.lookup(".picker-color") : iconPane;
            if (icon != null)
            {
                updateColor(colorPicker.getValue());
            }
        });
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        // @formatter:off
        this.stateBox.resizeRelocate(
            this.colorPicker.getLayoutBounds().getMinX(),
            this.colorPicker.getLayoutBounds().getMinY(),
            this.colorPicker.getWidth(), 
            this.colorPicker.getHeight());
        // @formatter:on
    }

    private void createAnimation()
    {
        // @formatter:off
        this.colorPicker.setPickOnBounds(true);
        this.interactionTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(200))
                .setKeyValues(
                        RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineStateBoxOpacity())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
        // @formatter:on
        this.interactionTimeline.setAnimateCondition(() -> !this.colorPicker.isDisableAnimation());
    }
    
    private void createAnimationListeners()
    {
        this.colorPicker.armedProperty().addListener((ov, oldVal, newVal) ->
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
        this.colorPicker.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            this.interactionTimeline.start();
        });
    }

    private void updateColor(Color color)
    {
        String colorText = color.toString().replaceFirst("0x", "");
        String colorStyle = String.format("-fx-background-color:#%s", colorText);
        if (this.label == null)
        {
            this.label = (Label)colorPicker.lookup(".color-picker-label");
        }
        if (label != null)
        {
            Node node = label.getGraphic();
            if (node != null && node instanceof StackPane)
            {
                iconPane = (StackPane) node;
                iconPane.setStyle(colorStyle);
            }
        }
    }

    private double determineStateBoxOpacity()
    {
        double opacity = 0;
        if (this.colorPicker.isArmed())
        {
            opacity = 1;
        }
        else if (this.colorPicker.isHover())
        {
            opacity = 0.4;
        }
        return opacity;
    }

    private void updateStateBoxColor()
    {
        CornerRadii radii = this.colorPicker.getBackground() == null ? null
                : this.colorPicker.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.colorPicker.getOverlayColor(), radii, insets)));
    }
}
