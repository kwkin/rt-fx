package mil.af.eglin.ccf.rt.fx.style;

public enum Theme
{
    LIGHT("light-theme.css"),
    DARK("dark-theme.css"),
    DARK2("dark-theme-material.css");
    
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
