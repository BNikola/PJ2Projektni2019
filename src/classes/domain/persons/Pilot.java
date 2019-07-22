package classes.domain.persons;

public class Pilot extends Person {
    private String licence;

    public Pilot(String firstName, String lastName, String licence) {
        super(firstName, lastName);
        this.licence = licence;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Pilot{" +
                "licence='" + licence + ", " +
                super.toString() +
                '}';
    }
}
