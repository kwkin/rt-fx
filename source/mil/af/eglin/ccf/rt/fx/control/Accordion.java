package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * An accordion is a group of {@link TitledPane TitledPanes}, where only one
 * pane can be opened at a time.
 */
public class Accordion extends javafx.scene.control.Accordion
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "accordion.css";
    private static final String CSS_CLASS = "rt-accordion";

    private static final StyleablePropertyFactory<Button> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.Accordion.getClassCssMetaData());

    /**
     * Creates an empty {@code Accordion}
     */
    public Accordion()
    {
        super();
        initialize();
    }

    /**
     * Creates an {@code Accordion} with the provided titled panes
     * 
     * @param titledPanes the titled panes to show inside the accordion
     */
    public Accordion(TitledPane... titledPanes)
    {
        super(titledPanes);
        initialize();
    }

    /**
     * Creates an {@code Accordion} with the provided accent
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public Accordion(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an {@code Accordion} with the provided accent and titled panes
     * 
     * @param accent the accent used to change the component's color scheme
     * @param titledPanes the titled panes to show inside the accordion
     */
    public Accordion(Accent accent, TitledPane... titledPanes)
    {
        super(titledPanes);
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
            getStyleClass().add(this.accent.getStyleClassName());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
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
        Accordion.loadStyleSheet();
    }
}
