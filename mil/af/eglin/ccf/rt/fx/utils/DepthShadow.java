package mil.af.eglin.ccf.rt.fx.utils;

import javafx.animation.Interpolatable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

// TODO look into adding static references to the levels, similarly to how Color defines predefined paints.
// TODO benchmark performance when creating new DepthShadow vs setting
public class DepthShadow extends DropShadow implements Interpolatable<DepthShadow>
{
    public DepthShadow(double radius, Color color)
    {
        super(radius, color);
    }
    
    public DepthShadow(double radius, double offsetX, double offsetY, Color color)
    {
        super(radius, offsetX, offsetY, color);
    }
    
    public DepthShadow(BlurType blurType, Color color, double radius, double spread,
            double offsetX, double offsetY)
    {
        super(blurType, color, radius, spread, offsetX, offsetY);
    }

    @Override
    public DepthShadow interpolate(DepthShadow endValue, double t)
    {
        if (t <= 0.0) return this;
        if (t >= 1.0) return endValue;
        float ft = (float) t;

        return new DepthShadow(getBlurType(), getColor(),
            getRadius() + (endValue.getRadius() - getRadius()) * ft,
            getSpread() + (endValue.getSpread() - getSpread()) * ft,
            getOffsetX() + (endValue.getOffsetX() - getOffsetX()) * ft,
            getOffsetY() + (endValue.getOffsetY() - getOffsetY()) * ft
        );
//        setRadius(getRadius() + (endValue.getRadius() - getRadius()) * ft);
//        setSpread(getSpread() + (endValue.getSpread() - getSpread()) * ft);
//        setOffsetX(getOffsetX() + (endValue.getOffsetX() - getOffsetX()) * ft);
//        setOffsetY(getOffsetY() + (endValue.getOffsetY() - getOffsetY()) * ft);
//        return this;
    }

}
