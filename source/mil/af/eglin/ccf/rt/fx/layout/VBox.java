package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class VBox extends javafx.scene.layout.VBox
{
    protected Accent accent;
    
    private static final String USER_AGENT_STYLESHEET = "vbox.css";
    private static final String CSS_CLASS = "rt-vbox";

    public VBox()
    {
        super();
        initialize();
    }

    public VBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public VBox(double spacing) 
    {
        super(spacing);
        initialize();
    }

    public VBox(double spacing, Accent accent)
    {
        super(spacing);
        this.accent = accent;
        initialize();
    }

    public VBox(Node... children) 
    {
        super(children);
        initialize();
    }

    public VBox(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    public VBox(double spacing, Node... children) 
    {
        super(spacing, children);
        initialize();
    }
    
    public VBox(double spacing, Accent accent, Node... children) 
    {
        super(spacing, children);
        this.accent = accent;
        initialize();
    }

    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}`
     */
    @Override
    public String getUserAgentStylesheet() 
    {
        return null;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        if (this.accent != null)
        {
            getStyleClass().add(this.accent.getCssName());
        }
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadLayouts(USER_AGENT_STYLESHEET));
    }
}