package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextFieldStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO add an option to have an icon
public class TextField extends javafx.scene.control.TextField implements RtComponent
{
    protected TextFieldStyle style = TextFieldStyle.FILLED;
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";
    
    public TextField()
    {
        super();
        initialize();
    }
    
    public TextField(String text)
    {
        super(text);
        initialize();
    }
    
    public TextField(TextFieldStyle style)
    {
        this();
        this.style = style;
        initialize();
    }
    
    public TextField(Accent accent) 
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public TextField(TextFieldStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public TextFieldStyle getButtonStyle()
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
}
