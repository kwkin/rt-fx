package test.sample.presentation.panes.controls;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
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
import test.sample.control.TitledCard;
import test.sample.control.DescriptionPane;
import test.sample.controller.PaneController;

public class ButtonPanePresentation extends TitledCard
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
        
        ButtonBar primaryButtonPane = new ButtonBar();
        Button primaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        Button primaryMidButton = new Button("Cancel", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        Button primaryDarkButton = new Button("Apply", ButtonStyle.RAISED, Accent.PRIMARY_DARK);
        primaryButtonPane.getButtons().add(primaryLightButton);
        primaryButtonPane.getButtons().add(primaryMidButton);
        primaryButtonPane.getButtons().add(primaryDarkButton);

        ButtonBar secondaryButtonPane = new ButtonBar();
        Button secondaryLightButton = new Button("OK", ButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        Button secondaryMidButton = new Button("Cancel", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        Button secondaryDarkButton = new Button("Apply", ButtonStyle.RAISED, Accent.SECONDARY_DARK);
        secondaryButtonPane.getButtons().add(secondaryLightButton);
        secondaryButtonPane.getButtons().add(secondaryMidButton);
        secondaryButtonPane.getButtons().add(secondaryDarkButton);

        ButtonBar baseButtonPane = new ButtonBar();
        Button baseLightButton = new Button("OK", ButtonStyle.RAISED, Accent.BASE_LIGHT);
        baseLightButton.setDefaultButton(true);
        Button baseMidButton = new Button("Cancel", ButtonStyle.RAISED, Accent.BASE_MID);
        Button baseDarkButton = new Button("Apply", ButtonStyle.RAISED, Accent.BASE_DARK);
        baseButtonPane.getButtons().add(baseLightButton);
        baseButtonPane.getButtons().add(baseMidButton);
        baseButtonPane.getButtons().add(baseDarkButton);

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

        VBox buttonVBox = new VBox();
        
        ButtonBar primaryButtonBar = new ButtonBar();
        Button primaryLightFlatButton = new Button("Discard", ButtonStyle.FLAT, Accent.PRIMARY_LIGHT);
        Button primaryMidFlatButton = new Button("Apply", ButtonStyle.FLAT, Accent.PRIMARY_MID);
        Button primaryDarkFlatButton = new Button("Add", ButtonStyle.FLAT, Accent.PRIMARY_DARK);
        primaryButtonBar.getButtons().add(primaryLightFlatButton);
        primaryButtonBar.getButtons().add(primaryMidFlatButton);
        primaryButtonBar.getButtons().add(primaryDarkFlatButton);

        ButtonBar secondaryButtonBar = new ButtonBar();
        Button secondaryLightFlatButton = new Button("Discard", ButtonStyle.FLAT, Accent.SECONDARY_LIGHT);
        Button secondaryMidFlatButton = new Button("Apply", ButtonStyle.FLAT, Accent.SECONDARY_MID);
        Button secondaryDarkFlatButton = new Button("Add", ButtonStyle.FLAT, Accent.SECONDARY_DARK);
        secondaryButtonBar.getButtons().add(secondaryLightFlatButton);
        secondaryButtonBar.getButtons().add(secondaryMidFlatButton);
        secondaryButtonBar.getButtons().add(secondaryDarkFlatButton);
        
        buttonVBox.getChildren().add(primaryButtonBar);
        buttonVBox.getChildren().add(secondaryButtonBar);

        descriptionPane.setContent(buttonVBox);
        
        return descriptionPane;
    }
    
    private Node createIconButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.setDescription("Icon buttons may appear with or without text.");
        
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
