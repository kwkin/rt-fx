package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ChoiceBox<T> extends javafx.scene.control.ChoiceBox<T> implements RtStyleableComponent 
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-choice-box";

    public ChoiceBox()
    {
        super();
        initialize();
    }
    
    public ChoiceBox(Accent accent) 
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ChoiceBox(ObservableList<T> items) 
    {
        super(items);
        initialize();
    }
    
    public ChoiceBox(ObservableList<T> items, Accent accent) 
    {
        super(items);
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
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        ChoiceBox.loadStyleSheet();
    }
}
