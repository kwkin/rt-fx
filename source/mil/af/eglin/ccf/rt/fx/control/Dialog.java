package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import mil.af.eglin.ccf.rt.fx.layout.DialogPane;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class Dialog<R> extends javafx.scene.control.Dialog<R>
{
    private static final String USER_AGENT_STYLESHEET = "dialog.css";

    public Dialog()
    {
        setDialogPane(new DialogPane(this));
        initialize();
    }

    @SuppressWarnings("unchecked")
    public void resultAndClose(ButtonType cmd, boolean close)
    {
        Callback<ButtonType, R> resultConverter = getResultConverter();

        R priorResultValue = getResult();
        R newResultValue = null;

        if (resultConverter == null)
        {
            newResultValue = (R) cmd;
        }
        else
        {
            newResultValue = resultConverter.call(cmd);
        }
        setResult(newResultValue);
        if (close && priorResultValue == newResultValue)
        {
            close();
        }
    }

    private void initialize()
    {
        initStyle(StageStyle.UNIFIED);
    }

    /**
     * Loads the user agent stylesheet specific to this component
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }

    static
    {
        Dialog.loadStyleSheet();
    }
}
