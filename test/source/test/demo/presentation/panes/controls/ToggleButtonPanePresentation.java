package test.demo.presentation.panes.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import mil.af.eglin.ccf.rt.fx.control.IconToggleButton;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.ToggleButton;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.IconToggleButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.controller.PaneController;

public class ToggleButtonPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Toggle Buttons";
    
    public ToggleButtonPanePresentation(PaneController controller)
    {
        super(TITLE);
        
        VBox stackPane = new VBox(0);
        stackPane.getChildren().add(createToggleButtons());
        stackPane.getChildren().add(new TitledSeparator("Toggle Switches"));
        stackPane.getChildren().add(createToggleSwitches());
        stackPane.getChildren().add(new TitledSeparator("Icon Toggle Buttons"));
        stackPane.getChildren().add(createToggleIcons());
        
        setContent(stackPane);
    }
    
    private Node createToggleButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle buttons are available in several varieties: raised, flat, icon, and icon with text. Here are some raised toggle buttons.");

        GridPane toggleButtonGridPane = new GridPane();

        ToggleButton primaryLightToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        ToggleButton primaryMidToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.PRIMARY_MID);
        ToggleButton primaryDarkToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.PRIMARY_DARK);

        ToggleButton secondaryLightToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        ToggleButton secondaryMidToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.SECONDARY_MID);
        ToggleButton secondaryDarkToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.SECONDARY_DARK);

        ToggleButton baseLightToggleButton = new ToggleButton("TOGGLE", IconToggleButtonStyle.RAISED, Accent.BASE);

        toggleButtonGridPane.add(primaryLightToggleButton, 0, 0);
        toggleButtonGridPane.add(primaryMidToggleButton, 1, 0);
        toggleButtonGridPane.add(primaryDarkToggleButton, 2, 0);

        toggleButtonGridPane.add(secondaryLightToggleButton, 0, 1);
        toggleButtonGridPane.add(secondaryMidToggleButton, 1, 1);
        toggleButtonGridPane.add(secondaryDarkToggleButton, 2, 1);

        toggleButtonGridPane.add(baseLightToggleButton, 0, 2);
        
        descriptionPane.setContent(toggleButtonGridPane);
        
        return descriptionPane;
    }
    
    private Node createToggleSwitches()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle switches behave the same as toggle buttons, but they have a different style");
        
        GridPane toggleButtonGridPane = new GridPane();
        toggleButtonGridPane.setPadding(new Insets(0));
        toggleButtonGridPane.setVgap(0);

        ToggleSwitch primaryLightToggleSwitch = new ToggleSwitch("Two State", Accent.PRIMARY_LIGHT);
        primaryLightToggleSwitch.setSelected(true);
        ToggleSwitch primaryMidToggleSwitch = new ToggleSwitch("Left Text", Accent.PRIMARY_MID);
        primaryMidToggleSwitch.setContentDisplay(ContentDisplay.RIGHT);
        ToggleSwitch primaryDarkToggleSwitch = new ToggleSwitch("Three State", Accent.PRIMARY_DARK);
        primaryDarkToggleSwitch.setAllowIndeterminate(true);
        primaryDarkToggleSwitch.setSelected(true);

        ToggleSwitch secondaryLightToggleSwitch = new ToggleSwitch("Two State", Accent.SECONDARY_LIGHT);
        ToggleSwitch secondaryMidToggleSwitch = new ToggleSwitch("Left Text", Accent.SECONDARY_MID);
        secondaryMidToggleSwitch.setSelected(true);
        secondaryMidToggleSwitch.setContentDisplay(ContentDisplay.RIGHT);
        ToggleSwitch secondaryDarkToggleSwitch = new ToggleSwitch("Three State", Accent.SECONDARY_DARK);
        secondaryDarkToggleSwitch.setAllowIndeterminate(true);
        secondaryDarkToggleSwitch.setIndeterminate(true);

        toggleButtonGridPane.add(primaryLightToggleSwitch, 0, 0);
        toggleButtonGridPane.add(primaryMidToggleSwitch, 0, 1);
        toggleButtonGridPane.add(primaryDarkToggleSwitch, 0, 2);

        toggleButtonGridPane.add(secondaryLightToggleSwitch, 1, 0);
        toggleButtonGridPane.add(secondaryMidToggleSwitch, 1, 1);
        toggleButtonGridPane.add(secondaryDarkToggleSwitch, 1, 2);
        
        descriptionPane.setContent(toggleButtonGridPane);

        return descriptionPane;
    }
    
    private Node createToggleIcons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Toggle buttons can also be represented with icons with and without text.");
        
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(0));
        
        SvgIcon smilyOn = new SvgIcon(SvgFile.EMOTICON_EXCITED, IconSize.SIZE_32);
        SvgIcon smilyOff = new SvgIcon(SvgFile.EMOTICON_EXCITED_OUTLINE, IconSize.SIZE_32);
        IconToggleButton normal = new IconToggleButton(smilyOn, smilyOff);
        
        SvgIcon eyeOn = new SvgIcon(SvgFile.EYE, IconSize.SIZE_32);
        SvgIcon eyeOff = new SvgIcon(SvgFile.EYE_OUTLINE, IconSize.SIZE_32);
        IconToggleButton normalWithText = new IconToggleButton(eyeOn, eyeOff);
        normalWithText.setText("Normal");

        SvgIcon play = new SvgIcon(SvgFile.PLAY, IconSize.SIZE_32);
        SvgIcon pause = new SvgIcon(SvgFile.PAUSE, IconSize.SIZE_32);
        IconToggleButton accented = new IconToggleButton(play, pause, IconToggleButtonStyle.ACCENTED_ICON);

        SvgIcon mapAlert = new SvgIcon(SvgFile.MAP_MARKER_ALERT, IconSize.SIZE_32);
        SvgIcon mapAlertCircle = new SvgIcon(SvgFile.MAP_MARKER_ALERT_OUTLINE, IconSize.SIZE_32);
        IconToggleButton accentedWithText = new IconToggleButton(mapAlert, mapAlertCircle, IconToggleButtonStyle.ACCENTED_ICON);
        accentedWithText.setText("Accented");

        SvgIcon alertCircle = new SvgIcon(SvgFile.ALERT_CIRCLE, IconSize.SIZE_32);
        SvgIcon alertCircleOutline = new SvgIcon(SvgFile.ALERT_CIRCLE_OUTLINE, IconSize.SIZE_32);
        IconToggleButton glowing = new IconToggleButton(alertCircle, alertCircleOutline, IconToggleButtonStyle.GLOWING_ICON);

        SvgIcon shieldAlert = new SvgIcon(SvgFile.SHIELD_ALERT, IconSize.SIZE_32);
        SvgIcon shieldAlertOutline = new SvgIcon(SvgFile.SHIELD_ALERT_OUTLINE, IconSize.SIZE_32);
        IconToggleButton glowingWithText = new IconToggleButton(shieldAlert, shieldAlertOutline, IconToggleButtonStyle.GLOWING_ICON);
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
