package test.demo;

import com.jfoenix.controls.JFXDecorator;
import com.sun.javafx.css.StyleManager;

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
        ThemeManager.getInstance().load(Theme.LIGHT);
        this.stage = stage;
        
        SampleSession session = new SampleSession();
        Settings settings = new Settings();
        
        this.session = session;
        this.settings = settings;

        PaneController controller = new PaneController(session, settings);
        MainPresentation presentation = new MainPresentation(controller);
        
        JFXDecorator decorator = new JFXDecorator(stage, presentation, false, true, true);
        stage.setTitle("RT-FX Demo");
        
        // TODO THIS SHOULD REALLY BE DONE BEFORE RT-FX IS BASELINED:
        // TODO Set the default sheet to the appropriate theme, and remove any reference to Modena.
        // TODO maybe the default can be set to rt-fx, and Modena is added as a secondary sheet?
        Scene scene = new Scene(decorator, settings.getDefaultWindowWidth(), settings.getDefaultWindowHeight());
        scene.getStylesheets().add(ResourceLoader.loadDemoFile("demo.css"));
       
        
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
