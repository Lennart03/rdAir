package com.realdolmen.rdAir.controllers;

import com.realdolmen.rdAir.domain.User;
import com.realdolmen.rdAir.repositories.UserRepository;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    private User user;

    @Inject
    private UserRepository ur;

    private boolean loggedIn = false;

    @NotEmpty(message="Your email must be filled in!")
    @Email(message="Your email should be like 'example@example.com'!")
    private String email;
    @Size(min=8, max=30, message="Your password should have at least 8 tokens(combination of basic letters and digits)!")
    @NotNull(message="Your password should have at least 8 tokens(combination of basic letters and digits)!")
    @Pattern(regexp = "[a-zA-Z0-9]{8,30}")
    private String password;

    public String doLogin() {
        if (user == null) {
            user = ur.getUserByEmail(email);
        }
        if(user != null) System.out.println("comparing " + password +" with " + user.getPasswordHash());
        else System.out.println("Could not find user!");
        if(user != null && BCrypt.checkpw(password, user.getPasswordHash())){//if user is found and pass is correct, user is logged in
            loggedIn = true;
            password = ""; //get rid of pass in mem;
            System.out.println("Logged in user "+ user.getFirstName());
            return "pretty:view-index";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "{Messages.invalid.login}", "{Message.invalid.login}"));
        return "";
    }

    public String doLogout(){
        loggedIn = false;
        return "pretty:view-home"; //todo redirect to home
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }


}
