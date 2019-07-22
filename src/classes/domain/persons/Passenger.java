package classes.domain.persons;

public class Passenger extends Person {
    private String passportNumber;

    public Passenger(String firstName, String lastName, String passportNumber) {
        super(firstName, lastName);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passportNumber=" + passportNumber + ", " +
                super.toString() +
                '}';
    }
}
