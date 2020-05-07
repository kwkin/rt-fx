package mil.af.eglin.ccf.rt.util;

import java.io.InputStream;
import java.net.URL;

// TODO replace with rt-util and fix up paths
// the type of path separator appears to matter for resources in the JAR (i.e. / vs. \), so Path is not used
public class ResourceLoader
{
    private final static String DATA_DIRECTORY       = "data/";
    private final static String COMPONENTS_DIRECTORY = "data/components/";
    private final static String LAYOUTS_DIRECTORY    = "data/layouts/";
    private final static String ICONS_DIRECTORY      = "data/icons/";
    private final static String DEMO_DATA_DIRECTORY  = "data/";

    public static String loadFile(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(DATA_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    public static String loadComponent(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(COMPONENTS_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    public static String loadLayouts(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(LAYOUTS_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    public static String loadDemoFile(String fileName)
    {
        URL resource = ResourceLoader.class.getClassLoader().getResource(DEMO_DATA_DIRECTORY + fileName);
        return resource == null ? "" : resource.toString();
    }

    public static InputStream loadIconStream(String fileName)
    {
        InputStream resource = ResourceLoader.class.getClassLoader().getResourceAsStream(ICONS_DIRECTORY + fileName);
        return resource;
    }
}
