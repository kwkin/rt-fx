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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimer;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;

import java.util.function.Supplier;

public class PromptLinesWrapper
{
    private final Supplier<Text> promptTextSupplier;
    private TextField control;

    public StackPane focusedLine = new StackPane();
    public StackPane promptContainer = new StackPane();

    private RtAnimationTimer focusTimer;
    private RtAnimationTimer unfocusTimer;

    private double initScale = 0.05;
    public final Scale promptTextScale = new Scale(1, 1, 0, 0);
    private final Scale scale = new Scale(initScale, 1);

    public ObjectProperty<Paint> animatedPromptTextFill;
    public BooleanBinding usePromptText;
    private ObjectProperty<Paint> promptTextFill;
    private ObservableValue<?> valueProperty;
    private ObservableValue<String> promptTextProperty;

    private boolean animating = false;
    private double contentHeight = 0;

    public PromptLinesWrapper(TextField control, ObjectProperty<Paint> promptTextFill, ObservableValue<?> valueProperty,
            ObservableValue<String> promptTextProperty, Supplier<Text> promptTextSupplier)
    {
        this.control = control;
        this.promptTextSupplier = promptTextSupplier;
        this.promptTextFill = promptTextFill;
        this.valueProperty = valueProperty;
        this.promptTextProperty = promptTextProperty;
    }

    public void init(Runnable createPromptNodeRunnable, Node... cachedNodes)
    {
        animatedPromptTextFill = new SimpleObjectProperty<>(promptTextFill.get());
        usePromptText = Bindings.createBooleanBinding(this::usePromptText, valueProperty, promptTextProperty,
                control.labelFloatProperty(), promptTextFill);

        // focused line
        focusedLine.setManaged(false);
        focusedLine.getStyleClass().add("input-focused-line");
        focusedLine.setBackground(
                new Background(new BackgroundFill(control.getFocusColor(), CornerRadii.EMPTY, Insets.EMPTY)));
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

        focusTimer = new RtAnimationTimer(
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(1))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(focusedLine.opacityProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> control.isFocused())
                        .build())
                    .build(), 
                RtKeyFrame.builder()
                    .setDuration(Duration.millis(160))
                    .setKeyValues(
                        RtKeyValue.builder()
                            .setTarget(scale.xProperty())
                            .setEndValue(1)
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> control.isFocused())
                        .build(),
                        RtKeyValue.builder().setTarget(animatedPromptTextFill)
                            .setEndValueSupplier(() -> control.getFocusColor())
                            .setInterpolator(Interpolator.EASE_BOTH)
                            .setAnimateCondition(() -> control.isFocused() && control.isLabelFloat())
                        .build(),
                        RtKeyValue.builder().setTargetSupplier(promptTargetSupplier)
                            .setEndValueSupplier(() -> -contentHeight)
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

        unfocusTimer = new RtAnimationTimer(new RtKeyFrame(Duration.millis(160),
                RtKeyValue.builder().setTargetSupplier(promptTargetSupplier).setEndValue(0)
                        .setInterpolator(Interpolator.EASE_BOTH).build(),
                        RtKeyValue.builder().setTarget(promptTextScale.xProperty()).setEndValue(1)
                        .setInterpolator(Interpolator.EASE_BOTH).build(),
                        RtKeyValue.builder().setTarget(promptTextScale.yProperty()).setEndValue(1)
                        .setInterpolator(Interpolator.EASE_BOTH).build()));

        promptContainer.getStyleClass().add("input-container");
        promptContainer.setManaged(false);
        promptContainer.setMouseTransparent(true);

        focusTimer.setOnFinished(() -> animating = false);
        unfocusTimer.setOnFinished(() -> animating = false);
        focusTimer.setCacheNodes(cachedNodes);
        unfocusTimer.setCacheNodes(cachedNodes);

        // handle animation on focus gained/lost event
        control.focusedProperty().addListener(observable ->
        {
            if (control.isFocused())
            {
                focus();
            }
            else
            {
                unFocus();
            }
        });

        promptTextFill.addListener(observable ->
        {
            if (!control.isLabelFloat() || !control.isFocused())
            {
                animatedPromptTextFill.set(promptTextFill.get());
            }
        });
    }

    private Object getControlValue()
    {
        Object text = valueProperty.getValue();
        return text;
    }

    private void focus()
    {
        unfocusTimer.stop();
        animating = true;
        runTimer(focusTimer, true);
    }

    private void unFocus()
    {
        focusTimer.stop();
        scale.setX(initScale);
        focusedLine.setOpacity(0);
        if (control.isLabelFloat())
        {
            animatedPromptTextFill.set(promptTextFill.get());
            Object text = getControlValue();
            if (text == null || text.toString().isEmpty())
            {
                animating = true;
                runTimer(unfocusTimer, true);
            }
            
        }
    }

    public void updateFocusColor()
    {
        Paint paint = control.getFocusColor();
        focusedLine.setBackground(paint == null ? Background.EMPTY
                : new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void updateLabelFloat(boolean animation)
    {
        if (control.isLabelFloat())
        {
            if (control.isFocused())
            {
                animateFloatingLabel(true, animation);
            }
            else
            {
                Object text = getControlValue();
                animateFloatingLabel(!(text == null || text.toString().isEmpty()), animation);
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
            if (promptTextSupplier.get().getTranslateY() != -contentHeight)
            {
                unfocusTimer.stop();
                runTimer(focusTimer, animation);
            }
        }
        else
        {
            if (promptTextSupplier.get().getTranslateY() != 0)
            {
                focusTimer.stop();
                runTimer(unfocusTimer, animation);
            }
        }
    }

    private void runTimer(RtAnimationTimer timer, boolean animation)
    {
        if (animation)
        {
            if (!timer.isRunning())
            {
                timer.start();
            }
        }
        else
        {
            timer.applyEndValues();
        }
    }

    private boolean usePromptText()
    {
        Object txt = getControlValue();
        String promptTxt = promptTextProperty.getValue();
        boolean isLabelFloat = control.isLabelFloat();
        return isLabelFloat || (promptTxt != null && (txt == null || txt.toString().isEmpty()) && !promptTxt.isEmpty()
                && !promptTextFill.get().equals(Color.TRANSPARENT));
    }

    public void layoutLines(double x, double y, double w, double h, double controlHeight, double controlWidth, double translateY)
    {
        this.contentHeight = translateY;
        focusedLine.resizeRelocate(0, controlHeight - 2, controlWidth, 2);
        
        promptContainer.resizeRelocate(x, y, w, h);
        
        scale.setPivotX(w / 2);
    }

    public void updateLabelFloatLayout()
    {
        if (!animating)
        {
            updateLabelFloat(false);
        }
        else if (unfocusTimer.isRunning())
        {
            unfocusTimer.stop();
            updateLabelFloat(true);
        }
    }
}