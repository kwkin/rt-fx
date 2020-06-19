package mil.af.eglin.ccf.rt.fx.control.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.control.Labeled;
import javafx.scene.control.Skin;
import mil.af.eglin.ccf.rt.fx.control.RtDescriptionControl;
import mil.af.eglin.ccf.rt.fx.control.skins.RtDescriptionContainerSkin;

public class DescriptionContainer<T extends ValidableControl<?> & RtDescriptionControl> extends Labeled
{
    private T control;

    public DescriptionContainer(T control)
    {
        this.control = control;

        textProperty().bind(control.helperTextProperty());

        getStyleClass().add("description-container");

    }

    public ReadOnlyBooleanProperty isValidProperty()
    {
        return this.control.isValidProperty();
    }

    public ReadOnlyBooleanProperty isShowHelperTextProperty()
    {
        return this.control.isShowHelperTextProperty();
    }

    public boolean getIsShowHelperText()
    {
        return this.control.getIsShowHelperText();
    }

    public ReadOnlyStringProperty errorMessageProperty()
    {
        return this.control.errorMessageProperty();
    }

    public ReadOnlyStringProperty helperTextProperty()
    {
        return this.control.helperTextProperty();
    }

    public String getHelperText()
    {
        return this.control.getHelperText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin()
    {
        return new RtDescriptionContainerSkin<>(this);
    }
}
