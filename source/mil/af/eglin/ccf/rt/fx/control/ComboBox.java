package mil.af.eglin.ccf.rt.fx.control;

import com.sun.javafx.css.StyleManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableControl;
import mil.af.eglin.ccf.rt.fx.control.validation.ValidableHandler;
import mil.af.eglin.ccf.rt.util.ResourceLoader;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> implements RtComponent, ValidableControl<T>
{
    protected Accent accent = Accent.PRIMARY_MID;

    private BooleanProperty isValid = new SimpleBooleanProperty(true);
    private StringProperty errorText = new SimpleStringProperty();    
    private ValidableHandler<T> validationHandler = new ValidableHandler<>(this);
    
    private static final String USER_AGENT_STYLESHEET = "combo-box.css";
    private static final String CSS_CLASS = "rt-combo-box";

    public ComboBox()
    {
        super();
        initialize();
    }
    
    public ComboBox(Accent accent) 
    {
        super();
        this.accent = accent;
        initialize();
    }
    
    public ComboBox(ObservableList<T> items) 
    {
        super(items);
        initialize();
    }
    
    public ComboBox(ObservableList<T> items, Accent accent) 
    {
        super(items);
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() 
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control getControl()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate()
    {
        this.isValid.set(this.validationHandler.validate(getValue()));
        return isValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadOnlyBooleanProperty isValidProperty()
    {
        return this.isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid()
    {
        return this.isValid.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringProperty errorMessageProperty()
    {
        return this.errorText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String message)
    {
        this.errorText.set(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage()
    {
        return this.errorText.get();
    }

    @Override
    public ObservableValue<T> getObservable()
    {
        return valueProperty();
    }

    @Override
    public boolean setValid(boolean isValid)
    {
        return isValid;
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.accent.getCssName());
    }
    
    static
    {
        StyleManager.getInstance().addUserAgentStylesheet(ResourceLoader.loadComponent(USER_AGENT_STYLESHEET));
    }
}
