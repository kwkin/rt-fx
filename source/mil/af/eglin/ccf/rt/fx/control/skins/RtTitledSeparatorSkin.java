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
        getChildren().add(leftLine);
        getChildren().add(rightLine);
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
        Node graphic = separator.getGraphic();
        double textWidth = text == null ? 0 : text.getLayoutBounds().getWidth();
        double textHeight = text == null ? 0 : text.getLayoutBounds().getHeight();
        double graphicWidth = graphic == null ? 0 : graphic.getLayoutBounds().getWidth();
        double graphicHeight = graphic == null ? 0 : graphic.getLayoutBounds().getHeight();
        double labelWidth = 0;
        double labelHeight = 0;
        double lineWidth = w;
        double lineHeight = h;
        
        
        if (sep.getOrientation() == Orientation.HORIZONTAL)
        {
            switch (sep.getContentDisplay())
            {
                case BOTTOM:
                case TOP:
                case CENTER:
                    labelWidth = Math.max(textWidth, graphicWidth);
                    break;
                case LEFT:
                case RIGHT:
                    labelWidth = textWidth + graphicWidth;
                    break;
                default:
                    break;
            }
            
            if (labelWidth != 0)
            {
                labelWidth += this.separator.getSparatorContentGap() * 2;
            }
            lineWidth = w / 2 - labelWidth / 2;
            leftLine.resize(lineWidth, leftLine.prefHeight(-1));
            rightLine.resize(lineWidth, rightLine.prefHeight(-1));
            positionInArea(leftLine, x, y, lineWidth, h, 0, sep.getHalignment(), sep.getValignment());
            positionInArea(rightLine, x + lineWidth + labelWidth, y, lineWidth, h, 0, sep.getHalignment(), sep.getValignment());
        }
        else
        {
            switch (sep.getContentDisplay())
            {
                case BOTTOM:
                case TOP:
                    labelHeight = textHeight + graphicHeight;
                    break;
                case CENTER:
                case LEFT:
                case RIGHT:
                    labelHeight = Math.max(textHeight, graphicHeight);
                    break;
                default:
                    break;
            }
            
            if (labelHeight != 0)
            {
                labelHeight += this.separator.getSparatorContentGap() * 2;
            }
            lineHeight = h / 2 - labelHeight / 2;
            leftLine.resize(leftLine.prefWidth(-1), lineHeight);
            rightLine.resize(rightLine.prefWidth(-1), lineHeight);
            positionInArea(leftLine, x, y, w, lineHeight, 0, sep.getHalignment(), sep.getValignment());
            positionInArea(rightLine, x, y + lineHeight + labelHeight, w, lineHeight, 0, sep.getHalignment(), sep.getValignment());
        }
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
        double labeledPrefWidth = super.computePrefWidth(h, topInset, rightInset, bottomInset, leftInset);
        final TitledSeparator sep = getSkinnable();
        double w = sep.getOrientation() == Orientation.VERTICAL ? leftLine.prefWidth(-1) : DEFAULT_LENGTH;
        double separatorPrefWidth = w + leftInset + rightInset;
        return Math.max(labeledPrefWidth, separatorPrefWidth);
    }

    @Override
    protected double computePrefHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labeledPrefHeight = super.computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
        final TitledSeparator sep = getSkinnable();
        double h = sep.getOrientation() == Orientation.VERTICAL ? DEFAULT_LENGTH : leftLine.prefHeight(-1);
        double separatorPrefHeight = h + topInset + bottomInset;
        return Math.max(labeledPrefHeight, separatorPrefHeight);
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
