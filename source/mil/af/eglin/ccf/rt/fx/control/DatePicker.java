package mil.af.eglin.ccf.rt.fx.control;

import java.time.LocalDate;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class DatePicker extends javafx.scene.control.DatePicker implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "date-picker.css";
    private static final String CSS_CLASS = "rt-date-picker";

    public DatePicker()
    {
        super();
        initialize();
    }
    
    public DatePicker(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public DatePicker(LocalDate localDate)
    {
        super(localDate);
        initialize();
    }
    
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
        getStyleClass().add(this.accent.getCssName());
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
