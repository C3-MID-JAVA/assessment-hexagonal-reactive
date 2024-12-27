package ec.com.sofka;

public class User {
    private String id;

    private String firstName;

    private String lastName;

    private String ci;

    private String email;

    private String password;

    private String status;

    public User(){

    }

    public User(String id, String firstName, String lastName, String ci, String email, String password, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ci = ci;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(String firstName, String lastName, String ci, String email, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ci = ci;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}