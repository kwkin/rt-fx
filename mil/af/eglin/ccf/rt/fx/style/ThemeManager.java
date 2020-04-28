package mil.af.eglin.ccf.rt.fx.style;

import com.sun.javafx.css.StyleManager;

import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO use nio paths instead of strings and check if file exists
public class ThemeManager
{
    private final static Theme DEFAULT_THEME = Theme.LIGHT;
    private Theme theme;

    private ThemeManager() 
    {
    }

    private ThemeManager(Theme theme) 
    {
        load(theme);
    }

    private static class InstanceHolder 
    {
        final static ThemeManager INSTANCE = new ThemeManager(DEFAULT_THEME);
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
        }
        return isChanged;
    }
    
    public Theme getCurrentTheme()
    {
        return theme;
    }
}
