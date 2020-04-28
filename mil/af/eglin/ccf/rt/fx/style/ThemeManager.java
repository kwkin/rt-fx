package mil.af.eglin.ccf.rt.fx.style;

import com.sun.javafx.css.StyleManager;

// TODO change to singleton
// TODO use paths instead of strings for the paths
public class ThemeManager
{
    private static Theme theme;
    
    public static void load(Theme theme)
    {
        if (theme != ThemeManager.theme)
        {
            StyleManager.getInstance().addUserAgentStylesheet(theme.getPath());
            if (ThemeManager.theme != null)
            {
                StyleManager.getInstance().removeUserAgentStylesheet(ThemeManager.theme.getPath());
            }
            ThemeManager.theme = theme;
        }
    }
    
    public static Theme getCurrentTheme()
    {
        return theme;
    }
}
