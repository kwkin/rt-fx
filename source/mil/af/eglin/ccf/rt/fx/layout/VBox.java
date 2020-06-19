package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class VBox extends javafx.scene.layout.VBox
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "vbox.css";
    private static final String CSS_CLASS = "rt-vbox";

    /**
     * Creates a VBox layout with spacing = 0 and alignment at TOP_LEFT.
     */
    public VBox()
    {
        super();
        initialize();
    }

    /**
     * Creates a VBox layout with spacing = 0, alignment at TOP_LEFT, and the
     * specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public VBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a VBox layout with the specified spacing between children.
     * 
     * @param spacing the amount of vertical space between each child
     */
    public VBox(double spacing)
    {
        super(spacing);
        initialize();
    }

    /**
     * Creates a VBox layout with the specified spacing between children and
     * accent.
     * 
     * @param spacing the amount of vertical space between each child
     * @param accent The accent type used to change the layout's color scheme.
     */
    public VBox(double spacing, Accent accent)
    {
        super(spacing);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an VBox layout with spacing = 0.
     * 
     * @param children The initial set of children for this pane.
     */
    public VBox(Node... children)
    {
        super(children);
        initialize();
    }

    /**
     * Creates an VBox layout with spacing = 0 and the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public VBox(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an VBox layout with the specified spacing between children.
     * 
     * @param spacing the amount of horizontal space between each child
     * @param children The initial set of children for this pane.
     */
    public VBox(double spacing, Node... children)
    {
        super(spacing, children);
        initialize();
    }

    /**
     * Creates an VBox layout with the specified spacing between children and
     * the specified spacing.
     * 
     * @param spacing the amount of horizontal space between each child
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
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
        VBox.loadStyleSheet();
    }
}