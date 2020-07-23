package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * Tooltips show information about a node when the node is hovered over
 */
public class Tooltip extends javafx.scene.control.Tooltip
{
    private static final String USER_AGENT_STYLESHEET = "tool-tip.css";
    private static final String CSS_CLASS = "rt-tool-tip";

    private static final StyleablePropertyFactory<Tooltip> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.Tooltip.getClassCssMetaData());

    /**
     * Creates an emppty {@code Tooltip}
     */
    public Tooltip()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code Tooltip} with the specified text
     * 
     * @param text a text string for the tooltip
     */
    public Tooltip(String text)
    {
        super(text);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return FACTORY.getCssMetaData();
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        Tooltip.loadStyleSheet();
    }
}
