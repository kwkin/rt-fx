package mil.af.eglin.ccf.rt.fx.control.skins;

import static javafx.scene.control.ContentDisplay.BOTTOM;
import static javafx.scene.control.ContentDisplay.LEFT;
import static javafx.scene.control.ContentDisplay.RIGHT;
import static javafx.scene.control.ContentDisplay.TOP;

import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.javafx.scene.control.skin.ToggleButtonSkin;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.animations.RtAnimationTimeline;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyFrame;
import mil.af.eglin.ccf.rt.fx.control.animations.RtKeyValue;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;

public class RtIconToggleButtonSkin extends ToggleButtonSkin
{
    private final static Duration ANIMATION_DURATION = Duration.millis(200);

    private final IconToggleButton button;
    private final StackPane iconPane = new StackPane();
    private final StackPane stateBox = new StackPane();
    private final LabeledText text;

    private RtAnimationTimeline interactionTimeline;

    public RtIconToggleButtonSkin(final IconToggleButton button)
    {
        super(button);
        this.button = button;

        this.text = (LabeledText) button.lookup(".text");

        this.button.getSelectedIcon().visibleProperty().bind(this.button.selectedProperty());
        this.button.getUnslectedIcon().visibleProperty().bind(this.button.selectedProperty().not());
        
        double width = this.button.getSelectedIcon().getSize();
        double height = this.button.getSelectedIcon().getSize();
        setIconPaneSize(width, height);

        this.stateBox.getStyleClass().setAll("state-box");
        this.stateBox.setOpacity(0);
        updateStateBoxColor();

        this.iconPane.getChildren().addAll(this.button.getSelectedIcon(), this.button.getUnslectedIcon());
        this.button.setGraphic(this.iconPane);

        createAnimation();
        createAnimationListeners();
        updateChildren();

        this.button.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            updateText();
        });

        registerChangeListener(button.selectedFillProperty(), button.selectedFillProperty().getName());
        registerChangeListener(button.unselectedFillProperty(), button.unselectedFillProperty().getName());
        registerChangeListener(button.overlayColorProperty(), button.overlayColorProperty().getName());
    }

    @Override
    protected void updateChildren()
    {
        super.updateChildren();
        if (stateBox != null)
        {
            getChildren().add(0, stateBox);
        }
    }

    @Override
    protected void handleControlPropertyChanged(String property)
    {
        super.handleControlPropertyChanged(property);
        if (button.selectedFillProperty().getName().equals(property))
        {
            this.button.getSelectedIcon().setFill(this.button.getSelectedFill());
        }
        else if (button.unselectedFillProperty().getName().equals(property))
        {
            this.button.getUnslectedIcon().setFill(this.button.getUnselectedFill());
        }
        else if (button.overlayColorProperty().getName().equals(property))
        {
            updateStateBoxColor();
        }
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h)
    {
        this.stateBox.resizeRelocate(getSkinnable().getLayoutBounds().getMinX(),
                getSkinnable().getLayoutBounds().getMinY(), getSkinnable().getWidth(), getSkinnable().getHeight());
        layoutLabelInArea(x, y, w, h);
        
        SvgIcon selectedIcon = this.button.getSelectedIcon();
        double selectedIconSize = selectedIcon != null ? selectedIcon.getSize() : 0;
        SvgIcon unSelectedIcon = this.button.getUnslectedIcon();
        double unselectedIconSize = unSelectedIcon != null ? unSelectedIcon.getSize() : 0;
        double maxSize = Math.max(selectedIconSize, unselectedIconSize);
        setIconPaneSize(maxSize, maxSize);
    }

    @Override
    protected double computeMinWidth(double h, double topInset, double rightInset, double bottomInset, double leftInset)
    {
        return computePrefWidth(h, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double h, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        double widthPadding = leftInset + leftLabelPadding() + rightInset + rightLabelPadding();

        final Font font = this.text.getFont();

        String selectedText = this.button.getSelectedText();
        boolean isSelectedTextEmpty = selectedText == null || selectedText.isEmpty();
        double selectedTextWidth = isSelectedTextEmpty ? 0 : Utils.computeTextWidth(font, selectedText, 0);
        String unselectedText = this.button.getUnselectedText();
        boolean isUnselectedTextEmpty = unselectedText == null || unselectedText.isEmpty();
        double unselectedTextWidth = isUnselectedTextEmpty ? 0 : Utils.computeTextWidth(font, unselectedText, 0);
        double textWidth = Math.max(selectedTextWidth, unselectedTextWidth);

        Node graphic = this.button.getGraphic();
        double graphicWidth = graphic == null ? 0.0
                : Utils.boundedSize(graphic.prefWidth(-1), graphic.minWidth(-1), graphic.maxWidth(-1));

        if (isIgnoreGraphic())
        {
            return textWidth + widthPadding;
        }
        else if (isIgnoreText())
        {
            return graphicWidth + widthPadding;
        }
        else if (this.button.getContentDisplay() == ContentDisplay.LEFT
                || this.button.getContentDisplay() == ContentDisplay.RIGHT)
        {
            return textWidth + this.button.getGraphicTextGap() + graphicWidth + widthPadding;
        }
        else
        {
            return Math.max(textWidth, graphicWidth) + widthPadding;
        }
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        final Labeled labeled = getSkinnable();
        final Font font = text.getFont();
        final ContentDisplay contentDisplay = labeled.getContentDisplay();
        final double gap = labeled.getGraphicTextGap();
        width -= leftInset + leftLabelPadding() + rightInset + rightLabelPadding();

        String str = labeled.getText();
        if (str != null && str.endsWith("\n"))
        {
            str = str.substring(0, str.length() - 1);
        }

        double selectedTextWidth = width;
        double unselectedTextWidth = width;
        if (!isIgnoreGraphic() && (contentDisplay == LEFT || contentDisplay == RIGHT))
        {
            selectedTextWidth -= (this.button.getSelectedIcon().prefWidth(-1) + gap);
            unselectedTextWidth -= (this.button.getUnslectedIcon().prefWidth(-1) + gap);
        }
        final double selectedTextHeight = Utils.computeTextHeight(font, str,
                labeled.isWrapText() ? selectedTextWidth : 0, labeled.getLineSpacing(), text.getBoundsType());
        final double unselectedTextHeight = Utils.computeTextHeight(font, str,
                labeled.isWrapText() ? unselectedTextWidth : 0, labeled.getLineSpacing(), text.getBoundsType());
        double textHeight = Math.max(selectedTextHeight, unselectedTextHeight);

        double h = textHeight;
        if (!isIgnoreGraphic())
        {
            final Node graphic = labeled.getGraphic();
            if (contentDisplay == TOP || contentDisplay == BOTTOM)
            {
                h = graphic.prefHeight(width) + gap + textHeight;
            }
            else
            {
                h = Math.max(textHeight, graphic.prefHeight(width));
            }
        }
        return topInset + h + bottomInset + topLabelPadding() + bottomLabelPadding();
    }

    @Override
    protected double computeMaxWidth(double h, double topInset, double rightInset, double bottomInset, double leftInset)
    {
        return computePrefWidth(h, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxHeight(double w, double topInset, double rightInset, double bottomInset,
            double leftInset)
    {
        return computePrefHeight(w, topInset, rightInset, bottomInset, leftInset);
    }

    private void createAnimation()
    {
        // @formatter:off
        this.interactionTimeline = new RtAnimationTimeline(
            RtKeyFrame.builder()
                .setDuration(ANIMATION_DURATION)
                .setKeyValues(
                        RtKeyValue.builder()
                        .setTarget(this.stateBox.opacityProperty())
                        .setEndValueSupplier(() -> determineOpacity())
                        .setInterpolator(Interpolator.EASE_OUT)
                        .build())
                .build());
        // @formatter:on
        this.interactionTimeline.setAnimateCondition(() -> !this.button.getIsAnimationDisabled());
    }

    private void createAnimationListeners()
    {
        this.button.selectedProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
        this.button.armedProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
        this.button.hoverProperty().addListener((ov, oldVal, newVal) ->
        {
            interactionTimeline.skipAndContinue();
        });
    }

    private double determineOpacity()
    {
        double opacity = 0;
        switch (this.button.getRtStyle())
        {
            case GLOWING_ICON:
                if (this.button.isArmed())
                {
                    opacity = 1;
                }
                else if (this.button.isHover() && this.button.isSelected())
                {
                    opacity = 0.8;
                }
                else if (this.button.isSelected())
                {
                    opacity = 0.6;
                }
                else if (this.button.isHover())
                {
                    opacity = 0.4;
                }
                break;
            default:
                if (button.isArmed())
                {
                    opacity = 1;
                }
                else if (button.isHover())
                {
                    opacity = 0.4;
                }
                break;
        }
        return opacity;
    }

    private void updateStateBoxColor()
    {
        CornerRadii radii = this.button.getBackground() == null ? null
                : this.button.getBackground().getFills().get(0).getRadii();
        Insets insets = this.stateBox.getInsets();
        this.stateBox.setBackground(new Background(new BackgroundFill(this.button.getOverlayColor(), radii, insets)));
    }

    private void setIconPaneSize(double width, double height)
    {
        this.iconPane.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.iconPane.setPrefSize(width, height);
        this.iconPane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
    }

    private void updateText()
    {
        this.button.setText(this.button.isSelected() ? this.button.getSelectedText() : this.button.getUnselectedText());
    }
}
