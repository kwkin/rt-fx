package mil.af.eglin.ccf.rt.fx.style;

import mil.af.eglin.ccf.rt.util.ResourceLoader;

public enum Theme
{
    // TODO change these to nio path
    LIGHT(ResourceLoader.loadFile("light-theme.css")),
    DARK(ResourceLoader.loadFile("dark-theme.css")),
    DARK2(ResourceLoader.loadFile("dark-theme-material.css"));
    
    private String path;
    
    Theme(String path)
    {
        this.path = path;
    }
    
    public String getPath()
    {
        return this.path;
    }
}
