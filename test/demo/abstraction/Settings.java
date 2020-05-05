package test.sample.abstraction;

public class Settings
{
    private int DEFAULT_WINDOW_WIDTH = 1920;
    private int DEFAULT_WINDOW_HEIGHT = 1200;
    
    public Settings()
    {
        
    }
    
    public int getDefaultWindowWidth()
    {
        return DEFAULT_WINDOW_WIDTH;
    }
    
    public int getDefaultWindowHeight()
    {
        return DEFAULT_WINDOW_HEIGHT;
    }
}
