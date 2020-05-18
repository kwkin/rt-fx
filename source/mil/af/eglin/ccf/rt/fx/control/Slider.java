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
import javafx.scene.control.Skin;
import javafx.scene.paint.Paint;
import mil.af.eglin.ccf.rt.fx.control.skins.RtSliderSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Slider extends javafx.scene.control.Slider implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "slider.css";
    private static final String CSS_CLASS = "rt-slider";

    private StyleableObjectProperty<Paint> thumbColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.THUMB_COLOR, this, "thumbColor");
    private StyleableObjectProperty<Paint> filledTrackColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.FILLED_TRACK_COLOR, this, "filledTrackColor");
    private StyleableObjectProperty<Paint> unfilledTrackColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNFILLED_TRACK_COLOR, this, "unfilledTrackColor");
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, this, "disableAnimation", false);

    public Slider()
    {
        super();
        initialize();
    }

    public Slider(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    public Slider(double min, double max, double value)
    {
        super(min, max, value);
        initialize();
    }

    public Slider(double min, double max, double value, Accent accent)
    {
        super(min, max, value);
        this.accent = accent;
        initialize();
    }

    public StyleableObjectProperty<Paint> thumbColorProperty()
    {
        return this.thumbColor;
    }

    public Paint getThumbColor()
    {
        return thumbColor.get();
    }

    public void setThumbColor(Paint color)
    {
        this.thumbColor.set(color);
    }

    public StyleableObjectProperty<Paint> filledTrackColorProperty()
    {
        return this.filledTrackColor;
    }

    public Paint getFilledTrackColor()
    {
        return filledTrackColor.get();
    }

    public void setFilledTrackColor(Paint color)
    {
        this.filledTrackColor.set(color);
    }

    public StyleableObjectProperty<Paint> unfilledTrackColorProperty()
    {
        return this.unfilledTrackColor;
    }

    public Paint getUnfilledTrackColor()
    {
        return unfilledTrackColor.get();
    }

    public void setUnfilledTrackColor(Paint color)
    {
        this.unfilledTrackColor.set(color);
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() 
    {
        return new RtSliderSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<Slider, Paint> THUMB_COLOR = new CssMetaData<Slider, Paint>("-rt-thumb-color",
                PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(Slider control)
            {
                return control.thumbColor == null || !control.thumbColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Slider control)
            {
                return control.thumbColor;
            }
        };
        private static final CssMetaData<Slider, Paint> FILLED_TRACK_COLOR = new CssMetaData<Slider, Paint>(
                "-rt-filled-track-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(Slider control)
            {
                return control.thumbColor == null || !control.thumbColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Slider control)
            {
                return control.thumbColor;
            }
        };
        private static final CssMetaData<Slider, Paint> UNFILLED_TRACK_COLOR = new CssMetaData<Slider, Paint>(
                "-rt-unfilled-track-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(Slider control)
            {
                return control.thumbColor == null || !control.thumbColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Slider control)
            {
                return control.thumbColor;
            }
        };
        private static final CssMetaData<Slider, Boolean> DISABLE_ANIMATION = new CssMetaData<Slider, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(Slider control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(Slider control)
            {
                return control.isAnimationDisabled;
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.Slider.getClassCssMetaData());
            styleables.add(THUMB_COLOR);
            styleables.add(FILLED_TRACK_COLOR);
            styleables.add(UNFILLED_TRACK_COLOR);
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
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
