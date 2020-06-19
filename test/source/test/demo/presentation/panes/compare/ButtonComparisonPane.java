package test.demo.presentation.panes.compare;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.IconButton;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.TitledSeparator;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgIcon;
import mil.af.eglin.ccf.rt.fx.layout.CardPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.TitledContentPane;

public class ButtonComparisonPane implements TitledContentPane
{
    private Node content; 
    private static final String TITLE = "Buttons";
    private ToggleSwitch disableToggle;
    
    public ButtonComparisonPane()
    {
        VBox cards = new VBox();
        cards.setSpacing(30);
        
        CardPane usage = new CardPane();
        VBox overview = new VBox();
        DescriptionPane pane = new DescriptionPane();
        pane.setDescription("A simple button control allows the user to take actions with a single press.");
        overview.getChildren().add(new Label("Overview", LabelStyle.LARGE));
        overview.getChildren().add(pane);
        usage.getChildren().add(overview);
        
        CardPane cardPane = new CardPane();
        VBox buttons = new VBox();
        buttons.setSpacing(30);

        buttons.getChildren().add(new Label("Usage"));
        this.disableToggle = new ToggleSwitch("Disable Buttons");
        buttons.getChildren().add(disableToggle);
        buttons.getChildren().add(new TitledSeparator("JavaFX Buttons"));
        buttons.getChildren().add(createFxButtons());
        buttons.getChildren().add(new TitledSeparator("Flat Buttons"));
        buttons.getChildren().add(createFlatButtons());
        buttons.getChildren().add(new TitledSeparator("Raised Buttons"));
        buttons.getChildren().add(createRaisedButtons());
        buttons.getChildren().add(new TitledSeparator("Icon Buttons"));
        buttons.getChildren().add(createIconButtons());
        buttons.getChildren().add(new TitledSeparator("Icon with Text Buttons"));
        buttons.getChildren().add(createIconTextButtons());
        cardPane.getChildren().add(buttons);

        cards.getChildren().add(new Label(TITLE, LabelStyle.TITLE));
        cards.getChildren().add(usage);
        cards.getChildren().add(cardPane);
        
        content = cards;
    }
    
    public Node createFxButtons()
    {
        HBox box = new HBox();
        box.setPadding(new Insets(4, 16, 4, 16));
        box.setSpacing(30);
        
        javafx.scene.control.Button fxButton = new javafx.scene.control.Button("Button");
        javafx.scene.control.Button fxDefaultButton = new javafx.scene.control.Button("Default");
        fxDefaultButton.setDefaultButton(true);
        box.getChildren().add(fxButton);
        box.getChildren().add(fxDefaultButton);
        
        fxButton.disableProperty().bind(disableToggle.selectedProperty());
        fxDefaultButton.disableProperty().bind(disableToggle.selectedProperty());
        return box;
    }
    
    public Node createFlatButtons()
    {
        HBox box = new HBox();
        box.setPadding(new Insets(4, 16, 4, 16));
        box.setSpacing(30);
        
        Button flatPrimary = new Button("BUTTON", ButtonStyle.FLAT, Accent.PRIMARY_MID);
        Button flatSecondary = new Button("ACCENT", ButtonStyle.FLAT, Accent.SECONDARY_MID);
        Button defaultButton = new Button("DEFAULT", ButtonStyle.FLAT, Accent.PRIMARY_MID);
        defaultButton.setDefaultButton(true);
        box.getChildren().add(flatPrimary);
        box.getChildren().add(flatSecondary);
        box.getChildren().add(defaultButton);

        flatPrimary.disableProperty().bind(disableToggle.selectedProperty());
        flatSecondary.disableProperty().bind(disableToggle.selectedProperty());
        defaultButton.disableProperty().bind(disableToggle.selectedProperty());
        return box;
    }
    
    public Node createRaisedButtons()
    {
        HBox box = new HBox();
        box.setPadding(new Insets(4, 16, 4, 16));
        box.setSpacing(30);
        
        Button raisedPrimary = new Button("BUTTON", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        Button raisedSecondary = new Button("ACCENT", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        box.getChildren().add(raisedPrimary);
        box.getChildren().add(raisedSecondary);

        raisedPrimary.disableProperty().bind(disableToggle.selectedProperty());
        raisedSecondary.disableProperty().bind(disableToggle.selectedProperty());
        return box;
    }
    
    public Node createIconButtons()
    {
        HBox box = new HBox();
        box.setPadding(new Insets(4, 16, 4, 16));
        box.setSpacing(30);

        IconButton iconButton1 = new IconButton(new SvgIcon(SvgFile.EMOTICON_EXCITED, IconSize.SIZE_24));
        IconButton iconButton2 = new IconButton(new SvgIcon(SvgFile.MAGNIFY, IconSize.SIZE_24));
        box.getChildren().add(iconButton1);
        box.getChildren().add(iconButton2);

        iconButton1.disableProperty().bind(disableToggle.selectedProperty());
        iconButton2.disableProperty().bind(disableToggle.selectedProperty());
        return box;
    }
    
    public Node createIconTextButtons()
    {
        HBox box = new HBox();
        box.setPadding(new Insets(4, 16, 4, 16));
        box.setSpacing(30);

        IconButton iconButton1 = new IconButton(new SvgIcon(SvgFile.MAGNIFY_PLUS, IconSize.SIZE_24), "Zoom in");
        IconButton iconButton2 = new IconButton(new SvgIcon(SvgFile.MAGNIFY_MINUS, IconSize.SIZE_24), "Zoom out");
        iconButton2.setContentDisplay(ContentDisplay.TOP);
        box.getChildren().add(iconButton1);
        box.getChildren().add(iconButton2);

        iconButton1.disableProperty().bind(disableToggle.selectedProperty());
        iconButton2.disableProperty().bind(disableToggle.selectedProperty());
        return box;
    }

    @Override
    public String getTitle()
    {
        return TITLE;
    }

    @Override
    public Node getNodeContent()
    {
        return content;
    }
}
