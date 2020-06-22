package mil.af.eglin.ccf.rt.fx.control.skins;

import java.util.Collections;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;
import com.sun.javafx.scene.control.skin.LabeledText;

import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mil.af.eglin.ccf.rt.fx.control.DescriptionContainer;
import mil.af.eglin.ccf.rt.fx.control.RtDescriptionControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.layout.StackPane;

/**
 * Creates a description container for displaying helper and error text for a
 * validable and description component.
 *
 * @param <T> the validable and description component.
 */
public class RtDescriptionContainerSkin<T extends ValidableControl<?> & RtDescriptionControl>
        extends LabeledSkinBase<DescriptionContainer<T>, BehaviorBase<DescriptionContainer<T>>>
{
    private DescriptionContainer<T> labeled;
    private StackPane textPane = new StackPane();
    private LabeledText helperText;
    private Text errorText;

    /**
     * Creates a description container for holding helper and error text
     * 
     * @param labeled A validable and description component
     */
    public RtDescriptionContainerSkin(final DescriptionContainer<T> labeled)
    {
        super(labeled, new BehaviorBase<>(labeled, Collections.emptyList()));
        this.labeled = labeled;

        this.helperText = (LabeledText) labeled.lookup(".text");

        createHelperText();
        createErrorText();

        Rectangle descriptionClip = new Rectangle();
        descriptionClip.setX(0);
        descriptionClip.setY(0);
        descriptionClip.widthProperty().bind(labeled.widthProperty());
        descriptionClip.heightProperty().bind(labeled.heightProperty());
        labeled.setClip(descriptionClip);

        textPane.getStyleClass().add("error-container");
        getChildren().addAll(this.textPane);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        this.textPane.resizeRelocate(x, y, w, h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double computePrefWidth(double h, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labeledPrefWidth = super.computePrefWidth(h, topInset, rightInset, bottomInset, leftInset);
        double errorPrefWidth = textPane.prefWidth(-1) + leftInset + rightInset;
        return Math.max(labeledPrefWidth, errorPrefWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double computePrefHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labeledPrefHeight = super.computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
        double errorPrefHeight = textPane.prefHeight(-1) + topInset + bottomInset;
        return Math.max(labeledPrefHeight, errorPrefHeight);
    }

    private void createHelperText()
    {
        this.helperText.getStyleClass().clear();
        this.helperText.getStyleClass().add("helper-text");
        this.helperText.visibleProperty().bind(this.labeled.isShowHelperTextProperty().and(this.labeled.isValidProperty()));
    }

    private void createErrorText()
    {
        this.errorText = new Text();
        this.errorText.setManaged(true);
        this.errorText.getStyleClass().add("error-text");
        this.errorText.visibleProperty().bind(this.labeled.isValidProperty().not());
        this.errorText.textProperty().bind(this.labeled.errorMessageProperty());
        StackPane.setAlignment(this.errorText, Pos.CENTER_LEFT);
        this.textPane.getChildren().add(this.errorText);
    }
}
