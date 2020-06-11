package mil.af.eglin.ccf.rt.fx.style;

public enum Theme
{
    DARK_MATERIAL("Dark Material", "dark-theme-material.css"),
    DEEP_OCEAN("Deep Ocean", "deep-ocean.css"),
    DEEP_SEA("Deep Sea", "deep-sea.css"),
    EMERALD_COAST("Emerald Coast", "emerald-coast.css"),
    LIGHT("Light", "light-theme.css"),
    LIGHT_SINGLE("Light", "light-theme-single.css"),
    CELESTIAL_QUASAR("Celestial Quasar", "celestial-quasar.css");

    private String themeName;
    private String fileName;
    
    Theme(String themeName, String fileName)
    {
        this.themeName = themeName;
        this.fileName = fileName;
    }
    
    public String getPath()
    {
        return this.fileName;
    }
    
    public static Theme getByName(String name)
    {
        Theme selectedTheme = Theme.LIGHT;
        for (Theme theme : Theme.values())
        {
            if (theme.toString().equals(name))
            {
                selectedTheme = theme;
                break;
            }
        }
        return selectedTheme;
    }
    
    @Override
    public String toString()
    {
        return themeName; 
    }
}
