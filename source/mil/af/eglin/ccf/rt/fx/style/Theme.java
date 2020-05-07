package mil.af.eglin.ccf.rt.fx.style;

public enum Theme
{
    DARK_MATERIAL("Dark Material", "dark-theme-material.css"),
    DEEP_SEA("Deep Sea", "deep-sea.css"),
    EMERALD_COAST("Emerald Coast", "emerald-coast.css"),
    LIGHT("Light", "light-theme.css"),
    LIGHT_SINGLE("Light", "light-theme-single.css"),
    PINE_TREES_AND_LOG_CABINS("Pine Trees and Log Cabins", "pine-trees-and-log-cabins.css"),
    CELESTIAL_QUASAR("Celestial Quasar", "celestial-quasar.css");

    private String name;
    private String fileName;
    
    Theme(String name, String fileName)
    {
        this.name = name;
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
        return name; 
    }
}
