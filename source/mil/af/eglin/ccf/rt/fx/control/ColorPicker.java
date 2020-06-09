package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.skins.RtColorPickerSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ColorPickerStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ColorPicker extends javafx.scene.control.ColorPicker implements RtStyleableComponent
{
    protected ColorPickerStyle style = ColorPickerStyle.FILLED;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "color-picker.css";
    private static final String CSS_CLASS = "rt-color-picker";

    public ColorPicker()
    {
        super();
        initialize();
    }

    public ColorPicker(ColorPickerStyle style)
    {
        this();
        this.style = style;
        initialize();
    }

    public ColorPicker(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public ColorPicker(ColorPickerStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }

    public ColorPicker(Color color)
    {
        super(color);
        initialize();
    }

    public ColorPicker(Color color, ColorPickerStyle style)
    {
        this(color);
        this.style = style;
        initialize();
    }

    public ColorPicker(Color color, Accent accent)
    {
        super(color);
        this.accent = accent;
        initialize();
    }

    public ColorPicker(Color color, ColorPickerStyle style, Accent accent)
    {
        super(color);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    public ColorPickerStyle getButtonStyle()
    {
        return this.style;
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
        return null;
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }

    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
