package test.demo.presentation.panes.controls;

import javafx.scene.Node;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSizes;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcons;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
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
        vBox.getChildren().add(createNormalButtons());
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createFlatButtons());
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createIconButtons());
        
        setContent(vBox);
    }
    
    private Node createNormalButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Normal buttons come in several varieties: raised, flat, icon, and icon with text. Here are some raised buttons.");

        VBox buttonVBox = new VBox();
        buttonVBox.setSpacing(16);
        
        HBox primaryButtonPane = new HBox();
        primaryButtonPane.setSpacing(14);
        Button primaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        Button primaryMidButton = new Button("CANCEL", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        Button primaryDarkButton = new Button("APPLY", ButtonStyle.RAISED, Accent.PRIMARY_DARK);
        primaryButtonPane.getChildren().add(primaryLightButton);
        primaryButtonPane.getChildren().add(primaryMidButton);
        primaryButtonPane.getChildren().add(primaryDarkButton);

        HBox secondaryButtonPane = new HBox();
        secondaryButtonPane.setSpacing(14);
        Button secondaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        Button secondaryMidButton = new Button("CANCEL", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        Button secondaryDarkButton = new Button("APPLY", ButtonStyle.RAISED, Accent.SECONDARY_DARK);
        secondaryButtonPane.getChildren().add(secondaryLightButton);
        secondaryButtonPane.getChildren().add(secondaryMidButton);
        secondaryButtonPane.getChildren().add(secondaryDarkButton);

        HBox baseButtonPane = new HBox();
        baseButtonPane.setSpacing(14);
        Button baseLightButton = new Button("OK", ButtonStyle.RAISED, Accent.BASE_LIGHT);
        baseLightButton.setDefaultButton(true);
        Button baseMidButton = new Button("CANCEL", ButtonStyle.RAISED, Accent.BASE_MID);
        Button baseDarkButton = new Button("APPLY", ButtonStyle.RAISED, Accent.BASE_DARK);
        baseButtonPane.getChildren().add(baseLightButton);
        baseButtonPane.getChildren().add(baseMidButton);
        baseButtonPane.getChildren().add(baseDarkButton);

        buttonVBox.getChildren().add(primaryButtonPane);
        buttonVBox.getChildren().add(secondaryButtonPane);
        buttonVBox.getChildren().add(baseButtonPane);
        
        descriptionPane.setContent(buttonVBox);
        
        return descriptionPane;
    }
    
    private Node createFlatButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Flat buttons behave the same as regular buttons, but they appear to be on the same level as its container.");

        // TODO figure out why these are linked to the other VBox properties
        VBox buttonVBox = new VBox();
        buttonVBox.setSpacing(16);
        
        HBox primaryButtonBar = new HBox();
        primaryButtonBar.setSpacing(14);
        Button primaryLightFlatButton = new Button("DISCARD", ButtonStyle.FLAT, Accent.PRIMARY_LIGHT);
        Button primaryMidFlatButton = new Button("APPLY", ButtonStyle.FLAT, Accent.PRIMARY_MID);
        Button primaryDarkFlatButton = new Button("ADD", ButtonStyle.FLAT, Accent.PRIMARY_DARK);
        primaryButtonBar.getChildren().add(primaryLightFlatButton);
        primaryButtonBar.getChildren().add(primaryMidFlatButton);
        primaryButtonBar.getChildren().add(primaryDarkFlatButton);

        HBox secondaryButtonBar = new HBox();
        secondaryButtonBar.setSpacing(14);
        Button secondaryLightFlatButton = new Button("DISCARD", ButtonStyle.FLAT, Accent.SECONDARY_LIGHT);
        Button secondaryMidFlatButton = new Button("APPLY", ButtonStyle.FLAT, Accent.SECONDARY_MID);
        Button secondaryDarkFlatButton = new Button("ADD", ButtonStyle.FLAT, Accent.SECONDARY_DARK);
        secondaryButtonBar.getChildren().add(secondaryLightFlatButton);
        secondaryButtonBar.getChildren().add(secondaryMidFlatButton);
        secondaryButtonBar.getChildren().add(secondaryDarkFlatButton);
        
        buttonVBox.getChildren().add(primaryButtonBar);
        buttonVBox.getChildren().add(secondaryButtonBar);

        descriptionPane.setContent(buttonVBox);
        
        return descriptionPane;
    }
    
    private Node createIconButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icon buttons are similar to flat buttons, but have a tighter padding and different API.");
        
        HBox primaryButtonBar = new HBox();
        IconButton iconButton1 = new IconButton(new SvgIcon(SvgIcons.MAGNIFY_MINUS, IconSizes.SIZE_24));
        IconButton iconButton2 = new IconButton(new SvgIcon(SvgIcons.MAGNIFY, IconSizes.SIZE_24));
        IconButton iconButton3 = new IconButton(new SvgIcon(SvgIcons.MAGNIFY_PLUS, IconSizes.SIZE_24));
        IconButton iconButton4 = new IconButton(new SvgIcon(SvgIcons.MAGNIFY_REMOVE_CURSOR, IconSizes.SIZE_24));
        IconButton iconButton5 = new IconButton(new SvgIcon(SvgIcons.COG, IconSizes.SIZE_24), "With Text");
        primaryButtonBar.getChildren().add(iconButton1);
        primaryButtonBar.getChildren().add(iconButton2);
        primaryButtonBar.getChildren().add(iconButton3);
        primaryButtonBar.getChildren().add(iconButton4);
        primaryButtonBar.getChildren().add(iconButton5);

        descriptionPane.setContent(primaryButtonBar);
        
        return descriptionPane;
    }
}
