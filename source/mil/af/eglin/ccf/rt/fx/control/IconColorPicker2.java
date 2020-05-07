package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.skins.RtColorPickerSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO combine with other icon color picker
// TODO add feature to change the icon size
public class IconColorPicker2 extends javafx.scene.control.ColorPicker implements RtComponent
{
    protected ColorPickerStyle style = ColorPickerStyle.ICON2;
    protected Accent accent = Accent.PRIMARY_MID;
    
    private boolean isLabelVisibile = false;
    private static final String USER_AGENT_STYLESHEET = "color-picker.css";
    private static final String CSS_CLASS = "rt-color-picker";

    public IconColorPicker2()
    {
        super();
        initialize();
    }

    public IconColorPicker2(Color color)
    {
        super(color);
        initialize();
    }
    
    public ColorPickerStyle getButtonStyle()
    {
        return this.style;
    }

    public void setLabelVisibility(boolean labelVisibility)
    {
        this.isLabelVisibile = labelVisibility;
        String style = String.format("-fx-color-label-visible:%s", this.isLabelVisibile);
        setStyle(style);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() 
    {
        return new RtColorPickerSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() 
    {
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
    }
    
    private void initialize()
    {
        
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }
}