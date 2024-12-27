package ec.com.sofka.data;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String ci;
    private String email;
    private String status;

    public UserResponseDTO(String firstName, String lastName, String ci, String email, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ci = ci;
        this.email = email;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}