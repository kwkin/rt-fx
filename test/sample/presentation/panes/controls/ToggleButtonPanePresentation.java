package test.sample.presentation.panes.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.ToggleButton;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.control.style.ToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class ToggleButtonPanePresentation extends TitledCard
{
    private static final String TITLE = "Toggle Buttons";
    
    public ToggleButtonPanePresentation(PaneController controller)
    {
        super(TITLE);
        
        VBox stackPane = new VBox();
        stackPane.getChildren().add(createToggleButtons());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createToggleSwitches());
        stackPane.getChildren().add(new Separator());
        stackPane.getChildren().add(createToggleIcons());
        
        setContent(stackPane);
    }
    
    private Node createToggleButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle buttons are available in several varieties: raised, flat, icon, and icon with text. Here are some raised toggle buttons.");

        GridPane toggleButtonGridPane = new GridPane();

        ToggleButton primaryLightToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        ToggleButton primaryMidToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.PRIMARY_MID);
        ToggleButton primaryDarkToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.PRIMARY_DARK);

        ToggleButton secondaryLightToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        ToggleButton secondaryMidToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.SECONDARY_MID);
        ToggleButton secondaryDarkToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.SECONDARY_DARK);

        ToggleButton baseLightToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.BASE_LIGHT);
        ToggleButton baseMidToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.BASE_MID);
        ToggleButton baseDarkToggleButton = new ToggleButton("Toggle", ToggleButtonStyle.RAISED, Accent.BASE_DARK);

        toggleButtonGridPane.add(primaryLightToggleButton, 0, 0);
        toggleButtonGridPane.add(primaryMidToggleButton, 1, 0);
        toggleButtonGridPane.add(primaryDarkToggleButton, 2, 0);

        toggleButtonGridPane.add(secondaryLightToggleButton, 0, 1);
        toggleButtonGridPane.add(secondaryMidToggleButton, 1, 1);
        toggleButtonGridPane.add(secondaryDarkToggleButton, 2, 1);

        toggleButtonGridPane.add(baseLightToggleButton, 0, 2);
        toggleButtonGridPane.add(baseMidToggleButton, 1, 2);
        toggleButtonGridPane.add(baseDarkToggleButton, 2, 2);
        
        descriptionPane.setContent(toggleButtonGridPane);
        
        return descriptionPane;
    }
    
    private Node createToggleSwitches()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle switches behave the same as toggle buttons, but they have a different style");
        
        GridPane toggleButtonGridPane = new GridPane();

        ToggleSwitch primaryLightToggleSwitch = new ToggleSwitch("Toggle", Accent.PRIMARY_LIGHT);
        primaryLightToggleSwitch.setSelected(true);
        ToggleSwitch primaryMidToggleSwitch = new ToggleSwitch("Toggle", Accent.PRIMARY_MID);
        primaryMidToggleSwitch.setSelected(true);
        ToggleSwitch primaryDarkToggleSwitch = new ToggleSwitch("Toggle", Accent.PRIMARY_DARK);

        ToggleSwitch secondaryLightToggleSwitch = new ToggleSwitch("Toggle", Accent.SECONDARY_LIGHT);
        ToggleSwitch secondaryMidToggleSwitch = new ToggleSwitch("Toggle", Accent.SECONDARY_MID);
        secondaryMidToggleSwitch.setSelected(true);
        ToggleSwitch secondaryDarkToggleSwitch = new ToggleSwitch("Toggle", Accent.SECONDARY_DARK);
        secondaryDarkToggleSwitch.setSelected(true);

        toggleButtonGridPane.add(primaryLightToggleSwitch, 0, 0);
        toggleButtonGridPane.add(primaryMidToggleSwitch, 1, 0);
        toggleButtonGridPane.add(primaryDarkToggleSwitch, 2, 0);

        toggleButtonGridPane.add(secondaryLightToggleSwitch, 0, 1);
        toggleButtonGridPane.add(secondaryMidToggleSwitch, 1, 1);
        toggleButtonGridPane.add(secondaryDarkToggleSwitch, 2, 1);
        
        descriptionPane.setContent(toggleButtonGridPane);

        return descriptionPane;
    }
    
    private Node createToggleIcons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle buttons can also be represented with icons with and without text. Additionally, the icon buttons can be accented or glowing.:");
        
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(0));
        
        SvgIcon smilyOn = new SvgIcon(SvgIcons.EMOTICON_EXCITED, IconSizes.SIZE_32);
        SvgIcon smilyOff = new SvgIcon(SvgIcons.EMOTICON_EXCITED_OUTLINE, IconSizes.SIZE_32);
        IconToggleButton normal = new IconToggleButton(smilyOn, smilyOff);
        
        SvgIcon eyeOn = new SvgIcon(SvgIcons.EYE, IconSizes.SIZE_32);
        SvgIcon eyeOff = new SvgIcon(SvgIcons.EYE_OUTLINE, IconSizes.SIZE_32);
        IconToggleButton normalWithText = new IconToggleButton(eyeOn, eyeOff);
        normalWithText.setText("Normal");

        SvgIcon play = new SvgIcon(SvgIcons.PLAY, IconSizes.SIZE_32);
        SvgIcon pause = new SvgIcon(SvgIcons.PAUSE, IconSizes.SIZE_32);
        IconToggleButton accented = new IconToggleButton(play, pause, IconToggleButtonStyle.ACCENTED);

        SvgIcon mapAlert = new SvgIcon(SvgIcons.MAP_MARKER_ALERT, IconSizes.SIZE_32);
        SvgIcon mapAlertCircle = new SvgIcon(SvgIcons.MAP_MARKER_ALERT_OUTLINE, IconSizes.SIZE_32);
        IconToggleButton accentedWithText = new IconToggleButton(mapAlert, mapAlertCircle, IconToggleButtonStyle.ACCENTED);
        accentedWithText.setText("Accented");

        SvgIcon alertCircle = new SvgIcon(SvgIcons.ALERT_CIRCLE, IconSizes.SIZE_32);
        SvgIcon alertCircleOutline = new SvgIcon(SvgIcons.ALERT_CIRCLE_OUTLINE, IconSizes.SIZE_32);
        IconToggleButton glowing = new IconToggleButton(alertCircle, alertCircleOutline, IconToggleButtonStyle.GLOWING);

        SvgIcon shieldAlert = new SvgIcon(SvgIcons.SHIELD_ALERT, IconSizes.SIZE_32);
        SvgIcon shieldAlertOutline = new SvgIcon(SvgIcons.SHIELD_ALERT_OUTLINE, IconSizes.SIZE_32);
        IconToggleButton glowingWithText = new IconToggleButton(shieldAlert, shieldAlertOutline, IconToggleButtonStyle.GLOWING);
        glowingWithText.setText("Glowing");

        pane.getChildren().add(normal);
        pane.getChildren().add(normalWithText);
        pane.getChildren().add(accented);
        pane.getChildren().add(accentedWithText);
        pane.getChildren().add(glowing);
        pane.getChildren().add(glowingWithText);
        
        descriptionPane.setContent(pane);

        return descriptionPane;
    }
}
