package test.demo.presentation.panes.samples;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.PasswordField;
import mil.af.eglin.ccf.rt.fx.control.Text;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.ToggleSwitch;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.icons.IconSize;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgGlyph;
import mil.af.eglin.ccf.rt.fx.icons.svg.SvgFile;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.TextFlow;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.demo.control.SizedTitledCard;

public class LogonPresentation extends SizedTitledCard
{
    private static final String TITLE = "Login";
    
    public LogonPresentation()
    {
        super(TITLE);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        
        ColumnConstraints centerConstraint = new ColumnConstraints();
        centerConstraint.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().add(centerConstraint);

        VBox vBox = new VBox(Accent.BASE_LIGHT);
        vBox.setPadding(new Insets(0, 0, 10, 0));
         
        SvgGlyph icon = new SvgGlyph(SvgFile.ACCOUNT, IconSize.SIZE_64);
        GridPane iconGridPane = new GridPane();
        iconGridPane.setPadding(new Insets(0, 0, 10, 0));
        iconGridPane.setAlignment(Pos.CENTER);
        iconGridPane.getColumnConstraints().add(centerConstraint);
        iconGridPane.add(icon, 0, 0);
        
        VBox usernameVBox = new VBox();
        Label usernameLabel = new Label("Username");
        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("Email");
        usernameVBox.getChildren().add(usernameLabel);
        usernameVBox.getChildren().add(usernameTextField);

        VBox passwordVBox = new VBox();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordVBox.getChildren().add(passwordLabel);
        passwordVBox.getChildren().add(passwordField);

        vBox.getChildren().add(iconGridPane);
        vBox.getChildren().add(usernameVBox);
        vBox.getChildren().add(passwordVBox);
        
        Label settingsLabel = new Label("Settings", LabelStyle.LARGE, Accent.PRIMARY_DARK);
        GridPane switchPane = new GridPane();
        GridPane.setHalignment(settingsLabel, HPos.CENTER);
        TextFlow option1Text = new TextFlow(new Text("Some configuration options for configuration option 1."));
        ToggleSwitch option1 = new ToggleSwitch(Accent.SECONDARY_DARK);
        option1.setSelected(true);
        TextFlow option2Text = new TextFlow(new Text("Some configuration options for configuration option 2."));
        ToggleSwitch option2 = new ToggleSwitch(Accent.SECONDARY_DARK);
        TextFlow option3Text = new TextFlow(new Text("Some configuration options for configuration option 3."));
        ToggleSwitch option3 = new ToggleSwitch(Accent.SECONDARY_DARK);
        option3.setSelected(true);
        TextFlow option4Text = new TextFlow(new Text("Some configuration options for configuration option 4."));
        ToggleSwitch option4 = new ToggleSwitch(Accent.SECONDARY_DARK);
        TextFlow option5Text = new TextFlow(new Text("Some configuration options for configuration option 5."));
        ToggleSwitch option5 = new ToggleSwitch(Accent.SECONDARY_DARK);
        option5.setSelected(true);

        GridPane buttonGridPane = new GridPane(Accent.BASE_DARK);
        buttonGridPane.setAlignment(Pos.CENTER);
        Button button = new Button("Save");
        buttonGridPane.add(button, 0, 0);
        
        int index = 0;
        switchPane.add(settingsLabel, 0, index++, 2, 1);
        switchPane.add(option1Text, 0, index);
        switchPane.add(option1, 1, index++);
        switchPane.add(option2Text, 0, index);
        switchPane.add(option2, 1, index++);
        switchPane.add(option3Text, 0, index);
        switchPane.add(option3, 1, index++);
        switchPane.add(option4Text, 0, index);
        switchPane.add(option4, 1, index++);
        switchPane.add(option5Text, 0, index);
        switchPane.add(option5, 1, index++);
        
        gridPane.add(vBox, 0, 0);
        gridPane.add(switchPane, 0, 1);
        gridPane.add(buttonGridPane, 0, 2);
        
        setContent(gridPane);
    }
}
