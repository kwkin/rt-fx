package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.converters.PaintConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtIconToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;

// TODO change some of this stuff to be in the skin instead
public class IconToggleButton extends ToggleButton implements RtIcon
{
    protected StackPane iconPane = new StackPane();
    protected SvgIcon selectedIcon;
    protected SvgIcon unselectedIcon;
    protected IconSize iconSize = IconSize.SIZE_32;
    protected ToggleButtonStyle style = ToggleButtonStyle.ICON;
    protected Accent accent = Accent.PRIMARY_MID;

    protected boolean isToggleText = false;
    protected String selectedText = "";
    protected String unselectedText = "";

    private StyleableObjectProperty<Paint> selectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_ICON_COLOR, IconToggleButton.this, "selectedFill",
            DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_ICON_COLOR, IconToggleButton.this, "unselectedFill",
            DefaultPalette.getInstance().getBaseColor());

    // TODO complete remaining constructors
    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon)
    {
        super(ToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        initialize();
    }

    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, String text)
    {
        super(ToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = text;
        initialize();
    }

    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, String selectedText, String unselectedText)
    {
        super(ToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = selectedText;
        this.unselectedText = unselectedText;
        this.isToggleText = true;
        initialize();
    }

    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, ToggleButtonStyle style)
    {
        super(style);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.style = style;
        initialize();
    }

    public IconSize getIconSizes()
    {
        return this.iconSize;
    }

    public ToggleButtonStyle getRtStyle()
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

    public void setToggleText(boolean isToggleText)
    {
        this.isToggleText = isToggleText;
    }

    public boolean isToggleText()
    {
        return this.isToggleText;
    }

    public boolean isGlyphColorManaged()
    {
        return this.isSelected() ? this.selectedIcon.isGlyphColorManaged() : this.unselectedIcon.isGlyphColorManaged();
    }

    public void setIsGlyphColorManaged(boolean isGlyphFillManaged)
    {
        this.selectedIcon.setIsGlyphColorManaged(isGlyphFillManaged);
        this.unselectedIcon.setIsGlyphColorManaged(isGlyphFillManaged);
    }

    @Override
    public void setGlyphFill(Paint fill)
    {
        this.unselectedIcon.setGlyphFill(fill);
        this.selectedIcon.setGlyphFill(fill);
    }

    @Override
    public Paint getGlyphFill()
    {
        return isSelected() ? getSelectedFill() : getUnselectedFill();
    }

    @Override
    public double getGlyphSize()
    {
        return isSelected() ? this.selectedIcon.getGlyphSize() : this.unselectedIcon.getGlyphSize();
    }

    @Override
    public Node getGlyph()
    {
        return this;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
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
        return new RtIconToggleButtonSkin(this);
    }

    private void initialize()
    {
        // TODO add binding for the icon sizes and padding
        this.iconPane.getChildren().addAll(this.selectedIcon, this.unselectedIcon);

        this.selectedIcon.visibleProperty().bind(selectedProperty());
        this.unselectedIcon.visibleProperty().bind(selectedProperty().not());
        selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateText();
        });
        updateText();
        textProperty().addListener((ov, oldVal, newVal) ->
        {
            this.selectedText = newVal;
        });
        this.selectedFill.addListener((ov, oldVal, newVal) ->
        {
            this.selectedIcon.setGlyphFill(newVal);
        });
        this.unselectedFill.addListener((ov, oldVal, newVal) ->
        {
            this.unselectedIcon.setGlyphFill(newVal);
        });

        double width = this.selectedIcon.getGlyphSize();
        double height = this.selectedIcon.getGlyphSize();
        setIconPaneSize(width, height);
        setGraphic(this.iconPane);
    }

    private void setIconPaneSize(double width, double height)
    {
        this.iconPane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconPane.setPrefSize(width, height);
        this.iconPane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<IconToggleButton, Paint> SELECTED_ICON_COLOR = new CssMetaData<IconToggleButton, Paint>(
                "-rt-selected-icon-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(IconToggleButton control)
            {
                return control.selectedFill == null || !control.selectedFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(IconToggleButton control)
            {
                return control.selectedFill;
            }
        };

        private static final CssMetaData<IconToggleButton, Paint> UNSELECTED_ICON_COLOR = new CssMetaData<IconToggleButton, Paint>(
                "-rt-unselected-icon-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(IconToggleButton control)
            {
                return control.unselectedFill == null || !control.unselectedFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(IconToggleButton control)
            {
                return control.unselectedFill;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    ToggleButton.getClassCssMetaData());
            styleables.add(SELECTED_ICON_COLOR);
            styleables.add(UNSELECTED_ICON_COLOR);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    private void updateText()
    {
        if (!this.isToggleText)
        {
            setText(this.selectedText);
        }
        else
        {
            setText(isSelected() ? this.selectedText : this.unselectedText);
        }
    }
}
