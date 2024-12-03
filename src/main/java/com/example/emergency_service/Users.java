package com.example.emergency_service;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Users {
    private IntegerProperty id;
    private StringProperty username;
    private StringProperty password;

    public Users() {
        this.id = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty userProperty() {
        return username;
    }

    public StringProperty passProperty() {
        return password;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getU_name() {
        return username.get();
    }

    public void setU_name(String username) {
        this.username.set(username);
    }

    public String getU_pass() {
        return password.get();
    }

    public void setU_pass(String password) {
        this.password.set(password);
    }
}
