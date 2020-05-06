package test.demo.control;

import mil.af.eglin.ccf.rt.fx.control.CheckBox;
import mil.af.eglin.ccf.rt.fx.control.Dialog;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;

import com.jfoenix.controls.JFXDecorator;

import javafx.geometry.HPos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.Text;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.TextFlow;

public class LoginDialog extends Dialog<Boolean>
{
    public LoginDialog()
    {
        setTitle("Login");
        
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setFillWidth(true);
        constraints.setPercentWidth(50);
        
        GridPane grid = new GridPane();
        grid.setVgap(16);
        grid.setHgap(24);
        grid.getColumnConstraints().addAll(constraints, constraints);
        
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        firstName.setLabelFloat(true);
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        lastName.setLabelFloat(true);

        TextField email = new TextField();
        email.setPromptText("Email");
        email.setLabelFloat(true);

        TextField streetAddress = new TextField();
        streetAddress.setPromptText("Street Address");
        streetAddress.setLabelFloat(true);

        TextField zipCode = new TextField();
        zipCode.setPromptText("Zip Code");
        zipCode.setLabelFloat(true);
        TextField city = new TextField();
        city.setPromptText("City");
        city.setLabelFloat(true);

        TextArea additionalInfo = new TextArea();
        additionalInfo.setPromptText("Additional Info");
        
        HBox shippingLayout = new HBox();
        Label shippingSpeed = new Label("Shipping Speed: ");
        ToggleGroup shippingGroup = new ToggleGroup();
        RadioButton firstDayShipping = new RadioButton("First Day", Accent.SECONDARY_LIGHT);
        firstDayShipping.setToggleGroup(shippingGroup);
        RadioButton priorityshipping = new RadioButton("Priority (2-3 days)", Accent.SECONDARY_MID);
        priorityshipping.setToggleGroup(shippingGroup);
        RadioButton noRushShipping = new RadioButton("No Rush (5-7 days)", Accent.SECONDARY_DARK);
        noRushShipping.setToggleGroup(shippingGroup);
        shippingLayout.getChildren().add(shippingSpeed);
        shippingLayout.getChildren().add(firstDayShipping);
        shippingLayout.getChildren().add(priorityshipping);
        shippingLayout.getChildren().add(noRushShipping);

        BorderPane notificationsLayout = new BorderPane();
        GridPane.setFillWidth(notificationsLayout, true);
        TextFlow notificationDetails = new TextFlow(new Text("Send me regular notifications on the status of my delivery."));
        ToggleSwitch notify = new ToggleSwitch(Accent.SECONDARY_MID);
        notify.setSelected(true);
        GridPane.setHalignment(notify, HPos.RIGHT);
        notificationsLayout.setCenter(notificationDetails);
        notificationsLayout.setRight(notify);
        
        int row = 0;
        grid.add(firstName, 0, row, 1, 1);
        grid.add(lastName, 1, row++, 1, 1);
        grid.add(email, 0, row++, 2, 1);
        grid.add(streetAddress, 0, row++, 2, 1);
        grid.add(zipCode, 0, row, 1, 1);
        grid.add(city, 1, row++, 1, 1);
        grid.add(additionalInfo, 0, row++, 2, 1);
        grid.add(shippingLayout, 0, row++, 2, 1);
        grid.add(notificationsLayout, 0, row++, 2, 1);
        
        ButtonType loginButtonType = new ButtonType("Submit", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        getDialogPane().setContent(grid);
//        Stage stage = (Stage) getDialogPane().getScene().getWindow();
//        JFXDecorator decorator = new JFXDecorator(stage, grid, false, true, true);
//        getDialogPane().setContent(decorator);
    }
}
