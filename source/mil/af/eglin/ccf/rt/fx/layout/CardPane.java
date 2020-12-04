package mil.af.eglin.ccf.rt.fx.layout;

import com.sun.javafx.css.StyleManager;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class CardPane extends StackPane
{
    protected Accent accent = Accent.BASE;
    
    private static final String USER_AGENT_STYLESHEET = "card-pane.css";
    private static final String CSS_CLASS = "rt-card-pane";

    /**
     * Creates an empty card pane.
     */
    public CardPane()
    {
        super();
        initialize();
    }

    /**
     * Creates an empty card pane with the specified accent.
     * 
     * @param accent The accent type used to change the layout's color scheme.
     */
    public CardPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a card pane with the specified content.
     * 
     * @param content The content to place inside the card layout.
     */
    public CardPane(Node content)
    {
        super();
        initialize();
    }

    /**
     * Creates a card pane with the specified content and accent.
     * 
     * @param content The content to place inside the card layout.
     * @param accent The accent type used to change the layout's color scheme.
     */
    public CardPane(Node content, Accent accent)
    {
        super();
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
        CardPane.loadStyleSheet();
    }
}
