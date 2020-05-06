package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.ColorPickerSkin;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class RtColorPickerSkin extends ColorPickerSkin
{
    protected StackPane iconPane;

    public RtColorPickerSkin(final ColorPicker colorPicker)
    {
        super(colorPicker);

        
        
        colorPicker.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() 
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c)
            {
                if (c.next() && c.wasAdded())
                {
                    Label label = (Label)colorPicker.lookup(".color-picker-label");
                    Node node = label.getGraphic();
                    if (node != null && node instanceof StackPane)
                    {
                        iconPane = (StackPane) node;
                        updateIconColor(iconPane, colorPicker.getValue());
                    }
                }
            }
        });
        colorPicker.valueProperty().addListener((ov, oldVal, newVal) ->
        {
            Node icon = iconPane == null ?  colorPicker.lookup(".picker-color") : iconPane;
            if (icon != null)
            {
                updateIconColor(icon, newVal);
            }
        });
    }

    private void updateIconColor(Node icon, Color color)
    {
        String colorText = color.toString().replaceFirst("0x", "");
        String colorStyle = String.format("-fx-background-color:#%s", colorText);
        icon.setStyle(colorStyle);
    }
}
