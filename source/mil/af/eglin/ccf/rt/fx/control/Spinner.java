package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Spinner<T> extends javafx.scene.control.Spinner<T> implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "spinner.css";
    private static final String CSS_CLASS = "rt-spinner";

    public Spinner()
    {
        super();
        initialize();
    }
    
    public Spinner(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public Spinner(SpinnerValueFactory<T> valueFactory)
    {
        super(valueFactory);
        initialize();
    }
    
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
        getStyleClass().add(this.accent.getCssName());
        getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
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
