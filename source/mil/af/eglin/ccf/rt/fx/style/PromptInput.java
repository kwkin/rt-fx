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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.LabelFloatControl;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

import java.util.function.Supplier;

public class PromptInput<T extends Control & LabelFloatControl>
{
    protected final T control;
    protected final StackPane inputContainer = new StackPane();
    protected final StackPane inputDisplayContainer = new StackPane();
    protected final StackPane overlayContainer = new StackPane();
    protected final StackPane promptContainer = new StackPane();
    protected final Region focusedLine = new Region();
    protected final Region unfocusedLine = new Region();
    protected final Rectangle promptClip = new Rectangle();
    protected Node displayNode;

    private Supplier<Text> promptTextSupplier;
    private ObjectProperty<Paint> promptTextFill;
    private ObservableValue<?> valueProperty;
    private ObservableValue<String> promptTextProperty;
    private ObservableValue<Boolean> activeStateProperty;
    private ObjectProperty<Paint> animatedPromptTextFill;
    private BooleanBinding isUsingPromptText;
    
    private Scale focusedLineScale = new Scale(0, 1);
    private Scale promptTextScale = new Scale(1, 1, 0, 0);
    
    private RtAnimationTimeline focusTimeline;
    private RtAnimationTimeline normalTimeline;
    private RtAnimationTimeline unfocusLabelTimeline;
    private RtAnimationTimeline hoverTimeline;

    private boolean animating = false;
    private double promptTranslateY = 0;
    
    public PromptInput(T control, ObservableValue<?> valueProperty, ObjectProperty<Paint> promptTextFill,
            ObservableValue<String> promptTextProperty, Supplier<Text> promptTextSupplier, ObservableValue<Boolean> activeStateProperty)
    {
        this.control = control;
        this.valueProperty = valueProperty;
        this.promptTextFill = promptTextFill;
        this.promptTextSupplier = promptTextSupplier;
        this.promptTextProperty = promptTextProperty;
        this.activeStateProperty = activeStateProperty;
        
        this.inputContainer.setManaged(false);
        this.inputContainer.getStyleClass().add("input-container");
        this.inputContainer.getChildren().add(inputDisplayContainer);
        
        this.overlayContainer.getStyleClass().add("overlay-container");
        this.overlayContainer.setOpacity(0);

        this.unfocusedLine.setManaged(false);
        this.unfocusedLine.getStyleClass().add("input-unfocused-line");
        
        this.focusedLine.setManaged(false);
        this.focusedLine.getStyleClass().add("input-focused-line");
        this.focusedLine.setOpacity(0);
        this.focusedLine.getTransforms().add(focusedLineScale);

        this.promptContainer.getStyleClass().add("prompt-container");
        this.promptContainer.setManaged(false);
        this.animatedPromptTextFill = new SimpleObjectProperty<>(promptTextFill.get());
        this.isUsingPromptText = Bindings.createBooleanBinding(this::usePromptText, valueProperty, promptTextProperty,
                control.labelFloatProperty(), promptTextFill);
    }

    public void init(Runnable createPromptNodeRunnable, Node... cachedNodes)
    {
        if (isUsingPromptText.get()) 
        {
            createPromptNodeRunnable.run();
        }
        this.isUsingPromptText.addListener(observable -> 
        {
            createPromptNodeRunnable.run();
            this.control.requestLayout();
        });

        createAnimators(cachedNodes);
        
        this.activeStateProperty.addListener((ov, oldVal, newVal) ->
        {
            updateState(true);
        });
        this.control.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!this.activeStateProperty.getValue())
            {
                updateState(true);
            }
        });

        this.promptTextFill.addListener(observable ->
        {
            if (!this.control.isLabelFloat() || !this.control.isFocused())
            {
                this.animatedPromptTextFill.set(this.promptTextFill.get());
            }
        });
        updateState(false);

        this.promptClip.setSmooth(false);
        this.promptClip.setX(0);
        this.promptClip.setY(0);
        this.promptClip.widthProperty().bind(this.promptContainer.widthProperty());
        this.promptClip.heightProperty().bind(this.promptContainer.heightProperty());
        this.promptContainer.setClip(this.promptClip);
    }

    public Pane getInputContainer()
    {
        return this.inputContainer;
    }
    
    public Pane getInputDisplayContainer()
    {
        return this.inputDisplayContainer;
    }

    public Pane getPromptContainer()
    {
        return this.promptContainer;
    }
    
    public Pane getOverlayContainer()
    {
        return this.overlayContainer;
    }

    public Node getDisplayNode()
    {
        return this.displayNode;
    }
    
    public Node getUnfocusedLine()
    {
        return this.unfocusedLine;
    }
    
    public Node getFocusedLine()
    {
        return this.focusedLine;
    }

    public ObjectProperty<Paint> animatedPromptTextFillProperty()
    {
        return this.animatedPromptTextFill;
    }

    public BooleanBinding isUsingPromptTextProperty()
    {
        return this.isUsingPromptText;
    }

    public Scale getPromptTextScale()
    {
        return this.promptTextScale;
    }

    public void addPromptText(Text promptText)
    {
        this.promptContainer.getChildren().add(promptText);
    }

    public void addInput(Node node)
    {
        if (this.displayNode != node)
        {
            if (this.displayNode != null)
            {
                this.inputDisplayContainer.getChildren().add(this.displayNode);
            }
            this.inputDisplayContainer.getChildren().add(node);
            this.displayNode = node;
        }
    }

    public void updateFocusColor()
    {
        Paint paint = control.getFocusColor();
        Background background = paint == null ?
                Background.EMPTY : new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
        this.focusedLine.setBackground(background);
    }

    public void updateUnfocusColor()
    {
        Paint paint = control.getUnfocusColor();
        Background background = paint == null ? 
                Background.EMPTY : new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY));
        this.unfocusedLine.setBackground(background);
    }

    public void updateOverlayColor(Paint overlayColor)
    {
        CornerRadii radii = this.control.getBackground() == null ? null
                : this.control.getBackground().getFills().get(0).getRadii();
        this.overlayContainer.setBackground(
                new Background(new BackgroundFill(overlayColor, radii, Insets.EMPTY)));
    }

    public void updateLabelFloatLayout()
    {
        if (!animating)
        {
            updateLabelFloat(false);
        }
        else if (unfocusLabelTimeline.isRunning())
        {
            this.unfocusLabelTimeline.stop();
            updateLabelFloat(true);
        }
    }

    public void layoutComponents(double x, double y, double width, double height, double translateY)
    {
        this.promptTranslateY = translateY;
    
        double unfocusedLineHeight = unfocusedLine.getPrefHeight();
        this.unfocusedLine.resizeRelocate(x, y + height - unfocusedLineHeight, width, unfocusedLineHeight);
        double focusedLineHeight = focusedLine.getPrefHeight();
        this.focusedLine.resizeRelocate(x, y + height - focusedLineHeight, width, focusedLineHeight);
        
        this.focusedLineScale.setPivotX(width / 2);
    
        this.inputContainer.resizeRelocate(x, y, width, height);
        this.overlayContainer.resizeRelocate(x, y, width, height);
    }

    private void updateState(boolean animate)
    {
        if (this.activeStateProperty.getValue())
        {
            this.normalTimeline.stop();
            this.hoverTimeline.stop();
            this.unfocusLabelTimeline.stop();
            runTimer(this.focusTimeline, animate);
        }
        else if (this.control.isHover())
        {
            this.normalTimeline.stop();
            this.focusTimeline.stop();
            this.unfocusLabelTimeline.stop();
            this.focusedLine.setOpacity(0);
            runTimer(this.hoverTimeline, animate);
        }
        else
        {
            this.hoverTimeline.stop();
            this.focusTimeline.stop();
            this.focusedLineScale.setX(0);
            this.focusedLine.setOpacity(0);
            if (this.control.isLabelFloat())
            {
                this.animatedPromptTextFill.set(this.promptTextFill.get());
                Object text = getControlValue();
                if (text == null || text.toString().isEmpty())
                {
                    runTimer(this.unfocusLabelTimeline, animate);
                }
            }
            runTimer(this.normalTimeline, animate);
        }
        this.animating = animate;
    }
    
    private Object getControlValue()
    {
        Object text = this.valueProperty.getValue();
        return text;
    }

    private void updateLabelFloat(boolean animation)
    {
        if (control.isLabelFloat())
        {
            if (this.activeStateProperty.getValue())
            {
                animateFloatingLabel(true, animation);
            }
            else
            {
                Object text = getControlValue();
                animateFloatingLabel(!(text == null || text.toString().isEmpty()), animation);
                runTimer(this.normalTimeline, animation);
            }
        }
    }

    private void animateFloatingLabel(boolean up, boolean animation)
    {
        if (this.promptTextSupplier.get() == null)
        {
            return;
        }
        if (up)
        {
            if (this.promptTextSupplier.get().getTranslateY() != -this.promptTranslateY)
            {
                this.unfocusLabelTimeline.stop();
                runTimer(this.focusTimeline, animation);
            }
        }
        else
        {
            if (this.promptTextSupplier.get().getTranslateY() != 0)
            {
                this.focusTimeline.stop();
                runTimer(this.unfocusLabelTimeline, animation);
            }
        }
    }
    
    private void runTimer(RtAnimationTimeline timer, boolean animation)
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
        String promptText = this.promptTextProperty.getValue();
        boolean isLabelFloat = this.control.isLabelFloat();
        return isLabelFloat || (promptText != null && (text == null || text.toString().isEmpty()) && !promptText.isEmpty()
                && !this.promptTextFill.get().equals(Color.TRANSPARENT));
    }
    
    private void createAnimators(Node...cachedNodes)
    {
        Supplier<WritableValue<Number>> promptTargetSupplier = () -> this.promptTextSupplier.get() == null ? null
                : this.promptTextSupplier.get().translateYProperty();
        
        // @formatter:off
        this.focusTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(1))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(focusedLine.opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setApplyCondition(() -> this.activeStateProperty.getValue())
                        .build())
                    .build(), 
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.focusedLineScale.xProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setApplyCondition(() -> this.activeStateProperty.getValue())
                        .build(),
                        RtKeyValue.builder().setTarget(this.overlayContainer.opacityProperty())
                            .setEndValueSupplier(() -> 0.5)
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(this.animatedPromptTextFill)
                            .setEndValueSupplier(() -> this.control.getFocusColor())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setApplyCondition(() -> this.activeStateProperty.getValue() && this.control.isLabelFloat())
                        .build(),
                        RtKeyValue.builder().setTargetSupplier(promptTargetSupplier)
                            .setEndValueSupplier(() -> -this.promptTranslateY)
                            .setApplyCondition(() -> this.control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(this.promptTextScale.xProperty()).setEndValue(0.85)
                        .setApplyCondition(() -> this.control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder().setTarget(this.promptTextScale.yProperty()).setEndValue(0.85)
                        .setApplyCondition(() -> this.control.isLabelFloat())
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                   .build());
        this.unfocusLabelTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTargetSupplier(promptTargetSupplier)
                            .setEndValue(0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder()
                            .setTarget(this.promptTextScale.xProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build(),
                        RtKeyValue.builder()
                            .setTarget(promptTextScale.yProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                    .build());
        this.normalTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.overlayContainer.opacityProperty())
                            .setEndValueSupplier(() -> 0)
                            .setInterpolator(Interpolator.EASE_BOTH)
                        .build())
                    .build());
        this.hoverTimeline = new RtAnimationTimeline(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(100))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(this.overlayContainer.opacityProperty())
                            .setEndValueSupplier(() -> 0.2)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .build())
                    .build());
        // @formatter:on
        this.focusTimeline.setAnimateCondition(() -> !this.control.isDisableAnimation());
        this.unfocusLabelTimeline.setAnimateCondition(() -> !this.control.isDisableAnimation());
        this.normalTimeline.setAnimateCondition(() -> !this.control.isDisableAnimation());
        this.hoverTimeline.setAnimateCondition(() -> !this.control.isDisableAnimation());
        
        this.focusTimeline.setOnFinished(() ->  animating = false);
        this.unfocusLabelTimeline.setOnFinished(() -> animating = false);
        this.normalTimeline.setOnFinished(() -> animating = false);
        this.hoverTimeline.setOnFinished(() -> animating = false);
        
        this.focusTimeline.setCacheNodes(cachedNodes);
        this.unfocusLabelTimeline.setCacheNodes(cachedNodes);
        this.normalTimeline.setCacheNodes(cachedNodes);
        this.normalTimeline.setCacheNodes(cachedNodes);
    }
}