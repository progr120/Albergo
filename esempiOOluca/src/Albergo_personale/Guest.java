package Albergo_personale;
public class Guest {
    /**
     * classe ospite albergo
     */
    private String firstName;
    private String lastName;

    public Guest(String firstName, String lastName, String idNumber, String docType, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Guest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
