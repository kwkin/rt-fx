package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A button bar allows one or more {@link Button Buttons} to be automatically
 * spaced horizontally.
 */
public class ButtonBar extends javafx.scene.control.ButtonBar implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "button-bar.css";
    private static final String CSS_CLASS = "rt-button-bar";

    private static final StyleablePropertyFactory<Button> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ButtonBar.getClassCssMetaData());

    /**
     * Creates an empty {@code ButtonBar} using the default properties for the user's operating system.
     */
    public ButtonBar()
    {
        super();
        initialize();
    }

    /**
     * Creates an empty {@code ButtonBar} with the given button order.
     * 
     * @param buttonOrder the button order
     */
    public ButtonBar(final String buttonOrder)
    {
        super(buttonOrder);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
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
        ButtonBar.loadStyleSheet();
    }
}
