package test.demo.abstraction.data.immutable;

import mil.af.eglin.ccf.rt.fx.style.Theme;

public enum DemoTheme
{
    DARK_MATERIAL(Theme.DARK_MATERIAL, "demo-dark.css"),
    DEEP_OCEAN(Theme.DEEP_OCEAN,  "demo-dark.css"),
    DEEP_SEA(Theme.DEEP_SEA, "demo-dark.css"),
    EMERALD_COAST(Theme.EMERALD_COAST, "demo-dark.css"),
    LIGHT(Theme.LIGHT, ""),
    CELESTIAL_QUASAR(Theme.CELESTIAL_QUASAR, "demo-dark.css");
    
    private Theme theme;
    private String demoFile;
    
    DemoTheme(Theme theme, String demoFile)
    {
        this.theme = theme;
        this.demoFile = demoFile;
    }
    
    public Theme getTheme()
    {
        return this.theme;
    }
    
    public String getDemoFile()
    {
        return this.demoFile;
    }
}
