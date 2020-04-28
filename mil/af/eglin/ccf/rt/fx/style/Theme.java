package mil.af.eglin.ccf.rt.fx.style;

public enum Theme
{
    LIGHT("Light", "light-theme.css"),
    EMERALD_COAST("Emerald Coast", "emerald-coast.css"),
    DARK2("Dark Material", "dark-theme-material.css"),
    DEEP_SEA("Deep Sea", "deep-sea.css");

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
    
    @Override
    public String toString()
    {
        return name; 
    }
}
