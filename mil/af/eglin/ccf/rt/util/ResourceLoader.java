package mil.af.eglin.ccf.rt.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO change to paths instead of strings
// TODO replace with rt-util and fix up paths
public class ResourceLoader
{
    private final static String DATA_DIRECTORY = "../../../../../../data";
    private final static String COMPONENTS_DIRECTORY = "../../../../../../data/components";
    private final static String LAYOUTS_DIRECTORY = "../../../../../../data/layouts";
    private final static String ICONS_DIRECTORY = "../../../../../../data/icons";
    private final static String DEMO_DATA_DIRECTORY = "../../../../../../test/data";
    
    // TODO make this into a proper gradle project or somethin' so I don't have to do this
    private final static String DATA_DIRECTORY_JFX = "../../../../../../../../JFoenix-master/src/data";
    private final static String DATA_DIRECTORY_JFX_DEMO = "../../../../../../../../JFoenix-master/demo/build/resources/main/css";

    public static String loadFile(String fileName)
    {
        Path path = Paths.get(DATA_DIRECTORY, fileName);
        URL resource = ResourceLoader.class.getResource(path.toString());
        String relativePath = resource == null ? "" : resource.toString();
        return relativePath;
    }
    
    public static String loadComponent(String fileName)
    {
        Path path = Paths.get(COMPONENTS_DIRECTORY, fileName);
        URL resource = ResourceLoader.class.getResource(path.toString());
        String relativePath = resource == null ? "" : resource.toString();
        return relativePath;
    }
    
    public static String loadLayouts(String fileName)
    {
        Path path = Paths.get(LAYOUTS_DIRECTORY, fileName);
        URL resource = ResourceLoader.class.getResource(path.toString());
        String relativePath = resource == null ? "" : resource.toString();
        return relativePath;
    }

    public static String loadDemoFile(String fileName)
    {
        Path path = Paths.get(DEMO_DATA_DIRECTORY, fileName);
        URL resource = ResourceLoader.class.getResource(path.toString());
        String relativePath = resource == null ? "" : resource.toString();
        return relativePath;
    }

    public static String loadIcon(String fileName)
    {
        Path path = Paths.get(ICONS_DIRECTORY, fileName);
        URL resource = ResourceLoader.class.getResource(path.toString());
        String relativePath = resource == null ? "" : resource.toString();
        return relativePath;
    }

    public static Path loadIconPath(String fileName)
    {
        Path path = Paths.get("data", "icons", fileName);
        return path;
    }

    public static String loadJFXCss(String fileName)
    {
        Path test = Paths.get("test", "data", fileName);
        String urlPath = String.format("file:\\\\\\%s", test.toAbsolutePath());
        String relativePath = urlPath == null ? "" : urlPath.toString();
        return relativePath.replace("\\", "/");
    }
}
