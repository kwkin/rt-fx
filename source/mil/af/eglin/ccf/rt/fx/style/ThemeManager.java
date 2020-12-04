package mil.af.eglin.ccf.rt.fx.style;

import com.sun.javafx.css.StyleManager;

import javafx.application.Application;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

/**
 * The {@code ThemeManager} can dynamically update the UI of an application by
 * loading standard {@link Theme themes}.
 */
public class ThemeManager
{
    private Theme theme;

    private ThemeManager()
    {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadFile("accents.css"));
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.getInstance().loadFile("fonts.css"));
    }

    private static class InstanceHolder
    {
        final static ThemeManager INSTANCE = new ThemeManager();
    }

    /**
     * Gets the instance of the ThemeManager
     * 
     * @return the instance of the ThemeManager
     */
    public static ThemeManager getInstance()
    {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Loads and updates the applications UI according to the standard {@link Theme theme}
     * 
     * @param theme the theme to load and upadate the UI
     * 
     * @return true if the theme is successfully loaded
     */
    public boolean load(Theme theme)
    {
        boolean isChanged = false;
        if (theme != this.theme)
        {
            String newFilePath = ResourceLoader.getInstance().loadFile(theme.getPath());
            StyleManager.getInstance().addUserAgentStylesheet(newFilePath);

            if (this.theme != null)
            {
                String oldFilePath = ResourceLoader.getInstance().loadFile(this.theme.getPath());
                StyleManager.getInstance().removeUserAgentStylesheet(oldFilePath);
            }
            this.theme = theme;
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Gets the current {@link Theme theme}
     * 
     * @return the current {@link Theme theme}
     */
    public Theme getCurrentTheme()
    {
        return theme;
    }
}
