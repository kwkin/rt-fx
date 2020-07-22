package mil.af.eglin.ccf.rt.fx.control;

import java.time.LocalDate;
import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class DatePicker extends javafx.scene.control.DatePicker implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "date-picker.css";
    private static final String CSS_CLASS = "rt-date-picker";

    private static final StyleablePropertyFactory<ColorPicker> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.DatePicker.getClassCssMetaData());
    
    /**
     * Creates a {@code DatePicker} with no date initialized
     */
    public DatePicker()
    {
        super();
        initialize();
    }
    
    /**
     * Creates a {@code DatePicker} with the specified accent and no date initialized
     * 
     * @param accent the accent used to change the component's color scheme
     */
    public DatePicker(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a {@code DatePicker} initialized with the provided date
     *
     * @param localDate the initial date
     */
    public DatePicker(LocalDate localDate)
    {
        super(localDate);
        initialize();
    }


    /**
     * Creates a {@code DatePicker} initialized with the provided date and accent
     *
     * @param localDate the initial date
     * @param accent the accent used to change the component's color scheme
     */
    public DatePicker(LocalDate localDate, Accent accent)
    {
        super(localDate);
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
        DatePicker.loadStyleSheet();
    }
}
