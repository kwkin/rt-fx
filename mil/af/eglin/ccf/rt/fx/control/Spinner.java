package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.control.SpinnerValueFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.SpinnerStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Spinner<T> extends javafx.scene.control.Spinner<T> implements RtComponent
{
    protected SpinnerStyle style = SpinnerStyle.RAISED;
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "spinner.css";
    private static final String CSS_CLASS = "rt-spinner";

    public Spinner()
    {
        super();
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
    
    public SpinnerStyle getSpinnerStyle()
    {
        return this.style;
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
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() 
    {
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
        getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
    }
}
