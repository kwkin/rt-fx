package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleablePropertyFactory;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * Choice boxes allow the user to select one or more options from a list of
 * options
 * <p>
 * A choice box is typically skinned as a text entry with a drop down button.
 * Selecting the drop down button will open a list of selectable options on top
 * of the choice box.
 * 
 * @param <T> the type of object contained in the choice box
 */
public class ChoiceBox<T> extends javafx.scene.control.ChoiceBox<T> implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-choice-box";

    private static final StyleablePropertyFactory<Button> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.ChoiceBox.getClassCssMetaData());

    /**
     * Creates an empty {@code ChoiceBox}
     */
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
        ChoiceBox.loadStyleSheet();
    }
}
