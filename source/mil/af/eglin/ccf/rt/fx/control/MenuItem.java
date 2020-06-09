package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;

public class MenuItem extends javafx.scene.control.MenuItem implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String DEFAULT_STYLE_CLASS = "rt-menu-item";
    
    public MenuItem()
    {
        super();
        initialize();
    }
    
    public MenuItem(String text)
    {
        super(text);
        initialize();
    }
    
    public MenuItem(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }
    
    public MenuItem(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }
    
    public MenuItem(String text, Node graphic, Accent accent)
    {
        super(text, graphic);
        initialize();
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
        return DEFAULT_STYLE_CLASS;
    }
    
    private void initialize()
    {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }
}
