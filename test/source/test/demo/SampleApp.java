package test.demo;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mil.af.eglin.ccf.rt.fx.style.Theme;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import mil.af.eglin.ccf.rt.util.ResourceLoader;
import test.demo.abstraction.SampleSession;
import test.demo.abstraction.Settings;
import test.demo.controller.PaneController;
import test.demo.presentation.MainPresentation;

public class SampleApp extends Application
{
    private static Stage stage;
    private SampleSession session;
    private Settings settings;

    public static void main(String[] args)
    {
        launch(args);
    }

    public static Stage getStage()
    {
        return stage;
    }
    
    public SampleSession getSession()
    {
        return this.session;
    }
    
    public Settings getSettings()
    {
        return this.settings;
    }

    @Override
    public void start(Stage stage)
    {
        ThemeManager.getInstance().load(Theme.LIGHT);
        SampleApp.stage = stage;
        
        SampleSession session = new SampleSession();
        Settings settings = new Settings();
        
        this.session = session;
        this.settings = settings;

        PaneController controller = new PaneController(session, settings);
        MainPresentation presentation = new MainPresentation(controller);
        
        JFXDecorator decorator = new JFXDecorator(stage, presentation, false, true, true);
        stage.setTitle("RT-FX Demo");
        
        Scene scene = new Scene(decorator, settings.getDefaultWindowWidth(), settings.getDefaultWindowHeight());
        scene.getStylesheets().add(ResourceLoader.getInstance().loadDemoFile("demo.css"));
       
        
        SampleApp.stage.setScene(scene);
        SampleApp.stage.show();
    }

    @Override
    public void stop()
    {
        if (session != null)
        {
            session.shutdown();
        }
        Platform.exit();
    }
}
