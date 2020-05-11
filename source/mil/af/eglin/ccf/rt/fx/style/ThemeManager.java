package mil.af.eglin.ccf.rt.fx.style;

import com.sun.javafx.css.StyleManager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO use nio paths instead of strings and check if file exists
// TODO remove reference to scene
public class ThemeManager
{
    private Theme theme;
    private ObservableList<String> sheets = FXCollections.observableArrayList();
    private boolean isLoaded;

    private ThemeManager() 
    {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("accents.css"));
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("fonts.css"));
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
//        if (isLoaded)
//        {
//            for (String sheet : this.sheets)
//            {
//                StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadFile("accents.css"));
//            }
//        }
        if (theme != this.theme)
        {
            String newFilePath = ResourceLoader.loadFile(theme.getPath());
            StyleManager.getInstance().addUserAgentStylesheet(newFilePath);
            
            if (this.theme != null)
            {
                String oldFilePath = ResourceLoader.loadFile(this.theme.getPath());
                StyleManager.getInstance().removeUserAgentStylesheet(oldFilePath);
            }
            this.theme = theme;
        }
        this.isLoaded = true;
        return isChanged;
    }
    
    public Theme getCurrentTheme()
    {
        return theme;
    }
}
