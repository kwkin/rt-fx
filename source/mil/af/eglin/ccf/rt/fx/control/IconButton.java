package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
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

// TODO rename all instances of glyph to icon

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
public class IconButton extends Button implements RtGlyph
{
    protected StackPane iconPane = new StackPane();
    protected Accent accent = Accent.BASE;
    protected SvgIcon icon;

    private static final String USER_AGENT_STYLESHEET = "button.css";

    /**
     * The color of the glyph when the button is armed.
     * <p>
     * When not armed, the glyph will retain the color speci
     */
    private StyleableObjectProperty<Paint> selectedGlyphFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_ICON_COLOR, this, "selectedFill",
            DefaultPalette.getInstance().getAccentColor());

    // TODO change SvgGlyph to use RtGlyph. Change curent solution for RtGlyph
    // to RtTextFieldIcon
    /**
     * Creates an icon button with the specified icon as its label.
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
     * Creates an icon button with the specified icon and text as its label.
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
     * Creates an icon button with the specified icon and text as its label.
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

    public final StyleableObjectProperty<Paint> selectedGlyphFillProperty()
    {
        return this.selectedGlyphFill;
    }

    public final void setSelectedGlyphFill(Paint fill)
    {
        this.selectedGlyphFill.set(fill);
    }

    public final Paint getSelectedGlyphFill()
    {
        return this.selectedGlyphFill.get();
    }

    public boolean isGlyphColorManaged()
    {
        return this.icon.isGlyphColorManaged();
    }

    public void setIsGlyphColorManaged(boolean isGlyphFillManaged)
    {
        this.icon.setIsGlyphColorManaged(isGlyphFillManaged);
    }

    @Override
    public void setGlyphFill(Paint fill)
    {
        this.icon.setGlyphFill(fill);
    }

    @Override
    public Paint getGlyphFill()
    {
        return this.icon.getGlyphFill();
    }

    @Override
    public double getGlyphSize()
    {
        return this.icon.getGlyphSize();
    }

    @Override
    public Node getGlyph()
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

    private void initialize()
    {
        this.iconPane.getChildren().addAll(this.icon);
        double width = this.icon.getGlyphSize();
        double height = this.icon.getGlyphSize();
        setIconPaneSize(width, height);
        setGraphic(this.iconPane);

        getStyleClass().add(this.accent.getCssName());
    }

    private void setIconPaneSize(double width, double height)
    {
        this.iconPane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconPane.setPrefSize(width, height);
        this.iconPane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<IconButton, Paint> SELECTED_ICON_COLOR = new CssMetaData<IconButton, Paint>(
                "-rt-selected-icon-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(IconButton control)
            {
                return control.selectedGlyphFill == null || !control.selectedGlyphFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(IconButton control)
            {
                return control.selectedGlyphFill;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Button.getClassCssMetaData());
            styleables.add(SELECTED_ICON_COLOR);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
