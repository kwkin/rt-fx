package test.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import test.demo.abstraction.SampleSession;
import test.demo.abstraction.Settings;
import test.demo.abstraction.data.immutable.Person;
import test.demo.abstraction.data.immutable.Service;
import test.demo.presentation.data.TablePerson;
import test.demo.presentation.model.BoxesPaneModel;
import test.demo.presentation.model.ColorPickerPaneModel;
import test.demo.presentation.model.ComboBoxPaneModel;
import test.demo.presentation.model.ContextMenuPaneModel;
import test.demo.presentation.model.LabelPaneModel;
import test.demo.presentation.model.ListViewPaneModel;
import test.demo.presentation.model.NormalButtonPaneModel;
import test.demo.presentation.model.PaneModel;
import test.demo.presentation.model.ProgressPaneModel;
import test.demo.presentation.model.SliderPaneModel;
import test.demo.presentation.model.SpecialButtonPaneModel;
import test.demo.presentation.model.StepperPaneModel;
import test.demo.presentation.model.TabPaneModel;
import test.demo.presentation.model.TableViewModel;
import test.demo.presentation.model.TextAreaPaneModel;
import test.demo.presentation.model.TextFieldPaneModel;
import test.demo.presentation.model.TitledPaneModel;

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
        
        this.tableViewModel = new TableViewModel();

        session.getPeople().addListener(new ListChangeListener<Person>() 
        {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Person> c)
            {
                while(c.next())
                {
                    if (c.wasPermutated())
                    {
                        int from = c.getFrom();
                        int to = c.getTo();
                        List<Integer> permutations = new ArrayList<>();
                        for (int oldIndex = from; oldIndex < to; oldIndex++)
                        {
                            permutations.add(c.getPermutation(oldIndex));
                        }
                        tableViewModel.setPeopleSublsit(from, to, permutations);
                    }
                    else if (c.wasUpdated())
                    {
                        for (int index = c.getFrom(); index < c.getTo(); ++ index)
                        {
                            tableViewModel.updatePerson(session.getPeople().get(index));
                        }
                    }
                    else
                    {
                        for(Person removedPerson : c.getRemoved())
                        {
                            tableViewModel.removePerson(removedPerson);
                        }
                        for(Person person : c.getAddedSubList())
                        {
                            tableViewModel.addPerson(person);
                        }
                    }
                }
            }
        });
        for(Person person : session.getPeople())
        {
            tableViewModel.addPerson(person);
        }
        session.getPeople().get(0).setAge(123);
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
}
