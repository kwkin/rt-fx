/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
import mil.af.eglin.ccf.rt.fx.control.skins.RtCheckBoxSkin;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.style.DefaultPalette;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class CheckBox extends javafx.scene.control.CheckBox implements RtComponent
{
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String USER_AGENT_STYLESHEET = "check-box.css";
    private static final String DEFAULT_STYLE_CLASS = "rt-check-box";
    
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, CheckBox.this, "selectedColor", DefaultPalette.getInstance().getAccentColor());
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, CheckBox.this, "unselectedColor", DefaultPalette.getInstance().getBaseColor());
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, CheckBox.this, "disableAnimation", false);

    /**
     * {@inheritDoc}
     */
    public CheckBox()
    {
        super();
        initialize();
    }

    public CheckBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    public CheckBox(String string)
    {
        super(string);
        initialize();
    }

    public CheckBox(String text, Accent accent)
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
        return selectedColor.getValue();
    }

    public void setSelectedColor(Paint color)
    {
        this.selectedColor.setValue(color);
    }

    public ObjectProperty<Paint> unselectedColorProperty()
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
        return DEFAULT_STYLE_CLASS;
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
        return new RtCheckBoxSkin(this);
    }

    private void initialize()
    {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }

    private static class StyleableProperties
    {
        private static final CssMetaData<CheckBox, Paint> SELECTED_COLOR = new CssMetaData<CheckBox, Paint>(
                "-rt-selected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(CheckBox control)
            {
                return control.selectedColor == null || !control.selectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(CheckBox control)
            {
                return control.selectedColor;
            }
        };
        private static final CssMetaData<CheckBox, Paint> UNSELECTED_COLOR = new CssMetaData<CheckBox, Paint>(
                "-rt-unselected-color", PaintConverter.getInstance())
        {
            @Override
            public boolean isSettable(CheckBox control)
            {
                return control.unselectedColor == null || !control.unselectedColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(CheckBox control)
            {
                return control.unselectedColor;
            }
        };
        private static final CssMetaData<CheckBox, Boolean> DISABLE_ANIMATION = new CssMetaData<CheckBox, Boolean>(
                "-rt-disable-animation", BooleanConverter.getInstance(), false)
        {
            @Override
            public boolean isSettable(CheckBox control)
            {
                return control.isAnimationDisabled == null || !control.isAnimationDisabled.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(CheckBox control)
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
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
