package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.scene.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;

// TODO combine with toggle button skin
public class RtIconToggleButtonSkin extends ToggleButtonSkin
{
    private final StackPane selectedBox = new StackPane();
    
    public RtIconToggleButtonSkin(IconToggleButton button)
    {
        super(button);

        selectedBox.getStyleClass().setAll("selectedBox");

        updateChildren();
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (selectedBox != null)
        {
            getChildren().add(0, selectedBox);
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        selectedBox.resizeRelocate(
            getSkinnable().getLayoutBounds().getMinX(),
            getSkinnable().getLayoutBounds().getMinY(),
            getSkinnable().getWidth(), getSkinnable().getHeight());
        layoutLabelInArea(x, y, w, h);
    }
}
