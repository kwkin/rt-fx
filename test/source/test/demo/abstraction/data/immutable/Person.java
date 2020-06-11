package test.demo.abstraction.data.immutable;

import java.util.UUID;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person
{
    private StringProperty id = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();
    private DoubleProperty coolFactor = new SimpleDoubleProperty();
    private ObjectProperty<Service> service = new SimpleObjectProperty<Service>();
    private BooleanProperty isSubscribed = new SimpleBooleanProperty();
    
    public Person(String firstName, String lastName, String email, int age, double coolFactor, Service service, boolean isSubscribed)
    {
        this.id.setValue(UUID.randomUUID().toString());
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.email.setValue(email);
        this.age.setValue(age);
        this.coolFactor.setValue(coolFactor);
        this.service.setValue(service);
        this.isSubscribed.setValue(isSubscribed);
    }
    
    public StringProperty getIdProperty()
    {
        return this.id;
    }
    
    public String getId()
    {
        return id.getValue();
    }
    
    public StringProperty getFirstNameProperty()
    {
        return this.firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName.setValue(firstName);
    }
    
    public String getFirstName()
    {
        return firstName.getValue();
    }
    
    public StringProperty getLastNameProperty()
    {
        return this.lastName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName.setValue(lastName);
    }
    
    public String getLastName()
    {
        return lastName.getValue();
    }
    
    public StringProperty getEmailProperty()
    {
        return this.email;
    }
    
    public void setEmail(String email)
    {
        this.email.setValue(email);
    }
    
    public String getEmail()
    {
        return email.getValue();
    }
    
    public IntegerProperty getAgeProperty()
    {
        return this.age;
    }
    
    public void setAge(int age)
    {
        this.age.setValue(age);
    }
    
    public int getAge()
    {
        return age.getValue();
    }
    
    public DoubleProperty getCoolFactorProperty()
    {
        return this.coolFactor;
    }
    
    public void setCoolFactor(double coolFactor)
    {
        this.coolFactor.setValue(coolFactor);
    }
    
    public double getCoolFactor()
    {
        return coolFactor.getValue();
    }
    
    public ObjectProperty<Service> getServiceProperty()
    {
        return this.service;
    }
    
    public void setService(Service service)
    {
        this.service.setValue(service);
    }
    
    public Service getService()
    {
        return this.service.getValue();
    }
    
    public BooleanProperty getIsSubscribedProperty()
    {
        return this.isSubscribed;
    }
    
    public void setIsSubscribed(boolean isSubscribed)
    {
        this.isSubscribed.setValue(isSubscribed);
    }
    
    public boolean getIsSubscribed()
    {
        return isSubscribed.getValue();
    }
}
