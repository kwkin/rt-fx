package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.BooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtIconToggleButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO change some of this stuff to be in the skin instead
public class IconToggleButton extends javafx.scene.control.ToggleButton implements RtGlyph
{
    protected StackPane iconPane = new StackPane();
    protected SvgGlyph selectedIcon;
    protected SvgGlyph unselectedIcon;
    protected IconSize iconSize = IconSize.SIZE_32;
    protected IconToggleButtonStyle style = IconToggleButtonStyle.NORMAL;
    protected Accent accent = Accent.PRIMARY_MID;
    
    protected boolean isToggleText = false;
    protected String selectedText = "";
    protected String unselectedText = "";
    
    private static final String USER_AGENT_STYLESHEET = "toggle-button.css";
    private static final String CSS_CLASS = "rt-icon-toggle-button";
    
    private StyleableObjectProperty<Paint> selectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_ICON_COLOR, this, "selectedFill", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_ICON_COLOR, this, "unselectedFill", DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    
    public IconToggleButton(SvgGlyph selectedIcon, SvgGlyph unselectedIcon)
    {
        super();
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        initialize();
    }
    
    public IconToggleButton(SvgGlyph selectedIcon, SvgGlyph unselectedIcon, String text)
    {
        super();
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = text;
        initialize();
    }
    
    public IconToggleButton(SvgGlyph selectedIcon, SvgGlyph unselectedIcon, String selectedText, String unselectedText)
    {
        super();
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selectedText = selectedText;
        this.unselectedText = unselectedText;
        this.isToggleText = true;
        initialize();
    }

    public IconToggleButton(SvgGlyph selectedIcon, SvgGlyph unselectedIcon, IconToggleButtonStyle style)
    {
        super();
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.style = style;
        initialize();
    }
    
    public IconSize getIconSizes()
    {
        return this.iconSize;
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
    
    public SvgGlyph getSelectedIcon()
    {
        return this.selectedIcon;
    }
    
    public SvgGlyph getUnslectedIcon()
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

    public BooleanProperty isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.get();
    }

    public void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.set(isAnimationDisabled);
    }

    @Override
    public void setGlyphFill(Paint fill)
    {
        if (isSelected())
        {
            this.selectedIcon.setGlyphFill(fill);
        }
        else
        {
            this.unselectedIcon.setGlyphFill(fill);
        }
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
        
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
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
        
        private static final CssMetaData<IconToggleButton, Boolean> DISABLE_ANIMATION = new CssMetaData<IconToggleButton, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(IconToggleButton control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(IconToggleButton control)
            {
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ToggleButton.getClassCssMetaData());
            styleables.add(SELECTED_ICON_COLOR);
            styleables.add(UNSELECTED_ICON_COLOR);
            styleables.add(DISABLE_ANIMATION);
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
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
