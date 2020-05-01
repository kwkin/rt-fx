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
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtToggleSwitchSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ToggleSwitch extends javafx.scene.control.ToggleButton implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "toggle-switch.css";
    private static final String CSS_CLASS = "rt-toggle-switch";

    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, this, "selectedColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, this, "unselectedColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> selectedLineColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_LINE_COLOR, this, "selectedLineColor", DefaultPalette.getInstance().getLightAccentColor());
    private StyleableObjectProperty<Paint> unselectedLineColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_LINE_COLOR, this, "unselectedLineColor", DefaultPalette.getInstance().getLightBaseColor());
    private StyleableObjectProperty<Boolean> isAnimationDisabled = new SimpleStyleableObjectProperty<>(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);

    public ToggleSwitch()
    {
        super();
        initialize();
    }

    public ToggleSwitch(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public ToggleSwitch(String text)
    {
        super(text);
        initialize();
    }

    public ToggleSwitch(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public StyleableObjectProperty<Paint> selectedColorProperty()
    {
        return this.selectedColor;
    }

    public Paint getSelectedColor()
    {
        return selectedColor.getValue();
    }

    public void setSelectedColor(Paint color)
    {
        this.selectedColor.setValue(color);
    }

    public StyleableObjectProperty<Paint> unselectedColorProperty()
    {
        return this.unselectedColor;
    }

    public Paint getUnselectedColor()
    {
        return unselectedColor.getValue();
    }

    public void setUnselectedColor(Paint color)
    {
        this.unselectedColor.setValue(color);
    }

    public StyleableObjectProperty<Paint> selectedLineColorProperty()
    {
        return this.selectedLineColor;
    }

    public Paint getSelectedLineColor()
    {
        return selectedLineColor.getValue();
    }

    public void setSelectedLineColor(Paint color)
    {
        this.selectedLineColor.setValue(color);
    }

    public StyleableObjectProperty<Paint> unselectedLineColorProperty()
    {
        return this.unselectedLineColor;
    }

    public Paint getUnselectedLineColor()
    {
        return unselectedLineColor.getValue();
    }

    public void setUnselectedLineColor(Paint color)
    {
        this.unselectedLineColor.setValue(color);
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
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
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
    protected Skin<?> createDefaultSkin()
    {
        return new RtToggleSwitchSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<ToggleSwitch, Paint> SELECTED_COLOR = new CssMetaData<ToggleSwitch, Paint>(
                "-rt-selected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ToggleSwitch control)
            {
                return control.selectedColor == null || !control.selectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ToggleSwitch control)
            {
                return control.selectedColorProperty();
            }
        };

        private static final CssMetaData<ToggleSwitch, Paint> UNSELECTED_COLOR = new CssMetaData<ToggleSwitch, Paint>(
                "-rt-unselected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ToggleSwitch control)
            {
                return control.unselectedColor == null || !control.unselectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ToggleSwitch control)
            {
                return control.unselectedColorProperty();
            }
        };

        private static final CssMetaData<ToggleSwitch, Paint> SELECTED_LINE_COLOR = new CssMetaData<ToggleSwitch, Paint>(
                "-rt-selected-line-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ToggleSwitch control)
            {
                return control.selectedLineColor == null || !control.selectedLineColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ToggleSwitch control)
            {
                return control.selectedLineColorProperty();
            }
        };

        private static final CssMetaData<ToggleSwitch, Paint> UNSELECTED_LINE_COLOR = new CssMetaData<ToggleSwitch, Paint>(
                "-rt-unselected-line-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(ToggleSwitch control)
            {
                return control.unselectedLineColor == null || !control.unselectedLineColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(ToggleSwitch control)
            {
                return control.unselectedLineColorProperty();
            }
        };
        private static final CssMetaData<ToggleSwitch, Boolean> DISABLE_ANIMATION = new CssMetaData<ToggleSwitch, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(ToggleSwitch control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(ToggleSwitch control)
            {
                return control.isAnimationDisabledProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ToggleButton.getClassCssMetaData());
            // @formatter:off
            Collections.addAll(styleables, 
                    SELECTED_COLOR, 
                    UNSELECTED_COLOR, 
                    SELECTED_LINE_COLOR, 
                    UNSELECTED_LINE_COLOR, 
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
