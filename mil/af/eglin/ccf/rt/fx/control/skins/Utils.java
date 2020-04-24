package mil.af.eglin.ccf.rt.fx.control.skins;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class Utils
{

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
}
