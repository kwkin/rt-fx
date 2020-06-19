package test.demo.presentation.model;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import test.demo.abstraction.data.immutable.Person;
import test.demo.abstraction.data.immutable.Service;
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
    
    public void addPerson(Person person)
    {
        Platform.runLater(() -> 
        {
            TablePerson newPerson = new TablePerson(
                    person.getId(), 
                    person.getFirstName(), 
                    person.getLastName(), 
                    person.getEmail(), 
                    person.getAge(), 
                    person.getCoolFactor(), 
                    person.getService(), 
                    person.getIsSubscribed());
            people.add(newPerson);
        }); 
    }
    
    public void removePerson(Person person)
    {
        Platform.runLater(() -> 
        {
            people.remove(getPersonFromId(person.getId()));
        }); 
    }
    
    public void updatePerson(Person person)
    {
        if (!this.people.isEmpty())
        {
            TablePerson tablePerson = getPersonFromId(person.getId());
            tablePerson.setFirstName(person.getFirstName());
            tablePerson.setLastName(person.getLastName());
            tablePerson.setEmail(person.getEmail());
            tablePerson.setAge(person.getAge());
            tablePerson.setCoolFactor(person.getCoolFactor());
            tablePerson.setService(person.getService());
            tablePerson.setIsSubscribed(person.getIsSubscribed());
        }
    }
    
    public void replacePerson(int index, Person person)
    {
        Platform.runLater(() -> 
        {
            TablePerson newPerson = new TablePerson(
                    person.getId(), 
                    person.getFirstName(), 
                    person.getLastName(), 
                    person.getEmail(), 
                    person.getAge(), 
                    person.getCoolFactor(), 
                    person.getService(), 
                    person.getIsSubscribed());
            people.set(index, newPerson);
        }); 
    }
    
    public ObservableList<TablePerson> getPeople()
    {
        return people;
    }
    
    public void setPeopleSublsit(int from, int to, List<Integer> permutations)
    {
        List<TablePerson> copy = new ArrayList<>(people.subList(from, to));
        int permIndex = 0;
        for (int oldIndex = from; oldIndex < to; oldIndex++)
        {
            int newIndex = permutations.get(permIndex++);
            copy.set(newIndex - from, people.get(oldIndex));
        }
        people.subList(from, to).clear();
        people.addAll(from, copy);
    }
    
    private TablePerson getPersonFromId(String id)
    {
        return people.stream().filter(person -> person.getId().equals(id)).findFirst().get();
    }
}
