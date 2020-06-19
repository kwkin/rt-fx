package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TextFlow extends javafx.scene.text.TextFlow
{
    protected Accent accent = Accent.BASE;
    
    private static final String USER_AGENT_STYLESHEET = "text-flow.css";
    private static final String CSS_CLASS = "rt-text-flow";

    /**
     * Creates an empty TextFlow layout.
     */
    public TextFlow()
    {
        super();
        initialize();
    }

    /**
     * Creates an empty TextFlow layout with the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public TextFlow(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a TextFlow layout with the given children.
     *
     * @param children children.
     */
    public TextFlow(Node... children)
    {
        super(children);
        initialize();
    }

    /**
     * Creates a TextFlow layout with the given children and specified accent.
     *
     * @param children children.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public TextFlow(Accent accent, Node... children)
    {
        super(children);
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
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadLayouts(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        TextFlow.loadStyleSheet();
    }
}
