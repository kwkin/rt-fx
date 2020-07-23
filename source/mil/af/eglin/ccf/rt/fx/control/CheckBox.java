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

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
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
    private static final String CSS_CLASS = "rt-check-box";

    private static final StyleablePropertyFactory<CheckBox> FACTORY = new StyleablePropertyFactory<>(
            javafx.scene.control.CheckBox.getClassCssMetaData());

    private static final CssMetaData<CheckBox, Paint> SELECTED_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-selected-color", s -> s.selectedColor, DefaultPalette.getInstance().getAccentColor(), false);
    private static final CssMetaData<CheckBox, Paint> UNSELECTED_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-unselected-color", s -> s.unselectedColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<CheckBox, Paint> OVERLAY_COLOR = FACTORY.createPaintCssMetaData(
            "-rt-overlay-color", s -> s.overlayColor, DefaultPalette.getInstance().getBaseColor(), false);
    private static final CssMetaData<CheckBox, Boolean> DISABLE_ANIMATION = FACTORY
            .createBooleanCssMetaData("-rt-disable-animation", s -> s.isAnimationDisabled, false, false);

    /**
     * The selected color specifies the color used by the border and fill when
     * in the selected and indeterminate state.
     */
    private StyleableObjectProperty<Paint> selectedColor = new SimpleStyleableObjectProperty<>(SELECTED_COLOR, this,
            "selectedColor");

    /**
     * The unselected color specifies the color used by the border when in the
     * unselected state.
     */
    private StyleableObjectProperty<Paint> unselectedColor = new SimpleStyleableObjectProperty<>(UNSELECTED_COLOR, this,
            "unselectedColor");

    /**
     * The overlay color specifies the background color used when hovering and
     * arming the button.
     * <p>
     * The color is added on top of the button to allow the base component color
     * to be visible when a semi-opaque overlay color is provided.
     */
    private final SimpleStyleableObjectProperty<Paint> overlayColor = new SimpleStyleableObjectProperty<>(OVERLAY_COLOR,
            this, "overlayColor");

    /**
     * An animated component will apply transitions between pseudostates.
     * <p>
     * When disabled, the transition end values will apply instantly.
     */
    private final SimpleStyleableBooleanProperty isAnimationDisabled = new SimpleStyleableBooleanProperty(
            DISABLE_ANIMATION, this, "isAnimationDisabled");

    /**
     * Creates a check box with an empty string for its label.
     */
    public CheckBox()
    {
        super();
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
     * Creates a check box with the accent.
     * 
     * @param accent the accent used to change the component's color scheme.
     */
    public CheckBox(Accent accent)
    {
        super();
        this.accent = accent;
        initialize();
    }

    /**
     * Creates a check box with the specified text and accent.
     * 
     * @param text a text string for its label.
     * @param accent the accent used to change the component's color scheme.
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
        return CSS_CLASS;
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
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData()
    {
        return FACTORY.getCssMetaData();
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
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getStyleClassName());
    }

    /**
     * Returns the list of available CSS properties associated with this class,
     * which may include the properties of its super classes.
     * 
     * @return The list of available CSS properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData()
    {
        return FACTORY.getCssMetaData();
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
