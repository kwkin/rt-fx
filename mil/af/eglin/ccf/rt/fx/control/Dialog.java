package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.stage.StageStyle;
import mil.af.eglin.ccf.rt.fx.layout.DialogPane;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

// TODO rt-fx alert dialog
// TODO style the header area
// TODO style the context string area
public class Dialog<R> extends javafx.scene.control.Dialog<R>
{
    private static final String USER_AGENT_STYLESHEET = "dialog.css";
    
    public Dialog()
    {
        setDialogPane(new DialogPane());
        initialize();
    }
    
    private void initialize()
    {
        
        String cssContextMenu = ResourceLoader.loadComponent(USER_AGENT_STYLESHEET);
        StyleManager.getInstance().addUserAgentStylesheet(cssContextMenu);
        
        initStyle(StageStyle.UNIFIED);
    }
}
