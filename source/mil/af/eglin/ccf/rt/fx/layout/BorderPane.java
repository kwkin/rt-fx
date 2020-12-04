package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A border pane lays out children in the top, left, right, bottom, and center
 * positions.
 */
public class BorderPane extends javafx.scene.layout.BorderPane
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "border-pane.css";
    private static final String CSS_CLASS = "rt-border-pane";

    /**
     * Creates a BorderPane layout.
     */
    public BorderPane()
    {
        super();
        initialize();
    }

    /**
     * Creates an BorderPane layout with the given Node as the center of the
     * BorderPane.
     * 
     * @param center The node to set as the center of the BorderPane.
     */
    public BorderPane(Node center)
    {
        super(center);
        initialize();
    }

    /**
     * Creates a BorderPane layout with the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public BorderPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an BorderPane layout with the specified center node and accent.
     * 
     * @param node The node to set as the center of the BorderPane.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public BorderPane(Node node, Accent accent)
    {
        super();
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
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadLayouts(USER_AGENT_STYLESHEET));
    }

    static
    {
        BorderPane.loadStyleSheet();
    }
}