package test.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.window.CustomDecorationWindowProcess;
import mil.af.eglin.ccf.rt.fx.control.window.WindowDecorator;
import mil.af.eglin.ccf.rt.fx.style.Theme;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;

public class WindowApp extends Application {
    CustomDecorationWindowProcess windowProcEx;

    @Override
    public void start(Stage primaryStage) throws Exception{

        ThemeManager.getInstance().load(Theme.LIGHT);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(16));;
        
        Button testButton = new Button("TEST");
        gridPane.add(testButton, 0, 0);
        
        WindowDecorator decorator = new WindowDecorator(primaryStage, "Custom Decorator", gridPane);
        Scene scene = new Scene(decorator, 300, 275);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}