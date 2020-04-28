package mil.af.eglin.ccf.rt.fx.style;

public enum Theme
{
    LIGHT("light-theme.css"),
    DARK("dark-theme.css"),
    DARK2("dark-theme-material.css"),
    DARK3("dark-theme-3.css");
    
    private String fileName;
    
    Theme(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getPath()
    {
        return this.fileName;
    }
}
