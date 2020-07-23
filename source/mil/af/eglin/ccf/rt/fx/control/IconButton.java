package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A simple button control allows the user to take actions with a single press.
 * <p>
 * An icon button contains an icon with an optional text string. Icon buttons
 * appear to be build into the surface.
 * <p>
 * The icon button is similar to the {@link Button Button} class, but contains
 * additional API for styling and using the icon inside the button.
 * 
 * @see Button
 */
public class IconButton extends Button implements Icon
{
    protected StackPane iconPane = new StackPane();
    protected Accent accent = Accent.BASE;
    protected SvgIcon icon;

    private static final String USER_AGENT_STYLESHEET = "button.css";

    private static final StyleablePropertyFactory<IconButton> FACTORY =
        new StyleablePropertyFactory<>(Button.getClassCssMetaData());

    private static final CssMetaData<IconButton, Paint> SELECTED_ICON_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-disable-animation", s -> s.selectedIconFill, DefaultPalette.getInstance().getAccentColor(), false);

    /**
     * The color of the icon when the button is armed.
     * <p>
     * When not armed, the icon will retain the color specified by the icon
     */
    private StyleableObjectProperty<Paint> selectedIconFill = new SimpleStyleableObjectProperty<>(
            SELECTED_ICON_COLOR, this, "selectedFill");

    /**
     * Creates an {@code IconButton} with the specified icon as its label.
     * 
     * @param icon The icon for its label
     */
    public IconButton(SvgIcon icon)
    {
        super(ButtonStyle.ICON);
        this.icon = icon;
        initialize();
    }

    /**
     * Creates an {@code IconButton} with the specified icon and text as its label.
     * 
     * @param icon The icon for its label
     * @param text The text string for its label
     */
    public IconButton(SvgIcon icon, String text)
    {
        super(text, ButtonStyle.ICON);
        this.icon = icon;
        initialize();
    }

    /**
     * Creates an {@code IconButton} with the specified icon and text as its label.
     * 
     * @param icon The icon for its label
     * @param text The text string for its label
     * @param display The position of the text relative to the icon
     */
    public IconButton(SvgIcon icon, String text, ContentDisplay display)
    {
        super(text, ButtonStyle.ICON);
        this.icon = icon;
        setContentDisplay(display);
        initialize();
    }

    public SvgIcon getIcon()
    {
        return this.icon;
    }

    public final StyleableObjectProperty<Paint> selectedIconFillProperty()
    {
        return this.selectedIconFill;
    }

    public final void setSelectedFill(Paint fill)
    {
        this.selectedIconFill.set(fill);
    }

    public final Paint getSelectedIconFill()
    {
        return this.selectedIconFill.get();
    }

    public boolean isColorManaged()
    {
        return this.icon.isColorManaged();
    }

    public void setIsColorManaged(boolean isFillManaged)
    {
        this.icon.setIsColorManaged(isFillManaged);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFill(Paint fill)
    {
        this.icon.setFill(fill);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paint getFill()
    {
        return this.icon.getFill();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSize()
    {
        return this.icon.getSize();
    }

    @Override
    public Node getNode()
    {
        return this;
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
    protected Skin<?> createDefaultSkin()
    {
        return new RtButtonSkin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() 
     {
        return FACTORY.getCssMetaData();
    }

    private void initialize()
    {
        this.iconPane.getChildren().addAll(this.icon);
        double width = this.icon.getSize();
        double height = this.icon.getSize();
        setIconPaneSize(width, height);
        setGraphic(this.iconPane);

        getStyleClass().add(this.accent.getStyleClassName());
    }

    private void setIconPaneSize(double width, double height)
    {
        this.iconPane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconPane.setPrefSize(width, height);
        this.iconPane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
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
        IconButton.loadStyleSheet();
    }
}
