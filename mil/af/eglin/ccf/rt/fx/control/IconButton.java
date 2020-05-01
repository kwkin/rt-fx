package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
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

// TODO add armed color before the icon
// TODO if text is added, place below the icon
public class IconButton extends Button
{
    protected StackPane iconPane = new StackPane();
    protected Accent accent = Accent.BASE_MID;
    protected SvgIcon icon;
    
    private static final String USER_AGENT_STYLESHEET = "button.css";

    private StyleableObjectProperty<Paint> iconFill = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_ICON_COLOR, this, "selectedFill", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Boolean> isAnimationDisabled = new SimpleStyleableObjectProperty<>(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    
    public IconButton(SvgIcon icon)
    {
        super(ButtonStyle.ICON);
        this.icon = icon;
        initialize();
    }
    
    public IconButton(SvgIcon icon, String text)
    {
        super(text, ButtonStyle.ICON);
        this.icon = icon;
        initialize();
    }
    
    public SvgIcon getIcon()
    {
        return this.icon;
    }

    public StyleableObjectProperty<Paint> iconFillProperty()
    {
        return this.iconFill;
    }

    public void setIconFill(Paint color)
    {
        this.iconFill.setValue(color);
    }

    public Paint getIconFill()
    {
        return iconFill.getValue();
    }

    public StyleableObjectProperty<Boolean> isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.getValue();
    }

    public void setIsAnimationDisabled(boolean isAnimationDisabled)
    {
        this.isAnimationDisabled.setValue(isAnimationDisabled);
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
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
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
        double width = this.icon.getSize();
        double height = this.icon.getSize();
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
                return control.iconFill == null || !control.iconFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(IconButton control)
            {
                return control.iconFill;
            }
        };
        
        private static final CssMetaData<IconButton, Boolean> DISABLE_ANIMATION = new CssMetaData<IconButton, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(IconButton control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(IconButton control)
            {
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    Button.getClassCssMetaData());
            // @formatter:off
            Collections.addAll(styleables, 
                    SELECTED_ICON_COLOR,
                    DISABLE_ANIMATION);
            // @formatter:on
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}
