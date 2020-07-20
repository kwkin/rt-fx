package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.skins.RtProgressBarSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ProgressBar extends javafx.scene.control.ProgressBar implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "progress-bar.css";
    private static final String CSS_CLASS = "rt-progress-bar";

    private static final StyleablePropertyFactory<IconButton> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.Label.getClassCssMetaData());
    
    public ProgressBar()
    {
        super();
        initialize();
    }
    
    public ProgressBar(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public ProgressBar(double progress)
    {
        super(progress);
        initialize();
    }

    public ProgressBar(double progress, Accent accent)
    {
        super(progress);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtProgressBarSkin(this);
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
    
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    static
    {
        ProgressBar.loadStyleSheet();
    }
}
