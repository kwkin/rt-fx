package test.sample;

import com.jfoenix.controls.JFXDecorator;
import com.sun.javafx.css.StyleManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mil.af.eglin.ccf.rt.util.ResourceLoader;
import test.sample.abstraction.SampleSession;
import test.sample.abstraction.Settings;
import test.sample.controller.PaneController;
import test.sample.presentation.MainPresentation;

public class SampleApp extends Application
{
    private Stage stage;
    private SampleSession session;
    private Settings settings;

    public static void main(String[] args)
    {
        launch(args);
    }

    public Stage getStage()
    {
        return this.stage;
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
        this.stage = stage;
        
        SampleSession session = new SampleSession();
        Settings settings = new Settings();
        
        this.session = session;
        this.settings = settings;

        PaneController controller = new PaneController(session, settings);
        MainPresentation presentation = new MainPresentation(controller);
        
        JFXDecorator decorator = new JFXDecorator(stage, presentation, false, true, true);
        stage.setTitle("RT-FX Demo");
        
        
        // TODO clean this up
        Scene scene = new Scene(decorator, settings.getDefaultWindowWidth(), settings.getDefaultWindowHeight());
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("fonts.css"));
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("light-theme.css"));
//        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("dark-theme.css"));
//        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadDemoFile("demo.css"));
      
        scene.getStylesheets().add(ResourceLoader.loadJFXCss("demo.css"));
        
        this.stage.setScene(scene);
        this.stage.show();
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
