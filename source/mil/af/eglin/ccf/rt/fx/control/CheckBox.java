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
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * A bi-state or tri-state selection control allowing the user to toggle
 * options.
 * <p>
 * A check box is typically skinned as a box with a check mark when selected or
 * a horizontal line when indeterminate.
 * <p>
 * {@link Checkbox Checkboxes) and {@link ToggleSwitch Toggleswitches} provide
 * similar behavior, but should be used in different situations. Checkboxes
 * should be use when presented a list of multiple related options, while toggle
 * switches should be used when one more independent options are present. 
 */
public class CheckBox extends javafx.scene.control.CheckBox implements RtStyleableComponent
{
    protected Accent accent = Accent.PRIMARY_MID;

    private static final String USER_AGENT_STYLESHEET = "check-box.css";
    private static final String DEFAULT_STYLE_CLASS = "rt-check-box";

    /**
     * The selected color specifies the color used by the border and fill when
     * in the selected and indeterminate state.
     */
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.SELECTED_COLOR, CheckBox.this, "selectedColor",
            DefaultPalette.getInstance().getAccentColor());

    /**
     * The unselected color specifies the color used by the border when in the
     * unselected state.
     */
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.UNSELECTED_COLOR, CheckBox.this, "unselectedColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base component color
     * to be visible when a semi-opaque overlay color is provided.
     */
    private StyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(
            StyleableProperties.OVERLAY_COLOR, CheckBox.this, "overlayColor",
            DefaultPalette.getInstance().getBaseColor());

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private StyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            StyleableProperties.DISABLE_ANIMATION, CheckBox.this, "disableAnimation", false);

    /**
     * Creates a check box with an empty string for its label.
     */
    public CheckBox()
    {
        super();
        initialize();
    }

    /**
     * Creates a check box with the accent.
     * 
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public CheckBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a check box with the specified text as its label.
     *
     * @param text A text string for its label.
     */
    public CheckBox(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Creates a check box with the specified text and accent.
     * 
     * @param text A text string for its label.
     * @param accent The accent type used to change the component's color
     *            scheme.
     */
    public CheckBox(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public final ObjectProperty<Paint> selectedColorProperty()
    {
        return this.selectedColor;
    }

    public final Paint getSelectedColor()
    {
        return selectedColor.get();
    }

    public final void setSelectedColor(Paint color)
    {
        this.selectedColor.set(color);
    }

    public final ObjectProperty<Paint> unselectedColorProperty()
    {
        return this.unselectedColor;
    }

    public final Paint getUnselectedColor()
    {
        return unselectedColor.get();
    }

    public final void setUnselectedColor(Paint color)
    {
        this.unselectedColor.set(color);
    }

    public final ObjectProperty<Paint> getOverlayColorProperty()
    {
        return this.overlayColor;
    }

    public final Paint getOverlayColor()
    {
        return this.overlayColor.get();
    }

    public final void setOverlayColor(Paint overlayColor)
    {
        this.overlayColor.set(overlayColor);
    }

    public final BooleanProperty isAnimationDisabledProperty()
    {
        return this.isAnimationDisabled;
    }

    public final boolean getIsAnimationDisabled()
    {
        return isAnimationDisabled.get();
    }

    public final void setIsAnimationDisabled(boolean isAnimationDisabled)
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
        return DEFAULT_STYLE_CLASS;
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
        getStyleClass().add(this.accent.getStyleClassName());
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
        private static final CssMetaData<CheckBox, Paint> OVERLAY_COLOR = new CssMetaData<CheckBox, Paint>(
                "-rt-overlay-color", PaintConverter.getInstance(), DefaultPalette.getInstance().getBaseColor())
        {
            @Override
            public boolean isSettable(CheckBox control)
            {
                return control.overlayColor == null || !control.overlayColor.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(CheckBox control)
            {
                return control.overlayColor;
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static
        {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(
                    javafx.scene.control.CheckBox.getClassCssMetaData());
            styleables.add(SELECTED_COLOR);
            styleables.add(UNSELECTED_COLOR);
            styleables.add(DISABLE_ANIMATION);
            styleables.add(OVERLAY_COLOR);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return getClassCssMetaData();
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
        CheckBox.loadStyleSheet();
    }
}
