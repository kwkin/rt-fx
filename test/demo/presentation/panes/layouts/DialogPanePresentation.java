package test.demo.presentation.panes.layouts;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.Separator;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.DescriptionPane;
import test.demo.control.LoginDialog;
import test.demo.control.SizedTitledCard;
import test.demo.control.TitledCardSize;
import test.demo.controller.PaneController;

public class DialogPanePresentation extends SizedTitledCard
{
    private static final String TITLE = "Dialogs";
    
    public DialogPanePresentation(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_1x2);

        VBox vBox = new VBox();
        
        vBox.getChildren().add(createDialogButtons());
        vBox.getChildren().add(new Separator());
        vBox.getChildren().add(createCustomDialogButton());
        
        setContent(vBox);
    }
    
    private Node createDialogButtons()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("There are several types of standard alert dialogs.");

        GridPane pane = new GridPane();
        Button errorButton = new Button("Error", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        errorButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.ERROR);
            dialog.setTitle("Error Dialog");
            dialog.setHeaderText("Error Header");
            dialog.setContentText("This is a message detailing the context of the error.");
            dialog.showAndWait();
        });
        
        Button headerlessErrorButton = new Button("Headerless Error", ButtonStyle.RAISED, Accent.PRIMARY_MID);
        headerlessErrorButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.ERROR);
            dialog.setTitle("Error Dialog");
            dialog.setHeaderText(null);
            dialog.setContentText("This is a message detailing the context of the error.");
            dialog.showAndWait();
        });
        
        Button warningButton = new Button("Warning", ButtonStyle.RAISED, Accent.PRIMARY_DARK);
        warningButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setTitle("Warning Dialog");
            dialog.setHeaderText("Warning Header");
            dialog.setContentText("This is a message detailing the context of the warning.");
            dialog.showAndWait();
        });
        
        Button headerlessWarningButton = new Button("Headerless Warning", ButtonStyle.RAISED, Accent.PRIMARY_DARK);
        headerlessWarningButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setTitle("Warning Dialog");
            dialog.setHeaderText(null);
            dialog.setContentText("This is a message detailing the context of the warning.");
            dialog.showAndWait();
        });
        
        Button informationButton = new Button("Information", ButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        informationButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.INFORMATION);
            dialog.setTitle("Information Dialog");
            dialog.setHeaderText("Information Header");
            dialog.setContentText("This is a message detailing the context of the information.");
            dialog.showAndWait();
        });
        
        Button headerlessInformationButton = new Button("Headerless Information", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        headerlessInformationButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.INFORMATION);
            dialog.setTitle("Information Dialog");
            dialog.setHeaderText(null);
            dialog.setContentText("This is a message detailing the context of the information.");
            dialog.showAndWait();
        });
        
        Button confirmationButton = new Button("Confirmation", ButtonStyle.RAISED, Accent.SECONDARY_LIGHT);
        confirmationButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.CONFIRMATION);
            dialog.setTitle("Confirmation Dialog");
            dialog.setHeaderText("Confirmation Header");
            dialog.setContentText("This is a message detailing the context of the confirmation.");
            dialog.showAndWait();
        });
        
        Button headerlessConfirmationButton = new Button("Headerless Confirmation", ButtonStyle.RAISED, Accent.SECONDARY_MID);
        headerlessConfirmationButton.setOnAction(action -> 
        {
            Alert dialog = new Alert(AlertType.CONFIRMATION);
            dialog.setTitle("Confirmation Dialog");
            dialog.setHeaderText(null);
            dialog.setContentText("This is a message detailing the context of the confirmation.");
            dialog.showAndWait();
            
        });
        
        int row = 0;
        pane.add(errorButton, 0, row);
        pane.add(headerlessErrorButton, 1, row++);
        pane.add(warningButton, 0, row);
        pane.add(headerlessWarningButton, 1, row++);
        pane.add(informationButton, 0, row);
        pane.add(headerlessInformationButton, 1, row++);
        pane.add(confirmationButton, 0, row);
        pane.add(headerlessConfirmationButton, 1, row++);
        
        descriptionPane.setContent(pane);
        return descriptionPane;
    }

    private Node createCustomDialogButton()
    {
        DescriptionPane descriptionPane = new DescriptionPane();
        descriptionPane.addLine("There are several types of standard alert dialogs.");

        VBox pane = new VBox();
        Button customDialogButton = new Button("Custom", ButtonStyle.RAISED, Accent.PRIMARY_LIGHT);
        customDialogButton.setOnAction(action ->
        {
            LoginDialog customDialog = new LoginDialog();
            customDialog.showAndWait();
        });
        pane.getChildren().add(customDialogButton);
        
        descriptionPane.setContent(pane);
        return descriptionPane;
    }
}