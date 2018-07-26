package org.launchcode.capstonebooksbuyback.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {

    @NotNull
    @Size(min=3,max=15)
    @Pattern(regexp = "[A-Za-z]*",message="username can only contain letters")
    private String username;

    @NotNull
    @Size(min=7,message="Please provide valid email")
    private String email;

    @NotNull
    @Size(min=3,message="Password > 3 and verify passwords do not match")
    private String password;

    @Id
    @GeneratedValue
    private int id;
    public int getId() {
        return id;
    }

    public User(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public User(){ }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
