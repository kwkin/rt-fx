package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class FlowPane extends javafx.scene.layout.FlowPane
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "flow-pane.css";
    private static final String CSS_CLASS = "rt-flow-pane";

    public FlowPane()
    {
        super();
        initialize();
    }

    public FlowPane(Orientation orientation)
    {
        super(orientation);
        initialize();
    }

    public FlowPane(double hgap, double vgap)
    {
        super(hgap, vgap);
        initialize();
    }

    public FlowPane(Orientation orientation, double hgap, double vgap)
    {
        super(orientation, hgap, vgap);
        initialize();
    }

    public FlowPane(Node... children)
    {
        super(children);
        initialize();
    }

    public FlowPane(Orientation orientation, Node... children)
    {
        super(orientation, children);
        initialize();
    }

    public FlowPane(double hgap, double vgap, Node... children)
    {
        super(hgap, vgap, children);
        initialize();
    }

    public FlowPane(Orientation orientation, double hgap, double vgap, Node... children)
    {
        super(orientation, hgap, vgap, children);
        initialize();
    }

    public FlowPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public FlowPane(Orientation orientation, Accent accent)
    {
        super(orientation);
        this.accent = accent;
        initialize();
    }

    public FlowPane(double hgap, double vgap, Accent accent)
    {
        super(hgap, vgap);
        this.accent = accent;
        initialize();
    }

    public FlowPane(Orientation orientation, double hgap, double vgap, Accent accent)
    {
        super(orientation, hgap, vgap);
        this.accent = accent;
        initialize();
    }

    public FlowPane(Accent accent, Node... children)
    {
        super(children);
        this.accent = accent;
        initialize();
    }

    public FlowPane(Orientation orientation, Accent accent, Node... children)
    {
        super(orientation, children);
        this.accent = accent;
        initialize();
    }

    public FlowPane(double hgap, double vgap, Accent accent, Node... children)
    {
        super(hgap, vgap, children);
        this.accent = accent;
        initialize();
    }

    public FlowPane(Orientation orientation, double hgap, double vgap, Accent accent, Node... children)
    {
        super(orientation, hgap, vgap, children);
        this.accent = accent;
        initialize();
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
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    static
    {
        FlowPane.loadStyleSheet();
    }
}
