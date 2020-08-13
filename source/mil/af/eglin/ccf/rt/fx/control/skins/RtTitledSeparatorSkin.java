package mil.af.eglin.ccf.rt.fx.control.skins;

import java.util.Collections;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.LabeledSkinBase;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;

/**
 * A skin for {@link mil.af.eglin.ccf.rt.fx.control.TitledSeparator titled separators}
 */
public class RtTitledSeparatorSkin extends LabeledSkinBase<TitledSeparator, BehaviorBase<TitledSeparator>>
{
    private final TitledSeparator separator;
    
    private final Node text;
    private final Region leftLine = new Region();
    private final Region rightLine = new Region();

    /**
     * Creates a {@code RtTitledSeparatorSkin} for the provided titled separator
     * 
     * @param separator the titled separator that will use this skin
     */
    public RtTitledSeparatorSkin(final TitledSeparator separator)
    {
        super(separator, new BehaviorBase<>(separator, Collections.emptyList()));
        this.separator = separator;
        
        this.leftLine.getStyleClass().setAll("left-line");
        this.rightLine.getStyleClass().setAll("right-line");
        this.text = separator.lookup(".text");
        
        updateChildren();
        
        registerChangeListener(separator.orientationProperty(), separator.orientationProperty().getName());
        registerChangeListener(separator.titleAlignmentProperty(), separator.titleAlignmentProperty().getName());
    }
    
    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (leftLine != null)
        {
            getChildren().add(this.leftLine);
        }
        if (rightLine != null)
        {
            getChildren().add(this.rightLine);
        }
    }

    @Override
    protected void handleControlPropertyChanged(String changed)
    {
        super.handleControlPropertyChanged(changed);
        if (this.separator.orientationProperty().getName().equals(changed) 
                || this.separator.titleAlignmentProperty().getName().equals(changed))
        {
            this.separator.requestLayout();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        super.layoutChildren(x, y, w, h);
        final TitledSeparator sep = getSkinnable();

        Node graphic = this.separator.getGraphic();
        double textWidth = this.text == null ? 0 : this.text.getLayoutBounds().getWidth();
        double textHeight = this.text == null ? 0 : this.text.getLayoutBounds().getHeight();
        double graphicWidth = graphic == null ? 0 : graphic.getLayoutBounds().getWidth();
        double graphicHeight = graphic == null ? 0 : graphic.getLayoutBounds().getHeight();
        double labelWidth = 0;
        double labelHeight = 0;
        double lineWidth = w;
        double lineHeight = h;
        
        if (sep.getOrientation() == Orientation.HORIZONTAL)
        {
            
            VPos lineVPos = VPos.CENTER;
            switch(this.separator.getTitlealignment().getVpos())
            {
                case BOTTOM:
                    lineVPos = VPos.TOP;
                    lineWidth = w / 2;
                    labelWidth = 0;
                    break;
                case TOP:
                    lineVPos = VPos.BOTTOM;
                    lineWidth = w / 2;
                    labelWidth = 0;
                    break;
                case CENTER:
                default:
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
                    break;
                
            }
            leftLine.resize(lineWidth, leftLine.prefHeight(-1));
            rightLine.resize(lineWidth, rightLine.prefHeight(-1));
            positionInArea(leftLine, x, y, lineWidth, h, 0, HPos.CENTER, lineVPos);
            positionInArea(rightLine, x + lineWidth + labelWidth, y, lineWidth, h, 0, HPos.CENTER, lineVPos);
        }
        else
        {
            HPos lineHPos = HPos.CENTER;
            switch(this.separator.getAlignment().getHpos())
            {
                case LEFT:
                    lineHPos = HPos.RIGHT;
                    lineHeight = h / 2;
                    labelHeight = 0;
                    break;
                case RIGHT:
                    lineHPos = HPos.LEFT;
                    lineHeight = h / 2;
                    labelHeight = 0;
                    break;
                case CENTER:
                    break;
                default:
                    lineHPos = HPos.CENTER;
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
                    break;
                
            }
            leftLine.resize(leftLine.prefWidth(-1), lineHeight);
            rightLine.resize(rightLine.prefWidth(-1), lineHeight);
            positionInArea(leftLine, x, y, w, lineHeight, 0,lineHPos, VPos.CENTER);
            positionInArea(rightLine, x, y + lineHeight + labelHeight, w, lineHeight, 0, lineHPos, VPos.CENTER);
        }
        layoutLabelInArea(x + this.separator.getSparatorContentGap(), y, w - this.separator.getSparatorContentGap() * 2, h, this.separator.getTitlealignment());
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
        double separatorPrefWidth = leftLine.prefWidth(-1) + leftInset + rightInset;
        return Math.max(labeledPrefWidth, separatorPrefWidth);
    }

    @Override
    protected double computePrefHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double labeledPrefHeight = super.computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
        double separatorPrefHeight = leftLine.prefHeight(-1) + topInset + bottomInset;
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
