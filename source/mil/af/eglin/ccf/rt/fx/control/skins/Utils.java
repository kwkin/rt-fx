package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.text.TextLayout;
import com.sun.javafx.tk.Toolkit;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;

public class Utils
{

    static final TextLayout layout = Toolkit.getToolkit().getTextLayoutFactory().createLayout();

    static double computeXOffset(double width, double contentWidth, HPos hpos)
    {
        switch (hpos)
        {
            case LEFT:
                return 0;
            case CENTER:
                return (width - contentWidth) / 2;
            case RIGHT:
                return width - contentWidth;
        }
        return 0;
    }

    static double computeYOffset(double height, double contentHeight, VPos vpos)
    {

        switch (vpos)
        {
            case TOP:
                return 0;
            case CENTER:
                return (height - contentHeight) / 2;
            case BOTTOM:
                return height - contentHeight;
            default:
                return 0;
        }
    }

    public static void setBackgroundColor(Region nodeToUpdate, Paint fill)
    {
        Background currentBackground = nodeToUpdate.getBackground();
        if (currentBackground != null && !currentBackground.getFills().isEmpty())
        {
            BackgroundFill[] fills = new BackgroundFill[currentBackground.getFills().size()];
            for (int index = 0; index < currentBackground.getFills().size(); ++index)
            {
                BackgroundFill backgroundFill = currentBackground.getFills().get(index);
                fills[index] = new BackgroundFill(fill, backgroundFill.getRadii(), backgroundFill.getInsets());
            }
            nodeToUpdate.setBackground(new Background(fills));
        }
    }

    public static void setBorderColor(Region nodeToUpdate, Paint fill)
    {
        Border currentBorder = nodeToUpdate.getBorder();
        if (currentBorder != null && !currentBorder.getStrokes().isEmpty())
        {
            BorderStroke[] fills = new BorderStroke[currentBorder.getStrokes().size()];
            for (int index = 0; index < currentBorder.getStrokes().size(); ++index)
            {
                BorderStroke borderStroke = currentBorder.getStrokes().get(index);
                // @formatter:off
                fills[index] = new BorderStroke(fill, fill, fill, fill,
                        borderStroke.getTopStyle(), 
                        borderStroke.getRightStyle(), 
                        borderStroke.getBottomStyle(), 
                        borderStroke.getLeftStyle(),
                        borderStroke.getRadii(), 
                        borderStroke.getWidths(), 
                        borderStroke.getInsets());
                // @formatter:on
            }
            nodeToUpdate.setBorder(new Border(fills));
        }
    }

    @SuppressWarnings("deprecation")
    public static double computeTextWidth(Font font, String text, double wrappingWidth)
    {
        layout.setContent(text != null ? text : "", font.impl_getNativeFont());
        layout.setWrapWidth((float) wrappingWidth);
        return layout.getBounds().getWidth();
    }

    @SuppressWarnings("deprecation")
    static double computeTextHeight(Font font, String text, double wrappingWidth, double lineSpacing,
            TextBoundsType boundsType)
    {
        layout.setContent(text != null ? text : "", font.impl_getNativeFont());
        layout.setWrapWidth((float) wrappingWidth);
        layout.setLineSpacing((float) lineSpacing);
        if (boundsType == TextBoundsType.LOGICAL_VERTICAL_CENTER)
        {
            layout.setBoundsType(TextLayout.BOUNDS_CENTER);
        }
        else
        {
            layout.setBoundsType(0);
        }
        return layout.getBounds().getHeight();
    }

    public static double boundedSize(double value, double min, double max)
    {
        return Math.min(Math.max(value, min), Math.max(min, max));
    }
}
