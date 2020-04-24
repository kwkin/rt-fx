package test.jfeonix.presentation.panes.controls;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import mil.af.eglin.ccf.rt.fx.control.Label;
import mil.af.eglin.ccf.rt.fx.control.PasswordField;
import mil.af.eglin.ccf.rt.fx.control.TextArea;
import mil.af.eglin.ccf.rt.fx.control.TextField;
import mil.af.eglin.ccf.rt.fx.control.style.LabelStyle;
import mil.af.eglin.ccf.rt.fx.layout.GridPane;
import mil.af.eglin.ccf.rt.fx.layout.VBox;
import test.sample.control.DescriptionPane;
import test.sample.control.TitledCard;
import test.sample.control.TitledCardSize;
import test.sample.controller.PaneController;

public class TextFieldComparison extends TitledCard
{
    private static final String TITLE = "TextFields";

    public TextFieldComparison(PaneController controller)
    {
        super(TITLE, TitledCardSize.SIZE_2x3);

        GridPane grid = new GridPane();
        grid.setVgap(30);
        GridPane.setHgrow(grid, Priority.ALWAYS);

        ColumnConstraints rightConstraint = new ColumnConstraints();
        rightConstraint.setFillWidth(true);
        rightConstraint.setPercentWidth(50);
        ColumnConstraints leftConstraint = new ColumnConstraints();
        leftConstraint.setFillWidth(true);
        leftConstraint.setPercentWidth(50);
        grid.getColumnConstraints().addAll(leftConstraint, leftConstraint);

        Label rtLabel = new Label("RT-FX Text Fields", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxLabel = new Label("JFeonix Text Fields", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);

        DescriptionPane horizontalDescription = new DescriptionPane();
        horizontalDescription.addLine("-Animation (Border)");
        horizontalDescription.addLine("-Background Fill");
        horizontalDescription.addLine("-Floating Prompt Text");
        horizontalDescription.addLine("-Validation");

        Label rtAreaLabel = new Label("RT-FX Text Area", LabelStyle.LARGE);
        rtLabel.setAlignment(Pos.CENTER);
        rtLabel.setMaxWidth(Double.MAX_VALUE);
        Label jfxAreaLabel = new Label("JFeonix Text Area", LabelStyle.LARGE);
        jfxLabel.setAlignment(Pos.CENTER);
        jfxLabel.setMaxWidth(Double.MAX_VALUE);
        
        int row = 0;
        javafx.scene.control.TextField fxComponent = new javafx.scene.control.TextField("JavaFX TextField");
        GridPane.setHalignment(fxComponent, HPos.CENTER);
        grid.add(fxComponent,                0, row++, 2, 1);
        grid.add(rtLabel,               0, row  , 1, 1);
        grid.add(jfxLabel,              1, row++, 1, 1);
        grid.add(createRtTextFields(),  0, row  , 1, 1);
        grid.add(createJfxTextFields(), 1, row++, 1, 1);

        grid.add(rtAreaLabel,           0, row  , 1, 1);
        grid.add(jfxAreaLabel,          1, row++, 1, 1);
        grid.add(createRtTextArea(),    0, row  , 1, 1);
        grid.add(createJfxTextArea(),   1, row++, 1, 1);
        grid.add(horizontalDescription, 0, row++, 2, 1);
        setContent(grid);
    }

    public Node createRtTextFields()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        TextField rtTextField = new TextField();
        rtTextField.setPromptText("Prompt Text");
        PasswordField rtPasswordField = new PasswordField();
        rtPasswordField.setText("abcdefg");
        TextField rtDisabledTextField = new TextField();
        rtDisabledTextField.setPromptText("Disabled");
        rtDisabledTextField.setDisable(true);
        box.getChildren().add(rtTextField);
        box.getChildren().add(rtPasswordField);
        box.getChildren().add(rtDisabledTextField);
        return box;
    }

    public Node createJfxTextFields()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        JFXTextField jfxNoFloating = new JFXTextField();
        jfxNoFloating.setPromptText("No floating");
        JFXTextField jfxFloating = new JFXTextField();
        jfxFloating.setPromptText("Floating");
        jfxFloating.setLabelFloat(true);
        JFXPasswordField jfxPasswordField = new JFXPasswordField();
        jfxPasswordField.setPromptText("Password");
        jfxPasswordField.setLabelFloat(true);
        JFXTextField requiredTextField = new JFXTextField();
        requiredTextField.setPromptText("Required Field");
        requiredTextField.setLabelFloat(true);
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Input Required");
        requiredTextField.setValidators(requiredFieldValidator);
        requiredTextField.focusedProperty().addListener((ov, oldVal, newVal) ->
        {
            if (!newVal)
            {
                requiredTextField.validate();
            }
        });
        JFXTextField jfxDisabled = new JFXTextField();
        jfxDisabled.setDisable(true);
        jfxDisabled.setPromptText("Disabled");
        box.getChildren().add(jfxNoFloating);
        box.getChildren().add(jfxFloating);
        box.getChildren().add(jfxPasswordField);
        box.getChildren().add(requiredTextField);
        box.getChildren().add(jfxDisabled);
        return box;
    }

    public Node createRtTextArea()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        TextArea textArea = new TextArea();
        textArea.setPromptText("Prompt Text");
        TextArea disabledTextArea = new TextArea();
        disabledTextArea.setPromptText("Disabled");
        disabledTextArea.setDisable(true);
        box.getChildren().add(textArea);
        box.getChildren().add(disabledTextArea);
        return box;
    }

    public Node createJfxTextArea()
    {
        VBox box = new VBox();
        box.setSpacing(30);
        JFXTextArea textArea = new JFXTextArea();
        textArea.setPromptText("Prompt Text");
        textArea.setLabelFloat(true);
        JFXTextArea disabledTextArea = new JFXTextArea();
        disabledTextArea.setPromptText("Disabled");
        disabledTextArea.setDisable(true);
        box.getChildren().add(textArea);
        box.getChildren().add(disabledTextArea);
        return box;
    }
}
