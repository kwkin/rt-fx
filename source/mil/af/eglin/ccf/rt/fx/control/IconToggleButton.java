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
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtIconToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;

public class IconToggleButton extends ToggleButton implements RtIcon
{
    protected ToggleButtonStyle style = ToggleButtonStyle.ICON;
    protected Accent accent = Accent.PRIMARY_MID;

    protected SvgIcon selectedIcon;
    protected SvgIcon unselectedIcon;
    protected String selectedText = "";
    protected String unselectedText = "";

    private StyleableObjectProperty<Paint> selectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_ICON_COLOR, IconToggleButton.this, "selectedFill",
            DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_ICON_COLOR, IconToggleButton.this, "unselectedFill",
            DefaultPalette.getInstance().getBaseColor());

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
        this.unselectedText = text;
        initialize();
    }

    public IconToggleButton(SvgIcon selectedIcon, SvgIcon unselectedIcon, String selectedText, String unselectedText)
    {
        super(ToggleButtonStyle.ICON);
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = selectedText;
        this.unselectedText = unselectedText;
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

    public boolean isColorManaged()
    {
        return this.isSelected() ? this.selectedIcon.isColorManaged() : this.unselectedIcon.isColorManaged();
    }

    public void setIsColorManaged(boolean isFillManaged)
    {
        this.selectedIcon.setIsColorManaged(isFillManaged);
        this.unselectedIcon.setIsColorManaged(isFillManaged);
    }

    @Override
    public void setFill(Paint fill)
    {
        this.unselectedIcon.setFill(fill);
        this.selectedIcon.setFill(fill);
    }

    @Override
    public Paint getFill()
    {
        return isSelected() ? getSelectedFill() : getUnselectedFill();
    }

    @Override
    public double getSize()
    {
        return isSelected() ? this.selectedIcon.getSize() : this.unselectedIcon.getSize();
    }

    @Override
    public Node getNode()
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
        textProperty().addListener((ov, oldVal, newVal) ->
        {
            this.selectedText = newVal;
            this.unselectedText = newVal;
        });
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
}
