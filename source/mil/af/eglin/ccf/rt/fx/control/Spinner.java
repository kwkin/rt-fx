package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A spinner allows the user to enter and edit text, or select the value from an ordered sequence
 * 
 * @param <T> the type of value that can be iterated through
 */
public class Spinner<T> extends javafx.scene.control.Spinner<T> implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "spinner.css";
    private static final String CSS_CLASS = "rt-spinner";

    private static final StyleablePropertyFactory<CheckBox> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.Spinner.getClassCssMetaData());

    /**
     * Creates an empty {@code Spinner} with a non-editable text editor
     */
    public Spinner()
    {
        super();
        initialize();
    }

    /**
     * Creates an empty {@code Spinner} with the provided accent and a non-editable text editor
     */
    public Spinner(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates an {@code Spinner} with the provided value factory
     * 
     * @param valueFactory the value factory to use
     */
    public Spinner(SpinnerValueFactory<T> valueFactory)
    {
        super(valueFactory);
        initialize();
    }


    /**
     * Creates an {@code Spinner} with the provided value factory and accent
     * 
     * @param valueFactory the value factory to use
     * @param accent the accent used to change the component's color scheme
     */
    public Spinner(SpinnerValueFactory<T> valueFactory, Accent accent)
    {
        super(valueFactory);
        this.accent = accent;
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
        getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
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
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
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
        Spinner.loadStyleSheet();
    }
}
