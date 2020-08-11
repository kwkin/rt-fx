package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;

/**
 * An item intended to be used in conjunction with {@link Menu} to provid options to users.
 */
public class MenuItem extends javafx.scene.control.MenuItem implements RtStyleableComponent
{
    /**
     * Creates a {@code MenuItem} with no text
     */
    public MenuItem()
    {
        super();
        initialize();
    }

    /**
     * Creates a {@code MenuItem} with the provided text to display
     * 
     * @param text the text to display
     */
    public MenuItem(String text)
    {
        super(text);
        initialize();
    }


    /**
     * Creates a {@code MenuItem} with the provided text and graphic
     * 
     * @param text the text to display
     * @param graphic the node to display
     */
    public MenuItem(String text, Node graphic)
    {
        super(text, graphic);
        initialize();
    }


    /**
     * Creates a {@code MenuItem} with the provided text and accent
     * 
     * @param text the text to display
     * @param accent the accent used to change the component's color scheme
     */
    public MenuItem(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }



    /**
     * Creates a {@code MenuItem} with the provided text, graphic, and accent
     * 
     * @param text the text to display
     * @param graphic the node to display
     * @param accent the accent used to change the component's color scheme
     */
    public MenuItem(String text, Node graphic, Accent accent)
    {
        super(text, graphic);
        initialize();
    }
    
    private void initialize()
    {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }

    /*************************************************************************
     *                                                                       *
     * CSS Loading                                                           *
     *                                                                       *
     ************************************************************************/
    
    private static final String DEFAULT_STYLE_CLASS = "rt-menu-item";
    
    protected Accent accent = Accent.PRIMARY_MID;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return DEFAULT_STYLE_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }
}
