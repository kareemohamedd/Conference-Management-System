package kareem;

public class Person extends User {

    public Person(String name, String email) {
        super(name, email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
