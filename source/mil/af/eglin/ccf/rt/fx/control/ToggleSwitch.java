package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.css.StyleManager;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.PaintConverter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
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

    // @formatter:off
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, this, "selectedColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, this, "unselectedColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableObjectProperty<Paint> selectedLineColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_LINE_COLOR, this, "selectedLineColor", DefaultPalette.getInstance().getLightAccentColor());
    private StyleableObjectProperty<Paint> unselectedLineColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_LINE_COLOR, this, "unselectedLineColor", DefaultPalette.getInstance().getLightBaseColor());
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    // @formatter:on

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

    public ObjectProperty<Paint> selectedColorProperty()
    {
        return this.selectedColor;
    }

    public Paint getSelectedColor()
    {
        return selectedColor.get();
    }

    public void setSelectedColor(Paint color)
    {
        this.selectedColor.set(color);
    }

    public ObjectProperty<Paint> unselectedColorProperty()
    {
        return this.unselectedColor;
    }

    public Paint getUnselectedColor()
    {
        return unselectedColor.get();
    }

    public void setUnselectedColor(Paint color)
    {
        this.unselectedColor.set(color);
    }

    public ObjectProperty<Paint> selectedLineColorProperty()
    {
        return this.selectedLineColor;
    }

    public Paint getSelectedLineColor()
    {
        return selectedLineColor.get();
    }

    public void setSelectedLineColor(Paint color)
    {
        this.selectedLineColor.set(color);
    }

    public StyleableObjectProperty<Paint> unselectedLineColorProperty()
    {
        return this.unselectedLineColor;
    }

    public Paint getUnselectedLineColor()
    {
        return unselectedLineColor.get();
    }

    public void setUnselectedLineColor(Paint color)
    {
        this.unselectedLineColor.set(color);
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
        return null;
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
                return control.selectedColor;
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
                return control.unselectedColor;
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
                return control.selectedLineColor;
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
                return control.unselectedLineColor;
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
                return control.isAnimationDisabled;
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.ToggleButton.getClassCssMetaData());
            styleables.add(SELECTED_COLOR);
            styleables.add(UNSELECTED_COLOR);
            styleables.add(SELECTED_LINE_COLOR);
            styleables.add(UNSELECTED_LINE_COLOR);
            styleables.add(DISABLE_ANIMATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
