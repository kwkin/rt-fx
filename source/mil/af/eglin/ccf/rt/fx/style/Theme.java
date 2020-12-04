package mil.af.eglin.ccf.rt.fx.style;

/**
 * A {@code Theme} defines a standard color palette that can by dynamically
 * loaded using the {@link ThemeManager ThemeManager}. Specifically, a {@code Theme} defines
 * the theme's name and a CSS file that containing the color values.
 */
public enum Theme
{
    // @formatter:off
    DARK_MATERIAL("Dark Material", "dark-theme-material.css"), 
    DEEP_OCEAN("Deep Ocean", "deep-ocean.css"), 
    DEEP_SEA("Deep Sea", "deep-sea.css"), 
    EMERALD_COAST("Emerald Coast", "emerald-coast.css"), 
    LIGHT("Light", "light-theme.css"), 
    LIGHT_SINGLE("Light", "light-theme-single.css"), 
    CELESTIAL_QUASAR("Celestial Quasar", "celestial-quasar.css");
    // @formatter:on

    private String themeName;
    private String fileName;

    Theme(String themeName, String fileName)
    {
        this.themeName = themeName;
        this.fileName = fileName;
    }

    /**
     * Gets the first {@code Theme} that matches (case-sensitive) the provided name. 
     * 
     * @param name the name to match with a standard {@code Theme}
     * 
     * @return the {@code Theme} matching the provided name
     * @throws IllegalArgumentException if the provided {@code name} does not equal any of the standard theme names
     */
    public static Theme getByName(String name)
    {
        Theme selectedTheme = null;
        for (Theme theme : Theme.values())
        {
            if (theme.toString().equals(name))
            {
                selectedTheme = theme;
                break;
            }
        }
        if (selectedTheme == null)
        {
            throw new IllegalArgumentException(String.format("%s not found in the list of standard thems", name));
        }
        return selectedTheme;
    }

    /**
     * Gets the file path of the CSS file defining the theme
     * 
     * @return the file path of the CSS file defining the theme
     */
    public String getPath()
    {
        return this.fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return themeName;
    }
}
