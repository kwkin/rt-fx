package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class AnchorPane extends javafx.scene.layout.AnchorPane
{
    protected Accent accent = Accent.BASE;
    
    private static final String USER_AGENT_STYLESHEET = "anchor-pane.css";
    private static final String CSS_CLASS = "rt-anchor-pane";

    /**
     * Creates an AnchorPane layout.
     */
    public AnchorPane()
    {
        super();
        initialize();
    }

    /**
     * Creates an AnchorPane layout with the specified accent.
     */
    public AnchorPane(Accent accent)
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
        AnchorPane.loadStyleSheet();
    }
}