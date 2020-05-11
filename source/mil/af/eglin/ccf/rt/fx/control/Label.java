package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Label extends javafx.scene.control.Label implements RtComponent
{
    protected LabelStyle style = LabelStyle.NORMAL;
    protected Accent accent = Accent.BASE_MID;
    
    private static final String USER_AGENT_STYLESHEET = "label.css";
    private static final String CSS_CLASS = "rt-label";
    
    public Label()
    {
        super();
        initialize();
    }
    
    public Label(String text)
    {
        super(text);
        initialize();
    }
    
    public Label(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }
    
    // TODO other constructors
    // TODO java doc
    public Label(String text, LabelStyle style)
    {
        this(text);
        this.style = style;
        initialize();
    }
    
    public Label(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }
    
    public Label(String text, LabelStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public LabelStyle getLabelStyle()
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
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
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
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
