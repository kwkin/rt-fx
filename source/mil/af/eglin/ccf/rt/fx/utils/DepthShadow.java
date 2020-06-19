package mil.af.eglin.ccf.rt.fx.utils;

import javafx.animation.Interpolatable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Depth shadow extends the JavaFX drop shadow class by enabling interpolation.
 * <p>
 * Several standard depth shadows are provided in the {@link Depth Depth} class. 
 * 
 * @see Depth
 */
public class DepthShadow extends DropShadow implements Interpolatable<DepthShadow>
{
    /**
     * Creates a new instance of DepthShadow with default parameters.
     */
    public DepthShadow()
    {
    }

    /**
     * Creates a new instance of DepthShadow with specified radius and color.
     * 
     * @param radius the radius of the shadow blur kernel
     * @param color the shadow {@code Color}
     */
    public DepthShadow(double radius, Color color)
    {
        super(radius, color);
    }

    /**
     * Creates a new instance of DepthShadow with the specified radius, offsetX,
     * offsetY and color.
     * 
     * @param radius the radius of the shadow blur kernel
     * @param offsetX the shadow offset in the x direction
     * @param offsetY the shadow offset in the y direction
     * @param color the shadow {@code Color}
     */
    public DepthShadow(double radius, double offsetX, double offsetY, Color color)
    {
        super(radius, offsetX, offsetY, color);
    }

    /**
     * Creates a new instance of DepthShadow with the specified blurType, color,
     * radius, spread, offsetX and offsetY.
     * 
     * @param blurType the algorithm used to blur the shadow
     * @param color the shadow {@code Color}
     * @param radius the radius of the shadow blur kernel
     * @param spread the portion of the radius where the contribution of the
     *            source material will be 100%
     * @param offsetX the shadow offset in the x direction
     * @param offsetY the shadow offset in the y direction
     */
    public DepthShadow(BlurType blurType, Color color, double radius, double spread, double offsetX, double offsetY)
    {
        super(blurType, color, radius, spread, offsetX, offsetY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepthShadow interpolate(DepthShadow endValue, double t)
    {
        if (t <= 0.0)
            return this;
        if (t >= 1.0)
            return endValue;
        float ft = (float) t;

        return new DepthShadow(getBlurType(), getColor(), getRadius() + (endValue.getRadius() - getRadius()) * ft,
                getSpread() + (endValue.getSpread() - getSpread()) * ft,
                getOffsetX() + (endValue.getOffsetX() - getOffsetX()) * ft,
                getOffsetY() + (endValue.getOffsetY() - getOffsetY()) * ft);
    }

}
