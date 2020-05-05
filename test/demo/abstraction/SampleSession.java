package test.demo.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import test.demo.abstraction.data.Person;
import test.demo.abstraction.immutable.Service;

public class SampleSession
{
    private ObservableList<Person> people = FXCollections.observableArrayList();
    
    public SampleSession()
    {
        for (int index = 0; index < 10; index++)
        {
            this.people.add(generateRandomPerson());
        }
    }
    
    public ObservableList<Person> getPeople()
    {
        return people;
    }
    
    public Person getPersonFromId(String id)
    {
        return people.stream().filter(person -> person.getId().equals(id)).findFirst().get();
    }
    
    public void shutdown()
    {
        
    }
    
    private Person generateRandomPerson()
    {
        String nameLetters = "abcdefghijklmnopqrstuvwxyz";
        String addressLetters = "abcdefghijklmnopqrstuvwxyz.";
        List<String> domains = new ArrayList<>();
        domains.add("com");
        domains.add("net");
        domains.add("org");
        domains.add("gov");
        Random random = new Random();
        
        int firstNameLength = random.nextInt(12) + 2;
        StringBuilder firstNameBuilder = new StringBuilder();
        while (firstNameBuilder.length() < firstNameLength) 
        {
            int index = (int) (random.nextFloat() * nameLetters.length());
            firstNameBuilder.append(nameLetters.charAt(index));
        }
        String first = firstNameBuilder.toString();

        int lastNameLength = random.nextInt(12) + 2;
        StringBuilder lastNameBuilder = new StringBuilder();
        while (lastNameBuilder.length() < lastNameLength) 
        {
            int index = (int) (random.nextFloat() * nameLetters.length());
            lastNameBuilder.append(nameLetters.charAt(index));
        }
        String last = lastNameBuilder.toString();

        int addressLength = random.nextInt(18) + 2;
        StringBuilder addressBuilder = new StringBuilder();
        while (addressBuilder.length() < addressLength) 
        {
            int index = (int) (random.nextFloat() * addressLetters.length());
            addressBuilder.append(addressLetters.charAt(index));
        }
        String addressName = addressBuilder.toString();

        int domainLength = random.nextInt(3) + 2;
        StringBuilder domainBuilder = new StringBuilder();
        while (domainBuilder.length() < domainLength) 
        {
            int index = (int) (random.nextFloat() * nameLetters.length());
            domainBuilder.append(nameLetters.charAt(index));
        }
        String domainName = domainBuilder.toString();

        int domainIndex = random.nextInt(domains.size());
        String domain = domains.get(domainIndex);
        
        String email = String.format("%s@%s.%s", addressName, domainName, domain);
        
        int age = random.nextInt(82) + 18;
        
        double coolFactor = random.nextDouble() * 100;

        int serviceIndex = random.nextInt(Service.values().length);
        Service service = Service.values()[serviceIndex];
        
        boolean isSubscribed = random.nextBoolean();
        
        return new Person(first, last, email, age, coolFactor, service, isSubscribed);
    }
}
