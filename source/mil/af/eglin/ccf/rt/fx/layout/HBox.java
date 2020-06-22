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

    /**
     * Creates an HBox layout with spacing = 0 and the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public HBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an HBox layout with the specified spacing between children.
     * 
     * @param spacing the amount of horizontal space between each child
     */
    public HBox(double spacing)
    {
        super(spacing);
        initialize();
    }

    /**
     * Creates an HBox layout with the specified spacing between children and
     * accent.
     * 
     * @param spacing the amount of horizontal space between each child
     * @param accent The accent type used to change the layout's color scheme.
     */
    public HBox(double spacing, Accent accent)
    {
        super(spacing);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an HBox layout with spacing = 0.
     * 
     * @param children The initial set of children for this pane.
     */
    public HBox(Node... children)
    {
        super(children);
        initialize();
    }

    /**
     * Creates an HBox layout with spacing = 0 and the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public HBox(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an HBox layout with the specified spacing between children.
     * 
     * @param spacing the amount of horizontal space between each child
     * @param children The initial set of children for this pane.
     */
    public HBox(double spacing, Node... children)
    {
        super(spacing, children);
        initialize();
    }

    /**
     * Creates an HBox layout with the specified spacing between children.
     * 
     * @param spacing the amount of horizontal space between each child.
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
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
        getStyleClass().add(this.accent.getStyleClassName());
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
        HBox.loadStyleSheet();
    }
}