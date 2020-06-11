package mil.af.eglin.ccf.rt.fx.layout;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.Dialog;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class DialogPane extends javafx.scene.control.DialogPane
{
    protected Accent accent = Accent.BASE;

    private static final String USER_AGENT_STYLESHEET = "dialog-pane.css";
    private static final String CSS_CLASS = "rt-dialog-pane";

    private ButtonBar buttonBar;
    private Dialog<?> dialog;

    public DialogPane(Dialog<?> dialog)
    {
        super();
        this.dialog = dialog;
        initialize();
    }

    public DialogPane(Dialog<?> dialog, Accent accent)
    {
        super();
        this.dialog = dialog;
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}`
     */
    @Override
    public String getUserAgentStylesheet()
    {
        return null;
    }

    // TODO expandable handler
    @Override
    protected Node createButtonBar()
    {
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getStyleClass().add("button-box");
        buttonBar.setMaxWidth(Double.MAX_VALUE);

        updateButtons();
        getButtonTypes().addListener((ListChangeListener<? super ButtonType>) c -> updateButtons());

        this.buttonBar = buttonBar;
        return buttonBar;
    }

    @Override
    protected Node createButton(ButtonType buttonType)
    {
        final Button button = new Button(buttonType.getText(), ButtonStyle.FLAT);
        final ButtonData buttonData = buttonType.getButtonData();
        ButtonBar.setButtonData(button, buttonData);
        button.setDefaultButton(buttonType != null && buttonData.isDefaultButton());
        button.setCancelButton(buttonType != null && buttonData.isCancelButton());
        button.addEventHandler(ActionEvent.ACTION, ae -> {
            if (ae.isConsumed()) return;
            if (dialog != null) {
                dialog.resultAndClose(buttonType, true);
            }
        });

        ButtonBar.setButtonUniformSize(button, false);
        return button;
    }

    private void updateButtons()
    {
        List<Button> buttons = new ArrayList<>();
        for (ButtonType buttonType : getButtonTypes())
        {
            // TODO this will create duplicate buttons
            Node lookup = lookupButton(buttonType);
            Node button = lookup == null ? createButton(buttonType) : lookup;
            buttonBar.getButtons().add(button);
            if (button instanceof Button)
            {
                buttons.add((Button) button);
            }
        }
    }

    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }

    /**
     * Loads the user agent stylesheet specific to this layout
     */
    public static void loadStyleSheet()
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
    
    static
    {
        DialogPane.loadStyleSheet();
    }
}
