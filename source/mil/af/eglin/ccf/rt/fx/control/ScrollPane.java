package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ScrollPane extends javafx.scene.control.ScrollPane
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "scroll-pane.css";
    private static final String CSS_CLASS = "rt-scroll-pane";

    private static final StyleablePropertyFactory<IconButton> FACTORY =
        new StyleablePropertyFactory<>(javafx.scene.control.Label.getClassCssMetaData());
    
    public ScrollPane()
    {
        super();
        initialize();
    }
    
    public ScrollPane(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ScrollPane(Node content)
    {
        super(content);
        initialize();
    }
    
    public ScrollPane(Node content, Accent accent)
    {
        super(content);
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
    
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        ScrollPane.loadStyleSheet();
    }
}
