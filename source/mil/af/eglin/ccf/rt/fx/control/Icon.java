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

    /**
     * Gets the size of the icon when uniform in width and height
     * 
     * @param the size of the icon when uniform in width and height
     */
    public double getSize();

    /**
     * Gets the javafx node of the icon
     * 
     * @return the javafx of the icon
     */
    public Node getNode();
}
