package org.o7planning.markat;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
    public static ArrayList<User> userArrayList = new ArrayList<>();
    private int id;
    private String user_name;
    private String password;

    public User(int id, String user_name, String password) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
    }
    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
