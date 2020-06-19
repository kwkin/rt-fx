package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TilePane extends javafx.scene.layout.TilePane
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "tile-pane.css";
    private static final String CSS_CLASS = "rt-tile-pane";

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5 and hgap/vgap =
     * 0.
     */
    public TilePane()
    {
        super();
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation,
     * prefColumn/prefRows = 5 and hgap/vgap = 0.
     * 
     * @param orientation the direction the tiles should flow & wrap
     */
    public TilePane(Orientation orientation)
    {
        super(orientation);
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5 and the
     * specified hgap/vgap.
     * 
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     */
    public TilePane(double hgap, double vgap)
    {
        super(hgap, vgap);
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation, hgap/vgap, and
     * prefRows/prefColumns = 5.
     * 
     * @param orientation the direction the tiles should flow & wrap
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     */
    public TilePane(Orientation orientation, double hgap, double vgap)
    {
        super(orientation, hgap, vgap);
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5 and hgap/vgap =
     * 0.
     * 
     * @param children The initial set of children for this pane.
     */
    public TilePane(Node... children)
    {
        super(children);
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation,
     * prefColumn/prefRows = 5 and hgap/vgap = 0.
     * 
     * @param orientation the direction the tiles should flow & wrap
     * @param children The initial set of children for this pane.
     */
    public TilePane(Orientation orientation, Node... children)
    {
        super(orientation, children);
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5 and the
     * specified hgap/vgap.
     * 
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     * @param children The initial set of children for this pane.
     */
    public TilePane(double hgap, double vgap, Node... children)
    {
        super(hgap, vgap, children);
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation, hgap/vgap, and
     * prefRows/prefColumns = 5.
     * 
     * @param orientation the direction the tiles should flow & wrap
     * @param hgap the amount of horizontal space between each tile
     * @param vgap the amount of vertical space between each tile
     * @param children The initial set of children for this pane.
     */
    public TilePane(Orientation orientation, double hgap, double vgap, Node... children)
    {
        super(orientation, hgap, vgap, children);
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5, hgap/vgap = 0,
     * and specified accent.
     */
    public TilePane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation,
     * prefColumn/prefRows = 5, hgap/vgap = 0, and accent.
     * 
     * @param orientation the direction the tiles should flow & wrap.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public TilePane(Orientation orientation, Accent accent)
    {
        super(orientation);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5, the specified
     * hgap/vgap, and accent.
     * 
     * @param hgap the amount of horizontal space between each tile.
     * @param vgap the amount of vertical space between each tile.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public TilePane(double hgap, double vgap, Accent accent)
    {
        super(hgap, vgap);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation, hgap/vgap,
     * prefRows/prefColumns = 5, and specified accent.
     * 
     * @param orientation the direction the tiles should flow & wrap.
     * @param hgap the amount of horizontal space between each tile.
     * @param vgap the amount of vertical space between each tile.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public TilePane(Orientation orientation, double hgap, double vgap, Accent accent)
    {
        super(orientation, hgap, vgap);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5, hgap/vgap = 0,
     * and specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public TilePane(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation,
     * prefColumn/prefRows = 5, hgap/vgap = 0, and accent.
     * 
     * @param orientation the direction the tiles should flow & wrap.
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public TilePane(Orientation orientation, Accent accent, Node... children)
    {
        super(orientation, children);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a horizontal TilePane layout with prefColumn = 5, the specified
     * hgap/vgap, and accent.
     * 
     * @param hgap the amount of horizontal space between each tile.
     * @param vgap the amount of vertical space between each tile.
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public TilePane(double hgap, double vgap, Accent accent, Node... children)
    {
        super(hgap, vgap, children);
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a TilePane layout with the specified orientation, hgap/vgap,
     * prefRows/prefColumns = 5, and accent.
     * 
     * @param orientation the direction the tiles should flow & wrap.
     * @param hgap the amount of horizontal space between each tile.
     * @param vgap the amount of vertical space between each tile.
     * @param accent The accent type used to change the layout's color scheme.
     * @param children The initial set of children for this pane.
     */
    public TilePane(Orientation orientation, double hgap, double vgap, Accent accent, Node... children)
    {
        super(orientation, hgap, vgap, children);
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
        TilePane.loadStyleSheet();
    }
}