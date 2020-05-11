package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class TilePane extends javafx.scene.layout.TilePane
{
    protected Accent accent;
    
    private static final String USER_AGENT_STYLESHEET = "tile-pane.css";
    private static final String CSS_CLASS = "rt-tile-pane";

    public TilePane()
    {
        super();
        initialize();
    }

    public TilePane(Orientation orientation)
    {
        super(orientation);
        initialize();
    }

    public TilePane(double hgap, double vgap)
    {
        super(hgap, vgap);
        initialize();
    }

    public TilePane(Orientation orientation, double hgap, double vgap)
    {
        super(orientation, hgap, vgap);
        initialize();
    }

    public TilePane(Node... children)
    {
        super(children);
        initialize();
    }

    public TilePane(Orientation orientation, Node... children)
    {
        super(orientation, children);
        initialize();
    }

    public TilePane(double hgap, double vgap, Node... children)
    {
        super(hgap, vgap, children);
        initialize();
    }

    public TilePane(Orientation orientation, double hgap, double vgap, Node... children)
    {
        super(orientation, hgap, vgap, children);
        initialize();
    }

    public TilePane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public TilePane(Orientation orientation, Accent accent)
    {
        super(orientation);
        this.accent = accent;
        initialize();
    }

    public TilePane(double hgap, double vgap, Accent accent)
    {
        super(hgap, vgap);
        this.accent = accent;
        initialize();
    }

    public TilePane(Orientation orientation, double hgap, double vgap, Accent accent)
    {
        super(orientation, hgap, vgap);
        this.accent = accent;
        initialize();
    }

    public TilePane(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    public TilePane(Orientation orientation, Accent accent, Node... children)
    {
        super(orientation, children);
        this.accent = accent;
        initialize();
    }

    public TilePane(double hgap, double vgap, Accent accent, Node... children)
    {
        super(hgap, vgap, children);
        this.accent = accent;
        initialize();
    }

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