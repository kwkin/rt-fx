package mil.af.eglin.ccf.rt.fx.style;

import javafx.animation.Interpolator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.RtLabelFloatControl;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

import java.util.function.Supplier;

public class PromptInput<T extends Control & RtLabelFloatControl> 
{
    private final Supplier<Text> promptTextSupplier;
    private T control;

    public Region overlayContainer = new Region();
    public Region focusedLine = new Region();
    public Region unfocusedLine = new Region();
    public final Rectangle clip = new Rectangle();
    public StackPane promptContainer = new StackPane();

    private RtAnimationTimer focusTimer;
    private RtAnimationTimer normalTimer;
    private RtAnimationTimer unfocusLabelTimer;
    private RtAnimationTimer hoverTimer;

    private double initScale = 0.05;
    public final Scale promptTextScale = new Scale(1, 1, 0, 0);
    private final Scale scale = new Scale(initScale, 1);

    public ObjectProperty<Paint> animatedPromptTextFill;
    public BooleanBinding usePromptText;
    private ObjectProperty<Paint> promptTextFill;
    private ObservableValue<?> valueProperty;
    private ObservableValue<String> promptTextProperty;
    private ObservableValue<Boolean> toggleFlag;

    private boolean animating = false;
    private double promptTranslateY = 0;

    public PromptInput(T control, Region overlaycontainer, ObjectProperty<Paint> promptTextFill, ObservableValue<?> valueProperty,
            ObservableValue<String> promptTextProperty, Supplier<Text> promptTextSupplier, ObservableValue<Boolean> toggleFlag, StackPane promptContainer)
    {
        this.control = control;
        this.overlayContainer = overlaycontainer;
        this.promptTextSupplier = promptTextSupplier;
        this.promptTextFill = promptTextFill;
        this.valueProperty = valueProperty;
        this.promptTextProperty = promptTextProperty;
        this.toggleFlag = toggleFlag;
        this.promptContainer = promptContainer;
    }

    public void init(Runnable createPromptNodeRunnable, Node... cachedNodes)
    {
        animatedPromptTextFill = new SimpleObjectProperty<>(promptTextFill.get());
        usePromptText = Bindings.createBooleanBinding(this::usePromptText, valueProperty, promptTextProperty,
                control.labelFloatProperty(), promptTextFill);

        unfocusedLine.setManaged(false);
        unfocusedLine.getStyleClass().add("input-unfocused-line");
        unfocusedLine.setOpacity(1);
        
        focusedLine.setManaged(false);
        focusedLine.getStyleClass().add("input-focused-line");
        focusedLine.setOpacity(0);
        focusedLine.getTransforms().add(scale);
        
        if (usePromptText.get()) 
        {
            createPromptNodeRunnable.run();
        }
        usePromptText.addListener(observable -> 
        {
            createPromptNodeRunnable.run();
            control.requestLayout();
        });
        
        final Supplier<WritableValue<Number>> promptTargetSupplier = () -> promptTextSupplier.get() == null ? null
                : promptTextSupplier.get().translateYProperty();

        // @formatter:off
        focusTimer = new RtAnimationTimer(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(1))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(focusedLine.opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> this.toggleFlag.getValue())
                        .build())
                    .build(), 
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(160))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(scale.xProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> !control.isDisableAnimation())
                        .build(),
                        RtKeyValue.builder().setTarget(this.overlayContainer.opacityProperty())
                            .setEndValueSupplier(() -> 0.5)
                            .setAnimateCondition(() -> !control.isDisableAnimation())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(animatedPromptTextFill)
                            .setEndValueSupplier(() -> control.getFocusColor())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> this.toggleFlag.getValue() && control.isLabelFloat())
                        .build(),
                        RtKeyValue.builder().setTargetSupplier(promptTargetSupplier)
                            .setEndValueSupplier(() -> -promptTranslateY)
                            .setAnimateCondition(() -> control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(promptTextScale.xProperty()).setEndValue(0.85)
                            .setAnimateCondition(() -> control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(promptTextScale.yProperty()).setEndValue(0.85)
                            .setAnimateCondition(() -> control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                   .build());
        // @formatter:on

        // @formatter:off
        unfocusLabelTimer = new RtAnimationTimer(new RtKeyFrame(Duration.millis(160),
                RtKeyValue.builder().setTargetSupplier(promptTargetSupplier)
                    .setEndValue(0)
                    .setInterpolator(Interpolator.EASE_BOTH)
                .build(),
                RtKeyValue.builder().setTarget(promptTextScale.xProperty())
                    .setEndValue(1)
                    .setInterpolator(Interpolator.EASE_BOTH)
                .build(),
                RtKeyValue.builder().setTarget(promptTextScale.yProperty())
                    .setEndValue(1)
                    .setInterpolator(Interpolator.EASE_BOTH)
                .build()));
        normalTimer = new RtAnimationTimer(new RtKeyFrame(Duration.millis(160),
                RtKeyValue.builder().setTarget(this.overlayContainer.opacityProperty())
                    .setEndValueSupplier(() -> 0)
                    .setInterpolator(Interpolator.EASE_BOTH)
                .build()));
        // @formatter:on

        // @formatter:off
        hoverTimer = new RtAnimationTimer(new RtKeyFrame(Duration.millis(80),
                RtKeyValue.builder().setTarget(this.overlayContainer.opacityProperty())
                    .setEndValueSupplier(() -> 0.2)
                    .setInterpolator(Interpolator.EASE_BOTH)
                .build()));
        // @formatter:on
        
        focusTimer.setOnFinished(() ->  animating = false);
        normalTimer.setOnFinished(() -> animating = false);
        focusTimer.setCacheNodes(cachedNodes);
        normalTimer.setCacheNodes(cachedNodes);
        unfocusLabelTimer.setCacheNodes(cachedNodes);
        
        this.toggleFlag.addListener((ov, oldVal, newVal) ->
        {
            updateState(true);
        });
        control.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!this.toggleFlag.getValue())
            {
                updateState(true);
            }
        });

        promptTextFill.addListener(observable ->
        {
            if (!control.isLabelFloat() || !control.isFocused())
            {
                animatedPromptTextFill.set(promptTextFill.get());
            }
        });
        updateState(false);

        clip.setSmooth(false);
        clip.setX(0);
        clip.setY(0);
        clip.widthProperty().bind(this.promptContainer.widthProperty());
        clip.heightProperty().bind(this.promptContainer.heightProperty());
        promptContainer.setClip(clip);
    }

    private void updateState(boolean animate)
    {
        if (this.toggleFlag.getValue())
        {
            normalTimer.stop();
            hoverTimer.stop();
            unfocusLabelTimer.stop();
            runTimer(focusTimer, animate);
        }
        else if (this.control.isHover())
        {
            normalTimer.stop();
            focusTimer.stop();
            unfocusLabelTimer.stop();
            focusedLine.setOpacity(0);
            runTimer(hoverTimer, animate);
        }
        else
        {
            hoverTimer.stop();
            focusTimer.stop();
            scale.setX(initScale);
            focusedLine.setOpacity(0);
            if (control.isLabelFloat())
            {
                animatedPromptTextFill.set(promptTextFill.get());
                Object text = getControlValue();
                if (text == null || text.toString().isEmpty())
                {
                    runTimer(unfocusLabelTimer, animate);
                }
            }
            runTimer(normalTimer, animate);
        }
        animating = animate;
    }
    
    private Object getControlValue()
    {
        Object text = valueProperty.getValue();
        return text;
    }

    public void updateFocusColor()
    {
        Paint paint = control.getFocusColor();
        Background background = paint == null ?
                Background.EMPTY : new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
        focusedLine.setBackground(background);
    }

    public void updateUnfocusColor()
    {
        Paint paint = control.getUnfocusColor();
        Background background = paint == null ? 
                Background.EMPTY : new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
        unfocusedLine.setBackground(background);
    }
    
    private void updateLabelFloat(boolean animation)
    {
        if (control.isLabelFloat())
        {
            if (this.toggleFlag.getValue())
            {
                animateFloatingLabel(true, animation);
            }
            else
            {
                Object text = getControlValue();
                animateFloatingLabel(!(text == null || text.toString().isEmpty()), animation);
                runTimer(normalTimer, animation);
            }
        }
    }

    private void animateFloatingLabel(boolean up, boolean animation)
    {
        if (promptTextSupplier.get() == null)
        {
            return;
        }
        if (up)
        {
            if (promptTextSupplier.get().getTranslateY() != -promptTranslateY)
            {
                unfocusLabelTimer.stop();
                runTimer(focusTimer, animation);
            }
        }
        else
        {
            if (promptTextSupplier.get().getTranslateY() != 0)
            {
                focusTimer.stop();
                runTimer(unfocusLabelTimer, animation);
            }
        }
    }
    private void runTimer(RtAnimationTimer timer, boolean animation)
    {
        if (animation && !timer.isRunning())
        {
            timer.start();
        }
        else
        {
            timer.applyEndValues();
        }
    }

    private boolean usePromptText()
    {
        Object text = getControlValue();
        String promptText = promptTextProperty.getValue();
        boolean isLabelFloat = control.isLabelFloat();
        return isLabelFloat || (promptText != null && (text == null || text.toString().isEmpty()) && !promptText.isEmpty()
                && !promptTextFill.get().equals(Color.TRANSPARENT));
    }

    public void layoutComponents(double x, double y, double inputContainerWidth, double inputContainerHeight, double translateY)
    {
        this.promptTranslateY = translateY;

        double unfocusedLineHeight = unfocusedLine.getPrefHeight();
        unfocusedLine.resizeRelocate(x, y + inputContainerHeight - unfocusedLineHeight, inputContainerWidth, unfocusedLineHeight);
        double focusedLineHeight = focusedLine.getPrefHeight();
        focusedLine.resizeRelocate(x, y + inputContainerHeight - focusedLineHeight, inputContainerWidth, focusedLineHeight);
        
        scale.setPivotX(inputContainerWidth / 2);
    }

    public void updateLabelFloatLayout()
    {
        if (!animating)
        {
            updateLabelFloat(false);
        }
        else if (unfocusLabelTimer.isRunning())
        {
            unfocusLabelTimer.stop();
            updateLabelFloat(true);
        }
    }
}