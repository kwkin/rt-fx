package mil.af.eglin.ccf.rt.fx.style;

import com.sun.javafx.css.StyleManager;

import javafx.application.Application;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO use nio paths instead of strings and check if file exists
// TODO remove reference to scene
public class ThemeManager
{
    private Theme theme;

    private ThemeManager() 
    {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
//        sheets.add(ResourceLoader.loadFile("accents.css"));
//        sheets.add(ResourceLoader.loadFile("fonts.css"));
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
            isChanged = true;
        }
        return isChanged;
    }
    
    public Theme getCurrentTheme()
    {
        return theme;
    }
}
