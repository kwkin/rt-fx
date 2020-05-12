package mil.af.eglin.ccf.rt.fx.control.skins;

import java.util.Collections;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;

public class RtTitledSeparatorSkin extends LabeledSkinBase<TitledSeparator, BehaviorBase<TitledSeparator>>
{
    private static final double DEFAULT_LENGTH = 10;

    private final Region leftLine;
    private final Region rightLine;
    private final TitledSeparator separator;

    public RtTitledSeparatorSkin(final TitledSeparator separator)
    {
        super(separator, new BehaviorBase<>(separator, Collections.emptyList()));
        this.separator = separator;
        
        leftLine = new Region();
        leftLine.getStyleClass().setAll("left-line");
        rightLine = new Region();
        rightLine.getStyleClass().setAll("right-line");
        getChildren().add(0, leftLine);
        getChildren().add(0, rightLine);
        registerChangeListener(separator.orientationProperty(), "ORIENTATION");
        registerChangeListener(separator.halignmentProperty(), "HALIGNMENT");
        registerChangeListener(separator.valignmentProperty(), "VALIGNMENT");
    }

    @Override
    protected void handleControlPropertyChanged(String p)
    {
        super.handleControlPropertyChanged(p);
        if ("ORIENTATION".equals(p) || "HALIGNMENT".equals(p) || "VALIGNMENT".equals(p))
        {
            getSkinnable().requestLayout();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        // TODO use snapSize
        super.layoutChildren(x, y, w, h);
        final TitledSeparator sep = getSkinnable();

        Node text = separator.lookup(".text");
        double labelWidth = 0;
        if (sep.getOrientation() == Orientation.HORIZONTAL)
        {
            double textWidth = text.getLayoutBounds().getWidth() + this.separator.getSparatorContentGap() * 2;
            labelWidth = w / 2 - textWidth / 2;
            leftLine.resize(labelWidth, leftLine.prefHeight(-1));
            rightLine.resize(labelWidth, rightLine.prefHeight(-1));
        }
        else
        {
            leftLine.resize(leftLine.prefWidth(-1), h);
            rightLine.resize(rightLine.prefWidth(-1), h);
        }
        positionInArea(leftLine, x, y, labelWidth, h, 0, sep.getHalignment(), sep.getValignment());
        positionInArea(rightLine, x + labelWidth + text.getLayoutBounds().getWidth() + this.separator.getSparatorContentGap() * 2, y, labelWidth, h, 0, sep.getHalignment(), sep.getValignment());
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double h, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        final TitledSeparator sep = getSkinnable();
        double w = sep.getOrientation() == Orientation.VERTICAL ? leftLine.prefWidth(-1) : DEFAULT_LENGTH;
        return w + leftInset + rightInset;
    }

    @Override
    protected double computePrefHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        final TitledSeparator sep = getSkinnable();
        double h = sep.getOrientation() == Orientation.VERTICAL ? DEFAULT_LENGTH : leftLine.prefHeight(-1);
        return h + topInset + bottomInset;
    }

    @Override
    protected double computeMaxWidth(double h, double topInset, double rightInset, double bottomInset, double leftInset)
    {
        final TitledSeparator sep = getSkinnable();
        return sep.getOrientation() == Orientation.VERTICAL ? sep.prefWidth(h) : Double.MAX_VALUE;
    }

    @Override
    protected double computeMaxHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        final TitledSeparator sep = getSkinnable();
        return sep.getOrientation() == Orientation.VERTICAL ? Double.MAX_VALUE : sep.prefHeight(w);
    }
}
