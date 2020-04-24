package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ComboBoxStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> implements RtComponent
{
    protected ComboBoxStyle style = ComboBoxStyle.FILLED;
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-combo-box";

    public ComboBox()
    {
        super();
        initialize();
    }
    
    public ComboBox(ComboBoxStyle style)
    {
        this();
        this.style = style;
        initialize();
    }
    
    public ComboBox(Accent accent) 
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ComboBox(ComboBoxStyle style, Accent accent)
    {
        super();
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public ComboBox(ObservableList<T> items) 
    {
        super(items);
        initialize();
    }
    
    public ComboBox(ObservableList<T> items, ComboBoxStyle style)
    {
        this(items);
        this.style = style;
        initialize();
    }
    
    public ComboBox(ObservableList<T> items, Accent accent) 
    {
        super(items);
        this.accent = accent;
        initialize();
    }
    
    public ComboBox(ObservableList<T> items, ComboBoxStyle style, Accent accent)
    {
        super(items);
        this.style = style;
        this.accent = accent;
        initialize();
    }
    
    public ComboBoxStyle getButtonStyle()
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
        String cssContextMenu = ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
        StyleManager.getInstance().addUserAgentStylesheet(cssContextMenu);
        return cssContextMenu;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }
}
