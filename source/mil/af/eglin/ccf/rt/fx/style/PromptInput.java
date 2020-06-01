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
import mil.af.eglin.ccf.rt.fx.control.RtLabelFloatControl;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

import java.util.function.Supplier;

public class PromptInput<T extends Control & RtLabelFloatControl> extends StackPane
{
    private T control;

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
    
    private RtAnimationTimer focusTimer;
    private RtAnimationTimer normalTimer;
    private RtAnimationTimer unfocusLabelTimer;
    private RtAnimationTimer hoverTimer;

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
        
        getChildren().addAll(inputContainer, overlayContainer, unfocusedLine, focusedLine, promptContainer);
    }

    public void init(Runnable createPromptNodeRunnable, Node... cachedNodes)
    {
        if (isUsingPromptText.get()) 
        {
            createPromptNodeRunnable.run();
        }
        isUsingPromptText.addListener(observable -> 
        {
            createPromptNodeRunnable.run();
            control.requestLayout();
        });
        
        Supplier<WritableValue<Number>> promptTargetSupplier = () -> promptTextSupplier.get() == null ? null
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
                            .setAnimateCondition(() -> this.activeStateProperty.getValue())
                        .build())
                    .build(), 
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(160))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(focusedLineScale.xProperty())
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
                            .setAnimateCondition(() -> this.activeStateProperty.getValue() && control.isLabelFloat())
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
        
        activeStateProperty.addListener((ov, oldVal, newVal) ->
        {
            updateState(true);
        });
        control.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!this.activeStateProperty.getValue())
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

        promptClip.setSmooth(false);
        promptClip.setX(0);
        promptClip.setY(0);
        promptClip.widthProperty().bind(this.promptContainer.widthProperty());
        promptClip.heightProperty().bind(this.promptContainer.heightProperty());
        promptContainer.setClip(promptClip);
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
                inputDisplayContainer.getChildren().add(this.displayNode);
            }
            inputDisplayContainer.getChildren().add(node);
            this.displayNode = node;
        }
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
        else if (unfocusLabelTimer.isRunning())
        {
            unfocusLabelTimer.stop();
            updateLabelFloat(true);
        }
    }

    public void layoutComponents(double x, double y, double width, double height)
    {
        resizeRelocate(x, y, width, height);
        double promptTopPadding = this.promptContainer.getPadding().getTop();
        double inputTopPadding = this.inputContainer.getPadding().getTop();
        double translateY = inputTopPadding - promptTopPadding + 2;
        
        this.promptTranslateY = translateY;
    
        double unfocusedLineHeight = unfocusedLine.getPrefHeight();
        unfocusedLine.resizeRelocate(x, y + height - unfocusedLineHeight, width, unfocusedLineHeight);
        double focusedLineHeight = focusedLine.getPrefHeight();
        focusedLine.resizeRelocate(x, y + height - focusedLineHeight, width, focusedLineHeight);
        
        focusedLineScale.setPivotX(width / 2);
    
        this.inputContainer.resizeRelocate(x, y, width, height);
        this.overlayContainer.resizeRelocate(x, y, width, height);
        
//        this.inputDisplayContainer.setPadding(new Insets(8, 0, 0, 0));
    }

    private void updateState(boolean animate)
    {
        if (this.activeStateProperty.getValue())
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
            focusedLineScale.setX(0);
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
}