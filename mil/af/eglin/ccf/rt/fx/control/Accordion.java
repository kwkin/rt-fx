package mil.af.eglin.ccf.rt.fx.control;

import javafx.scene.control.Skin;
import javafx.scene.control.TitledPane;
import mil.af.eglin.ccf.rt.fx.control.skins.RtAccordionSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Accordion extends javafx.scene.control.Accordion
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "titled-pane.css";
    private static final String CSS_CLASS = "rt-titled-pane";
    
    public Accordion()
    {
        super();
        initialize();
    }

    public Accordion(TitledPane... titledPanes)
    {
        super(titledPanes);
        initialize();
    }
    
    public Accordion(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Accordion(Accent accent, TitledPane... titledPanes)
    {
        super(titledPanes);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtAccordionSkin(this);
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
        if (this.accent != null)
        {
            getStyleClass().add(this.accent.getCssName());
        }
    }
}
