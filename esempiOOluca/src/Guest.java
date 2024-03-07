public class Guest {
    /**
     * classe ospite albergo
     */
    private String firstName;
    private String lastName;
    private String idNumber;
    private String docType;
    private String email;
    private String phone;

    public Guest(String firstName, String lastName, String idNumber, String docType, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.docType = docType;
        this.email = email;
        this.phone = phone;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
