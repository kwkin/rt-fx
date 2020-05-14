package mil.af.eglin.ccf.rt.fx.control.skins;

import java.text.DecimalFormat;

import com.sun.javafx.scene.control.skin.ProgressIndicatorSkin;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import mil.af.eglin.ccf.rt.fx.control.ProgressBar;
import mil.af.eglin.ccf.rt.fx.control.Text;
import mil.af.eglin.ccf.rt.fx.utils.JFXNodeUtils;

public class RtProgressBarSkin extends ProgressIndicatorSkin
{
    private final ProgressBar progressBar;
    private final StackPane track = new StackPane();
    private final StackPane bar = new StackPane();
    private final StackPane textPane = new StackPane();
    private final Text text = new Text();
    private double barWidth = 0;
    private Region clip;

    public RtProgressBarSkin(final ProgressBar progressBar)
    {
        super(progressBar);
        this.progressBar = progressBar;

        track.getStyleClass().setAll("track");
        bar.getStyleClass().setAll("bar");
        text.getStyleClass().setAll("text");
        textPane.getChildren().add(text);
        
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");
        StringConverter<Number> converter = new NumberStringConverter(decimalFormat);
        Bindings.bindBidirectional(text.textProperty(), progressBar.progressProperty(), converter);
        
        clip = new Region();
        clip.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        bar.backgroundProperty().addListener(observable -> JFXNodeUtils.updateBackground(bar.getBackground(), clip));
        
        getChildren().addAll(track, bar, textPane);

        progressBar.widthProperty().addListener(observable ->
        {
            updateProgress();
        });
    }

    @Override
    public double computeBaselineOffset(double topInset, double rightInset, double bottomInset, double leftInset)
    {
        return Node.BASELINE_OFFSET_SAME_AS_HEIGHT;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return Math.max(100, leftInset + bar.prefWidth(getSkinnable().getWidth()) + rightInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return topInset + bar.prefHeight(width) + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return progressBar.prefWidth(height);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return progressBar.prefHeight(width);
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h)
    {
        this.track.resizeRelocate(x, y, w, h);
        this.textPane.resizeRelocate(x, y, w, h);
        this.bar.resizeRelocate(x, y, barWidth, h);
        this.clip.resizeRelocate(0, 0, w, h);
    }

    @Override
    protected void updateProgress()
    {
        final ProgressIndicator control = getSkinnable();
        barWidth = ((int) (control.getWidth() - snappedLeftInset() - snappedRightInset()) * 2
                * Math.min(1, Math.max(0, control.getProgress()))) / 2.0F;
        control.requestLayout();
    }
}
