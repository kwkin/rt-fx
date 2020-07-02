package test.demo.presentation.panes.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconScale;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.FlowPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.SizedTitledCard;
import test.demo.controller.PaneController;

public class ButtonPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Buttons";
    
    public ButtonPanePresentation(PaneController controller)
    {
        super(TITLE);

        VBox vBox = new VBox();
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Normal buttons come in several varieties: raised, flat, icon, and icon with text.");
        vBox.getChildren().add(descriptionPane);
        vBox.getChildren().add(new TitledSeparator("Normal"));
        vBox.getChildren().add(createNormalButtons());
        vBox.getChildren().add(new TitledSeparator("Flat"));
        vBox.getChildren().add(createFlatButtons());
        vBox.getChildren().add(new TitledSeparator("Icon"));
        vBox.getChildren().add(createIconButtons());
        setContent(vBox);
    }
    
    private Node createNormalButtons()
    {
        GridPane toggleButtonGridPane = new GridPane();
        
        Button primaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        Button primaryMidButton = new Button("CANCEL", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        Button primaryDarkButton = new Button("APPLY", ButtonStyle.RAISED, Accent.PRIMARY_DARK);

        Button secondaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        Button secondaryMidButton = new Button("CANCEL", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        Button secondaryDarkButton = new Button("APPLY", ButtonStyle.RAISED, Accent.SECONDARY_DARK);

        Button baseButton = new Button("OK", ButtonStyle.RAISED, Accent.BASE);

        toggleButtonGridPane.add(primaryLightButton, 0, 0);
        toggleButtonGridPane.add(primaryMidButton, 1, 0);
        toggleButtonGridPane.add(primaryDarkButton, 2, 0);
        toggleButtonGridPane.add(secondaryLightButton, 0, 1);
        toggleButtonGridPane.add(secondaryMidButton, 1, 1);
        toggleButtonGridPane.add(secondaryDarkButton, 2, 1);
        toggleButtonGridPane.add(baseButton, 0, 2);
        
        return toggleButtonGridPane;
    }
    
    private Node createFlatButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Flat buttons behave the same as regular buttons, but they appear to be on the same level as its container.");

        GridPane toggleButtonGridPane = new GridPane();
        
        Button primaryLightFlatButton = new Button("DEFAULT", ButtonStyle.FLAT, Accent.PRIMARY_LIGHT);
        primaryLightFlatButton.setDefaultButton(true);
        Button primaryMidFlatButton = new Button("ADD", ButtonStyle.FLAT, Accent.PRIMARY_MID);
        Button primaryDarkFlatButton = new Button("DISCARD", ButtonStyle.FLAT, Accent.PRIMARY_DARK);

        Button secondaryLightFlatButton = new Button("DEFAULT", ButtonStyle.FLAT, Accent.SECONDARY_LIGHT);
        secondaryLightFlatButton.setDefaultButton(true);
        Button secondaryMidFlatButton = new Button("ADD", ButtonStyle.FLAT, Accent.SECONDARY_MID);
        Button secondaryDarkFlatButton = new Button("DISCARD", ButtonStyle.FLAT, Accent.SECONDARY_DARK);

        toggleButtonGridPane.add(primaryLightFlatButton, 0, 0);
        toggleButtonGridPane.add(primaryMidFlatButton, 1, 0);
        toggleButtonGridPane.add(primaryDarkFlatButton, 2, 0);

        toggleButtonGridPane.add(secondaryLightFlatButton, 0, 1);
        toggleButtonGridPane.add(secondaryMidFlatButton, 1, 1);
        toggleButtonGridPane.add(secondaryDarkFlatButton, 2, 1);

        descriptionPane.setContent(toggleButtonGridPane);
        
        return descriptionPane;
    }
    
    private Node createIconButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icon buttons are similar to flat buttons, but have a tighter padding and different API.");
        
        FlowPane primaryButtonBar = new FlowPane();
        IconButton iconButton1 = new IconButton(new SvgIcon(SvgFile.MAGNIFY_MINUS, IconSize.SIZE_24));
        IconButton iconButton2 = new IconButton(new SvgIcon(SvgFile.MAGNIFY, IconSize.SIZE_24));
        IconButton iconButton3 = new IconButton(new SvgIcon(SvgFile.MAGNIFY_PLUS, IconSize.SIZE_24));
        IconButton iconButton4 = new IconButton(new SvgIcon(SvgFile.MAGNIFY_REMOVE_CURSOR, IconSize.SIZE_24));

        IconButton iconButtonLeftText = new IconButton(new SvgIcon(SvgFile.COG, IconSize.SIZE_24), "Left Icon");
        IconButton iconButtonRightText = new IconButton(new SvgIcon(SvgFile.COG, IconSize.SIZE_24), "Right Icon");
        iconButtonRightText.setContentDisplay(ContentDisplay.RIGHT);
        IconButton iconButtonTopText = new IconButton(new SvgIcon(SvgFile.COG, IconSize.SIZE_24), "Top Icon");
        iconButtonTopText.setContentDisplay(ContentDisplay.TOP);
        IconButton iconButtonBottomText = new IconButton(new SvgIcon(SvgFile.COG, IconSize.SIZE_24), "Bottom Icon");
        iconButtonBottomText.setContentDisplay(ContentDisplay.BOTTOM);
        
        primaryButtonBar.getChildren().add(iconButton1);
        primaryButtonBar.getChildren().add(iconButton2);
        primaryButtonBar.getChildren().add(iconButton3);
        primaryButtonBar.getChildren().add(iconButton4);
        primaryButtonBar.getChildren().add(iconButtonLeftText);
        primaryButtonBar.getChildren().add(iconButtonRightText);
        primaryButtonBar.getChildren().add(iconButtonTopText);
        primaryButtonBar.getChildren().add(iconButtonBottomText);

        descriptionPane.setContent(primaryButtonBar);
        return descriptionPane;
    }
}
