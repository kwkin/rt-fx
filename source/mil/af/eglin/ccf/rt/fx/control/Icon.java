package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

public interface Icon
{
    public boolean isColorManaged();
    
    public void setIsColorManaged(boolean isFillManaged);
    
    public void setFill(Paint fill);
    
    public Paint getFill();
    
    public double getSize();
    
    public Node getNode();
}
