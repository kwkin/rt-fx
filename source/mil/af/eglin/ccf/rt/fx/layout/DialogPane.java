package mil.af.eglin.ccf.rt.fx.layout;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import mil.af.eglin.ccf.rt.fx.control.Button;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.ButtonStyle;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class DialogPane extends javafx.scene.control.DialogPane
{
    protected Accent accent;

    private static final String USER_AGENT_STYLESHEET = "dialog-pane.css";
    private static final String CSS_CLASS = "rt-dialog-pane";

    private ButtonBar buttonBar;

    public DialogPane()
    {
        super();
        initialize();
    }

    public DialogPane(Accent accent)
    {
        super();
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
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadLayouts(USER_AGENT_STYLESHEET));
    }
}
