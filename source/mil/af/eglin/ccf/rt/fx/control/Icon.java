package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

/**
 * Icons are symbols used to represent actions or items.
 */
public interface Icon
{
    /**
     * Determines if the icon's color is controlled by the parent container
     * 
     * @return true if the icon's color is controlled by the parent container
     */
    public boolean isColorManaged();

    /**
     * Sets if the icon's color is controlled by the parent container
     * 
     * @param isColorManaged ture if the icon's color should be controlled by
     *            the parent container
     */
    public void setIsColorManaged(boolean isColorManaged);

    /**
     * Sets the icon's color
     * 
     * @param fill the icon's color
     */
    public void setFill(Paint fill);

    /**
     * Gets the icon's color
     * 
     * @return the icon's color
     */
    public Paint getFill();

    public double getSize();

    public Node getNode();
}
