package mil.af.eglin.ccf.rt.util;

import java.io.InputStream;
import java.net.URL;

import mil.af.eglin.ccf.rt.fx.utils.DepthManager;

/**
 * The {@code ResourceLoader} generates URL strings to standard rt-fx data
 * directories.
 */
public class ResourceLoader
{
    private final static String DATA_DIRECTORY = "data/";
    private final static String COMPONENTS_DIRECTORY = "data/components/";
    private final static String LAYOUTS_DIRECTORY = "data/layouts/";
    private final static String ICONS_DIRECTORY = "data/icons/";
    private final static String DEMO_DATA_DIRECTORY = "data/";

    private ResourceLoader()
    {
        
    }

    private static class InstanceHolder 
    {
        final static ResourceLoader INSTANCE = new ResourceLoader();
    }
    
    /**
     * Gets the instance of the {@code DepthManager}
     * 
     * @return the instance of the {@code DepthManager}
     */
    public static ResourceLoader getInstance()
    {
        return InstanceHolder.INSTANCE;
    }
    
    /**
     * Returns a URL string to the file in the data directory.
     * 
     * @param fileName the file name
     * @return a URL string for the full path to the provided file.
     */
    public String loadFile(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(DATA_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    /**
     * Returns a URL string to the file in the component data directory.
     * 
     * @param fileName the file name
     * @return a URL string for the full path to the provided file.
     */
    public String loadComponent(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(COMPONENTS_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    /**
     * Returns a URL string to the file in the layout data directory.
     * 
     * @param fileName the file name
     * @return a URL string for the full path to the provided file.
     */
    public String loadLayouts(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(LAYOUTS_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    /**
     * Returns a URL string to the file in the demo data directory.
     * 
     * @param fileName the file name
     * @return a URL string for the full path to the provided file.
     */
    public String loadDemoFile(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(DEMO_DATA_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    /**
     * Returns a stream to the icon given the file name
     * 
     * @param fileName the file name
     * @return an input stream to the file
     */
    public InputStream loadIconStream(String fileName)
    {
        InputStream resource = ResourceLoader.class.getClassLoader().getResourceAsStream(ICONS_DIRECTORY + fileName);
        return resource;
    }
}
