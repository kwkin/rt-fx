package mil.af.eglin.ccf.rt.fx.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtRadioButtonSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class RadioButton extends javafx.scene.control.RadioButton implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "radio-button.css";
    private static final String CSS_CLASS = "rt-radio-button";
    
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, this, "selectedColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, this, "unselectedColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);
    
    public RadioButton()
    {
        super();
        initialize();
    }
    
    public RadioButton(String text)
    {
        super(text);
        initialize();
    }
    
    public RadioButton(String text, Accent accent)
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
        this.unselectedColor.set(color);
    }

    public BooleanProperty isAnimationDisabledProperty()
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtRadioButtonSkin(this);
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<RadioButton, Paint> SELECTED_COLOR = new CssMetaData<RadioButton, Paint>(
                "-rt-selected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.selectedColor == null || !control.selectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(RadioButton control)
            {
                return control.selectedColorProperty();
            }
        };
        private static final CssMetaData<RadioButton, Paint> UNSELECTED_COLOR = new CssMetaData<RadioButton, Paint>(
                "-rt-unselected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.unselectedColor == null || !control.unselectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(RadioButton control)
            {
                return control.unselectedColorProperty();
            }
        };
        private static final CssMetaData<RadioButton, Boolean> DISABLE_ANIMATION = new CssMetaData<RadioButton, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(RadioButton control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(RadioButton control)
            {
                return control.isAnimationDisabled;
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(javafx.scene.control.CheckBox.getClassCssMetaData());
            styleables.add(SELECTED_COLOR);
            styleables.add(UNSELECTED_COLOR);
            styleables.add(DISABLE_ANIMATION);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}