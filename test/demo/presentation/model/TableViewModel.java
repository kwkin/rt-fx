package test.demo.presentation.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import test.demo.abstraction.immutable.Service;
import test.demo.presentation.data.TablePerson;

public class TableViewModel
{
    public ObservableList<TablePerson> people = FXCollections.observableArrayList();
    
    public TableViewModel()
    { 
        this.people.addAll(people);
    }
    
    public void updatePersonFirstName(String id, String firstName)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setFirstName(firstName);;
        }); 
    }
    
    public void updatePersonLastName(String id, String lastName)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setLastName(lastName);;
        }); 
    }
    
    public void updatePersonEmail(String id, String email)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setEmail(email);;
        }); 
    }
    
    public void updatePersonAge(String id, int age)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setAge(age);
        }); 
    }
    
    public void updatePersonCoolFactor(String id, double coolFactor)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setCoolFactor(coolFactor);
        }); 
    }
    
    public void updatePersonService(String id, Service service)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setService(service);
        }); 
    }
    
    public void updatePersonIsSubscribed(String id, boolean isSubscribed)
    {
        Platform.runLater(() -> 
        {
            getPersonFromId(id).setIsSubscribed(isSubscribed);
        }); 
    }
    
    public void addPerson(TablePerson person)
    {
        Platform.runLater(() -> 
        {
            people.add(person);
        }); 
    }
    
    public void removePerson(String id)
    {
        Platform.runLater(() -> 
        {
            people.remove(getPersonFromId(id));
        }); 
    }
    
    public ObservableList<TablePerson> getPeople()
    {
        return people;
    }
    
    private TablePerson getPersonFromId(String id)
    {
        return people.stream().filter(person -> person.getId().equals(id)).findFirst().get();
    }
}
