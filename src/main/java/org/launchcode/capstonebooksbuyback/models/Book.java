package org.launchcode.capstonebooksbuyback.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Book {
    @NotNull
    @Size(min=3,max=15)
    private String name;

    @NotNull
    @Size(min=1,message="Author name cannot be empty")
    private String author;

    @Id
    @GeneratedValue
    private int id;
    public int getId() {
        return id;
    }

    @ManyToOne
    private User user;

    public Book(){ }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }


    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

