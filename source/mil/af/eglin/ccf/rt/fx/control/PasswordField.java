package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A text field that masks entered characters
 */
public class PasswordField extends javafx.scene.control.PasswordField implements RtStyleableComponent
{
    /**
     * Creates an empty {@code PasswordField}
     */
    public PasswordField()
    {
        super();
        initialize();
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }
    
    /*************************************************************************
     *                                                                       *
     * CSS Properties                                                        *
     *                                                                       *
     ************************************************************************/

    private static final StyleablePropertyFactory<PasswordField> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.PasswordField.getClassCssMetaData());

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
    
    
    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/

    private static final String USER_AGENT_STYLESHEET = "text-field.css";
    private static final String CSS_CLASS = "rt-text-field";
    
    protected Accent accent = Accent.PRIMARY_MID;

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
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
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    static
    {
        PasswordField.loadStyleSheet();
    }
}
