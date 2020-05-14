package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.RadioButtonSkin;

import javafx.animation.Interpolator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

public class RtRadioButtonSkin extends RadioButtonSkin
{
    private final RadioButton radioButton;
    private final Circle radio = new Circle();
    private final Circle dot = new Circle();
    private final StackPane container = new StackPane();

    // TODO remove padding
    private double padding = 12;
    
    private RtAnimationTimer timer;
    
    public RtRadioButtonSkin(final RadioButton radioButton)
    {
        super(radioButton);
        this.radioButton = radioButton;
        
        double radioRadius = 8;
        this.radio.setRadius(radioRadius);
        this.radio.getStyleClass().setAll("radio");
        this.radio.strokeProperty().setValue(determineColor(this.radioButton.isSelected()));
        this.radio.setStrokeWidth(2);
        this.radio.setFill(Color.TRANSPARENT);
        this.radio.setSmooth(true);

        this.dot.setRadius(radioRadius);
        this.dot.getStyleClass().setAll("dot");
        this.dot.fillProperty().setValue(radioButton.getSelectedColor());
        this.dot.setOpacity(0);
        this.dot.setScaleX(0);
        this.dot.setScaleY(0);
        this.dot.setSmooth(true);

        this.container.getChildren().addAll(radio, dot);
        this.container.getStyleClass().add("radio-container");

        getSkinnable().selectedProperty().addListener(observable -> 
        {
            if (!radioButton.getIsAnimationDisabled())
            {
                timer.reverseAndContinue();
            }
            else
            {
                timer.applyEndValues();
            }
        });

        // @formatter:off
        timer = new RtAnimationTimer(
            RtKeyFrame.builder()
                .setDuration(Duration.millis(150))
                .setKeyValues(
                    RtKeyValue.builder()
                        .setTarget(this.radio.strokeProperty())
                        .setEndValueSupplier(() -> determineColor(this.radioButton.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.opacityProperty())
                        .setEndValueSupplier(() -> this.radioButton.isSelected() ? 1 : 0)
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.scaleXProperty())
                        .setEndValueSupplier(() -> determineSize(this.radioButton.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                    RtKeyValue.builder()
                        .setTarget(this.dot.scaleYProperty())
                        .setEndValueSupplier(() -> determineSize(this.radioButton.isSelected()))
                        .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                .build());
        timer.applyEndValues();
        // @formatter:on

        updateChildren();
        
        registerChangeListener(radioButton.selectedColorProperty(), "SELECTED_COLOR");
        registerChangeListener(radioButton.unselectedColorProperty(), "UNSELECTED_COLOR");
    }
    
    @Override
    protected void handleControlPropertyChanged(String property) 
    {
        super.handleControlPropertyChanged(property);
        if ("SELECTED_COLOR".equals(property)) 
        {
            Paint paint = determineColor(this.radioButton.isSelected());
            if (paint != null)
            {
                radio.setStroke(paint);
            }
            this.dot.setFill(this.radioButton.getSelectedColor());
        } 
        else if ("UNSELECTED_COLOR".equals(property)) 
        {
            Paint paint = determineColor(this.radioButton.isSelected());
            if (paint != null)
            {
                radio.setStroke(paint);
            }
        }
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (container != null)
        {
            removeRadio();
            getChildren().add(container);
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computeMinWidth(height,
            topInset,
            rightInset,
            bottomInset,
            leftInset) + snapSize(radio.minWidth(-1)) + padding / 3;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefWidth(height,
            topInset,
            rightInset,
            bottomInset,
            leftInset) + snapSize(radio.prefWidth(-1)) + padding / 3;
    }
    
    private Paint determineColor(boolean isSelected)
    {
        return isSelected ? this.radioButton.getSelectedColor() : this.radioButton.getUnselectedColor();
    }
    
    private double determineSize(boolean isSelected)
    {
        return isSelected ? 0.55 : 0;
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        final javafx.scene.control.RadioButton radioButton = getSkinnable();
        final double contWidth = snapSize(container.prefWidth(-1));
        final double contHeight = snapSize(container.prefHeight(-1));
        final double computeWidth = Math.max(radioButton.prefWidth(-1), radioButton.minWidth(-1));
        final double width = snapSize(contWidth);
        final double height = snapSize(contHeight);
        final double labelWidth = Math.min(computeWidth - contWidth, w - width);
        final double labelHeight = Math.min(radioButton.prefHeight(labelWidth), h);
        final double maxHeight = Math.max(contHeight, labelHeight);
        final double xOffset = Utils.computeXOffset(w, labelWidth + contWidth, radioButton.getAlignment().getHpos())
                + x;
        final double yOffset = Utils.computeYOffset(h, maxHeight, radioButton.getAlignment().getVpos()) + x;

        layoutLabelInArea(xOffset + contWidth + padding / 3, yOffset, labelWidth, maxHeight, radioButton.getAlignment());
        this.container.resize(width, height);
        positionInArea(container, xOffset, yOffset, contWidth, maxHeight, 0, radioButton.getAlignment().getHpos(),
                radioButton.getAlignment().getVpos());
    }

    private void removeRadio() {
        for (int i = 0; i < getChildren().size(); i++) {
            if (getChildren().get(i).getStyleClass().contains("radio")) {
                getChildren().remove(i);
                break;
            }
        }
    }
}
