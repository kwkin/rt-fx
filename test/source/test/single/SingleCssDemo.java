package test.single;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mil.af.eglin.ccf.rt.fx.style.Theme;
import mil.af.eglin.ccf.rt.fx.style.ThemeManager;
import test.demo.abstraction.SampleSession;
import test.demo.abstraction.Settings;
import test.demo.controller.PaneController;
import test.single.presentation.MainPresentation;

public class SingleCssDemo extends Application
{
    private Stage stage;

    public static void main(String[] args)
    {
        launch(args);
    }

    public Stage getStage()
    {
        return this.stage;
    }
    
    @Override
    public void start(Stage stage)
    {
        ThemeManager.getInstance().load(Theme.LIGHT_SINGLE);
        this.stage = stage;
        this.stage.setTitle("RT-FX Single CSS");

        SampleSession session = new SampleSession();
        Settings settings = new Settings();

        PaneController controller = new PaneController(session, settings);
        MainPresentation presentation = new MainPresentation(controller);
        Scene scene = new Scene(presentation, settings.getDefaultWindowWidth(), settings.getDefaultWindowHeight());

        
        this.stage.setScene(scene);
        this.stage.show();
    }
}
