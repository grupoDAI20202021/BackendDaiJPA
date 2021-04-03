package daibackend.demo.model.custom;

import javax.validation.constraints.Email;

public class updateEmail {

    @Email(message = "Insert a valid email")
    private String email;

    public updateEmail() {
    }

    public updateEmail(@Email(message = "Insert a valid email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
