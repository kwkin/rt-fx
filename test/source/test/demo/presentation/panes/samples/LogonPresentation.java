package test.demo.presentation.panes.samples;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.PasswordField;
import mil.af.eglin.ccf.rt.fx.control.RadioButton;
import mil.af.eglin.ccf.rt.fx.control.Text;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.BorderPane;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.HBox;
import mil.af.eglin.ccf.rt.fx.layout.TextFlow;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.SizedTitledCard;

public class LogonPresentation extends GridPane
{
    private static final String TITLE = "Login";

    public LogonPresentation()
    {
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setFillWidth(true);
        constraints.setPercentWidth(50);

        setVgap(16);
        setHgap(24);
        getColumnConstraints().addAll(constraints, constraints);

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
        TextFlow notificationDetails = new TextFlow(
                new Text("Send me regular notifications on the status of my delivery."));
        ToggleSwitch notify = new ToggleSwitch(Accent.SECONDARY_MID);
        notify.setSelected(true);
        GridPane.setHalignment(notify, HPos.RIGHT);
        notificationsLayout.setCenter(notificationDetails);
        notificationsLayout.setRight(notify);

        int row = 0;
        add(firstName, 0, row, 1, 1);
        add(lastName, 1, row++, 1, 1);
        add(email, 0, row++, 2, 1);
        add(streetAddress, 0, row++, 2, 1);
        add(zipCode, 0, row, 1, 1);
        add(city, 1, row++, 1, 1);
        add(additionalInfo, 0, row++, 2, 1);
        add(shippingLayout, 0, row++, 2, 1);
        add(notificationsLayout, 0, row++, 2, 1);
    }
}
