package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class HBox extends javafx.scene.layout.HBox
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "hbox.css";
    private static final String CSS_CLASS = "rt-hbox";

    /**
     * Creates an HBox layout with spacing = 0.
     */
    public HBox()
    {
        super();
        initialize();
    }

    public HBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public HBox(double spacing)
    {
        super(spacing);
        initialize();
    }

    public HBox(double spacing, Accent accent)
    {
        super(spacing);
        this.accent = accent;
        initialize();
    }

    public HBox(Node... children)
    {
        super(children);
        initialize();
    }

    public HBox(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    public HBox(double spacing, Node... children)
    {
        super(spacing, children);
        initialize();
    }
    
    public HBox(double spacing, Accent accent, Node... children) 
    {
        super(spacing, children);
        this.accent = accent;
        initialize();
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
        getStyleClass().add(this.accent.getCssName());
    }

    /**
     * Loads the user agent stylesheet specific to this layout
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        HBox.loadStyleSheet();
    }
}