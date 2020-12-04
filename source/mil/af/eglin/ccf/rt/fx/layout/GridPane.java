package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class GridPane extends javafx.scene.layout.GridPane
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "grid-pane.css";
    private static final String CSS_CLASS = "rt-grid-pane";

    /**
     * Creates a GridPane layout with hgap/vgap = 0 and TOP_LEFT alignment.
     */
    public GridPane()
    {
        super();
        initialize();
    }

    /**
     * Creates a GridPane layout with hgap/vgap = 0, TOP_LEFT alignment, and the
     * specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public GridPane(Accent accent)
    {
        super();
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
        getStyleClass().add(this.accent.getStyleClassName());
    }

    /**
     * Loads the user agent stylesheet specific to this layout
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadLayouts(USER_AGENT_STYLESHEET));
    }

    static
    {
        GridPane.loadStyleSheet();
    }
}
