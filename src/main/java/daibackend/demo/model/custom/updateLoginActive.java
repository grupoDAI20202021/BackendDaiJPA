package daibackend.demo.model.custom;

import javax.validation.constraints.Email;

public class updateLoginActive {
    @Email(message = "Insert a valid email")
    private String email;

    private int generatedCode;

    public updateLoginActive() {
    }

    public updateLoginActive(@Email(message = "Insert a valid email") String email, int generatedCode) {
        this.email = email;
        this.generatedCode = generatedCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(int generatedCode) {
        this.generatedCode = generatedCode;
    }
}
