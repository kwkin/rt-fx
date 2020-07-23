package mil.af.eglin.ccf.rt.fx.control;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtIconToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A bi-state selection control allowing the user to toggle options.
 */
public class IconToggleButton extends ToggleButton implements Icon
{
    protected IconToggleButtonStyle style = IconToggleButtonStyle.ICON;
    protected Accent accent = Accent.PRIMARY_MID;

    protected SvgIcon selectedIcon;
    protected SvgIcon unselectedIcon;
    protected String selectedText = "";
    protected String unselectedText = "";

    private static final String USER_AGENT_STYLESHEET = "button.css";
    
    private static final StyleablePropertyFactory<IconToggleButton> FACTORY =
        new StyleablePropertyFactory<>(ToggleButton.getClassCssMetaData());

    private static final CssMetaData<IconToggleButton, Paint> SELECTED_ICON_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-selected-fill", s -> s.selectedFill, DefaultPalette.getInstance().getAccentColor(), false);

    private static final CssMetaData<IconToggleButton, Paint> UNSELECTED_ICON_COLOR = 
            FACTORY.createPaintCssMetaData("-rt-unselected-fill", s -> s.unselectedFill, DefaultPalette.getInstance().getBaseColor(), false);

    private StyleableObjectProperty<Paint> selectedFill = new SimpleStyleableObjectProperty<>(
            SELECTED_ICON_COLOR, this, "selectedFill");
    private StyleableObjectProperty<Paint> unselectedFill = new SimpleStyleableObjectProperty<>(
            UNSELECTED_ICON_COLOR, this, "unselectedFill");

    /**
     * Creates a {@code IconToggleButton} with the provided select and unselected icons 
     * 
     * @param selectedIcon the button's icon when in the selected state
     * @param unselectedIcon the button's icon when in the unselected state
     */
    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon)
    {
        super(IconToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        initialize();
    }

    /**
     * Creates a {@code IconToggleButton} with the provided text and select and unselected icons 
     * 
     * @param selectedIcon the button's icon when in the selected state
     * @param unselectedIcon the button's icon when in the unselected state
     * @param text the text to use when the button is in both the selected and unselected states
     */
    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, String text)
    {
        super(IconToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = text;
        this.unselectedText = text;
        initialize();
    }

    /**
     * Creates a {@code IconToggleButton} with the provided select and unselected icons and text
     * 
     * @param selectedIcon the button's icon when selected
     * @param unselectedIcon the button's icon when unselected
     * @param selectedText the text to use when the button is selected
     * @param unselectedText the text to use when the button is unselected
     */
    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, String selectedText, String unselectedText)
    {
        super(IconToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = selectedText;
        this.unselectedText = unselectedText;
        initialize();
    }

    /**
     * Creates a {@code IconToggleButton} with the provided select and unselected icons
     * 
     * @param selectedIcon the button's icon when selected
     * @param unselectedIcon the button's icon when unselected
     * @param style the style used to change the overall look of the icon toggle button
     */
    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, IconToggleButtonStyle style)
    {
        super(style);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.style = style;
        initialize();
    }
    
    public IconToggleButtonStyle getRtStyle()
    {
        return this.style;
    }

    public StyleableObjectProperty<Paint> selectedFillProperty()
    {
        return this.selectedFill;
    }

    public void setSelectedFill(Paint color)
    {
        this.selectedFill.set(color);
    }

    public Paint getSelectedFill()
    {
        return selectedFill.get();
    }

    public StyleableObjectProperty<Paint> unselectedFillProperty()
    {
        return this.unselectedFill;
    }

    public void setUnselectedFill(Paint color)
    {
        this.unselectedFill.set(color);
    }

    public Paint getUnselectedFill()
    {
        return unselectedFill.get();
    }

    public SvgIcon getSelectedIcon()
    {
        return this.selectedIcon;
    }

    public SvgIcon getUnslectedIcon()
    {
        return this.unselectedIcon;
    }

    public void setSelectedText(String selectedText)
    {
        this.selectedText = selectedText;
    }

    public String getSelectedText()
    {
        return this.selectedText;
    }

    public void setUnselectedText(String unselectedText)
    {
        this.unselectedText = unselectedText;
    }

    public String getUnselectedText()
    {
        return this.unselectedText;
    }

    public boolean isColorManaged()
    {
        return this.isSelected() ? this.selectedIcon.isColorManaged() : this.unselectedIcon.isColorManaged();
    }

    public void setIsColorManaged(boolean isFillManaged)
    {
        this.selectedIcon.setIsColorManaged(isFillManaged);
        this.unselectedIcon.setIsColorManaged(isFillManaged);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFill(Paint fill)
    {
        this.unselectedIcon.setFill(fill);
        this.selectedIcon.setFill(fill);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Paint getFill()
    {
        return isSelected() ? getSelectedFill() : getUnselectedFill();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSize()
    {
        return isSelected() ? this.selectedIcon.getSize() : this.unselectedIcon.getSize();
    }

    /**
     * {@inheritDoc}
     */
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
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() 
     {
        return FACTORY.getCssMetaData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtIconToggleButtonSkin(this);
    }

    private void initialize()
    {
        textProperty().addListener((ov, oldVal, newVal) ->
        {
            this.selectedText = newVal;
            this.unselectedText = newVal;
        });
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
