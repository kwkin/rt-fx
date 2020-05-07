package test.demo.abstraction.immutable;

public enum Service
{
    EDUCATION("Education"),
    RETAIL("Retail"),
    MEDICAL("Medical"),
    TECHNOLOGY("Technology"),
    SAFETY("Safety"),
    OTHER("Other");
    
    private String name;
    
    Service(String name)
    {
        this.name = name;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
}
