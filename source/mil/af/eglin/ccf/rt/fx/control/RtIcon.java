package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

public interface RtIcon
{
    public boolean isGlyphColorManaged();
    
    public void setIsGlyphColorManaged(boolean isGlyphColorManaged);
    
    public void setGlyphFill(Paint fill);
    
    public Paint getGlyphFill();
    
    public double getGlyphSize();
    
    public Node getGlyph();
}
