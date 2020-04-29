package mil.af.eglin.ccf.rt.fx.style;

import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO use nio paths instead of strings and check if file exists
// TODO remove reference to scene
public class ThemeManager
{
    private Theme theme;
    private List<Scene> scenes = FXCollections.observableArrayList();

    private ThemeManager() 
    {
    }

    private static class InstanceHolder 
    {
        final static ThemeManager INSTANCE = new ThemeManager();
    }
    
    public static ThemeManager getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    public boolean load(Theme theme)
    {
        boolean isChanged = false;
        if (theme != this.theme)
        {
            String newFilePath = ResourceLoader.loadFile(theme.getPath());
            for (Scene scene : scenes)
            {
                scene.getStylesheets().add(newFilePath);
            }
            StyleManager.getInstance().addUserAgentStylesheet(newFilePath);
            
            if (this.theme != null)
            {
                String oldFilePath = ResourceLoader.loadFile(this.theme.getPath());
                StyleManager.getInstance().removeUserAgentStylesheet(oldFilePath);

                for (Scene scene : scenes)
                {
                    scene.getStylesheets().remove(oldFilePath);
                }
            }
            this.theme = theme;
        }
        return isChanged;
    }
    
    public void addScene(Scene scene)
    {
        this.scenes.add(scene);
    }
    
    public Theme getCurrentTheme()
    {
        return theme;
    }
}
