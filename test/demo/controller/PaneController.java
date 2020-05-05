package test.sample.controller;

import javafx.collections.ListChangeListener;
import test.sample.abstraction.SampleSession;
import test.sample.abstraction.Settings;
import test.sample.abstraction.data.Person;
import test.sample.abstraction.immutable.Service;
import test.sample.presentation.data.TablePerson;
import test.sample.presentation.model.BoxesPaneModel;
import test.sample.presentation.model.ColorPickerPaneModel;
import test.sample.presentation.model.ComboBoxPaneModel;
import test.sample.presentation.model.ContextMenuPaneModel;
import test.sample.presentation.model.TitledPaneModel;
import test.sample.presentation.model.LabelPaneModel;
import test.sample.presentation.model.ListViewPaneModel;
import test.sample.presentation.model.NormalButtonPaneModel;
import test.sample.presentation.model.PaneModel;
import test.sample.presentation.model.ProgressPaneModel;
import test.sample.presentation.model.SliderPaneModel;
import test.sample.presentation.model.SpecialButtonPaneModel;
import test.sample.presentation.model.StepperPaneModel;
import test.sample.presentation.model.TabPaneModel;
import test.sample.presentation.model.TableViewModel;
import test.sample.presentation.model.TextAreaPaneModel;
import test.sample.presentation.model.TextFieldPaneModel;

public class PaneController
{
    private SampleSession session;
    
    private PaneModel paneModel = new PaneModel();
    private LabelPaneModel labelPaneModel = new LabelPaneModel();
    private NormalButtonPaneModel normalButtonPaneModel = new NormalButtonPaneModel();
    private SpecialButtonPaneModel specialButtonPaneModel = new SpecialButtonPaneModel();
    private BoxesPaneModel boxesPaneModel = new BoxesPaneModel();
    private SliderPaneModel sliderPaneModel = new SliderPaneModel();
    private TextFieldPaneModel textBoxPaneModel = new TextFieldPaneModel();
    private ComboBoxPaneModel comboBoxPaneModel = new ComboBoxPaneModel();
    private TextAreaPaneModel textAreaPaneModel = new TextAreaPaneModel();
    private StepperPaneModel stepperPaneModel = new StepperPaneModel();
    private ColorPickerPaneModel colorPickerPaneModel = new ColorPickerPaneModel();
    private ProgressPaneModel progressPaneModel = new ProgressPaneModel();
    private TitledPaneModel expanderPaneModel = new TitledPaneModel();
    private TabPaneModel tabPaneModel = new TabPaneModel();
    private ListViewPaneModel listViewPaneModel = new ListViewPaneModel();
    private ContextMenuPaneModel contextMenuPaneModel = new ContextMenuPaneModel();
    private TableViewModel tableViewModel;
    
    public PaneController(SampleSession session, Settings settings)
    {
        this.session = session;
        
        tableViewModel = new TableViewModel();

        for(Person addedPerson : session.getPeople())
        {
            TablePerson tablePerson = addNewTablePerson(addedPerson);
            tableViewModel.addPerson(tablePerson);
        }
        session.getPeople().addListener(new ListChangeListener<Person>() 
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Person> c)
            {
                // TODO look at update function and include replace, etc...
                if (c.next() && c.wasAdded())
                {
                    for(Person addedPerson : c.getAddedSubList())
                    {
                        TablePerson tablePerson = addNewTablePerson(addedPerson);
                        tableViewModel.addPerson(tablePerson);
                    }
                }
                else if (c.wasRemoved())
                {
                    for(Person removedPerson : c.getRemoved())
                    {
                        tableViewModel.removePerson(removedPerson.getId());
                    }
                }
            }
        });
    }
    
    public PaneModel getPaneModel()
    {
        return this.paneModel;
    }
    
    public LabelPaneModel getLabelPaneModel()
    {
        return this.labelPaneModel;
    }
    
    public NormalButtonPaneModel getNormalButtonPaneModel()
    {
        return this.normalButtonPaneModel;
    }
    
    public SpecialButtonPaneModel getSpecialButtonPaneModel()
    {
        return this.specialButtonPaneModel;
    }
    
    public BoxesPaneModel getBoxesPaneModel()
    {
        return this.boxesPaneModel;
    }
    
    public SliderPaneModel getSliderPaneModel()
    {
        return this.sliderPaneModel;
    }
    
    public TextFieldPaneModel getTextBoxPaneModel()
    {
        return this.textBoxPaneModel;
    }
    
    public ComboBoxPaneModel getComboBoxPaneModel()
    {
        return this.comboBoxPaneModel;
    }
    
    public TextAreaPaneModel getTextAreaPaneModel()
    {
        return this.textAreaPaneModel;
    }
    
    public StepperPaneModel getStepperPaneModel()
    {
        return this.stepperPaneModel;
    }
    
    public ColorPickerPaneModel getColorPickerPaneModel()
    {
        return this.colorPickerPaneModel;
    }
    
    public ProgressPaneModel getProgressPaneModel()
    {
        return this.progressPaneModel;
    }
    
    public TitledPaneModel getExpanderPaneModel()
    {
        return this.expanderPaneModel;
    }
    
    public TabPaneModel getTabPaneModel()
    {
        return this.tabPaneModel;
    }
    
    public ListViewPaneModel getListViewPaneModel()
    {
        return this.listViewPaneModel;
    }
    
    public ContextMenuPaneModel getContextMenuPaneModel()
    {
        return this.contextMenuPaneModel;
    }
    
    public TableViewModel getTableViewModel()
    {
        return this.tableViewModel;
    }
    
    public void updatePersonFirstName(String id, String firstName)
    {
        this.session.getPersonFromId(id).setFirstName(firstName);
    }
    
    public void updatePersonLastName(String id, String lastName)
    {
        this.session.getPersonFromId(id).setLastName(lastName);
    }
    
    public void updatePersonEmail(String id, String email)
    {
        this.session.getPersonFromId(id).setEmail(email);
    }
    
    public void updatePersonAge(String id, int age)
    {
        this.session.getPersonFromId(id).setAge(age);
    }

    public void updatePersonCoolFactor(String id, double coolFactor)
    {
        this.session.getPersonFromId(id).setCoolFactor(coolFactor);
    }
    
    public void updatePersonGender(String id, Service service)
    {
        this.session.getPersonFromId(id).setService(service);
    }
    
    public void updatePersonIsSubscribed(String id, boolean isSubscribed)
    {
        this.session.getPersonFromId(id).setIsSubscribed(isSubscribed);
    }
    
    public Person getPerson(String id)
    {
        return this.session.getPersonFromId(id);
    }
    
    public TablePerson addNewTablePerson(Person person)
    {
        // @formatter:off
        TablePerson tablePerson = new TablePerson(
                person.getId(), 
                person.getFirstName(), 
                person.getLastName(), 
                person.getEmail(), 
                person.getAge(), 
                person.getCoolFactor(), 
                person.getService(), 
                person.getIsSubscribed());
        // @formatter:on
        
        person.getFirstNameProperty().addListener((ov, oldVal, newVal) -> tablePerson.setFirstName(newVal)); 
        person.getLastNameProperty().addListener((ov, oldVal, newVal) -> tablePerson.setLastName(newVal)); 
        person.getEmailProperty().addListener((ov, oldVal, newVal) -> tablePerson.setEmail(newVal));  
        person.getAgeProperty().addListener((ov, oldVal, newVal) -> tablePerson.setAge(newVal.intValue())); 
        person.getCoolFactorProperty().addListener((ov, oldVal, newVal) -> tablePerson.setCoolFactor(newVal.doubleValue())); 
        person.getServiceProperty().addListener((ov, oldVal, newVal) -> tablePerson.setService(newVal)); 
        person.getIsSubscribedProperty().addListener((ov, oldVal, newVal) -> tablePerson.setIsSubscribed(newVal)); 
        return tablePerson;
        
    }
}
