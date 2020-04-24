package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.SliderSkin;

import javafx.geometry.Orientation;
import javafx.scene.layout.StackPane;
import mil.af.eglin.ccf.rt.fx.control.Slider;

// TODO bind colors to rt style properties
public class RtSliderSkin extends SliderSkin
{
    private StackPane coloredTrack;
    private StackPane thumb;
    private StackPane track;

    public RtSliderSkin(Slider slider)
    {
        super(slider);

        track = (StackPane) getSkinnable().lookup(".track");
        thumb = (StackPane) getSkinnable().lookup(".thumb");

        coloredTrack = new StackPane();
        coloredTrack.getStyleClass().add("colored-track");
        coloredTrack.setMouseTransparent(true);

        getChildren().add(getChildren().indexOf(thumb), coloredTrack);
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h)
    {
        super.layoutChildren(x, y, w, h);

        double width, height, layoutX, layoutY;
        boolean isHorizontal = getSkinnable().getOrientation() == Orientation.HORIZONTAL;
        if (isHorizontal)
        {
            width = thumb.getLayoutX() - snappedLeftInset();
            height = track.getHeight();
            layoutX = track.getLayoutX();
            layoutY = track.getLayoutY();
        } 
        else
        {
            height = track.getLayoutBounds().getMaxY() + track.getLayoutY() - thumb.getLayoutY() - snappedBottomInset();
            width = track.getWidth();
            layoutX = track.getLayoutX();
            layoutY = thumb.getLayoutY();
        }

        coloredTrack.resizeRelocate(layoutX, layoutY, width, height);
    }
}
