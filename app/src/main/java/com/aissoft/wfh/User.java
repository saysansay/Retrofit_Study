package com.aissoft.wfh;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class User {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ",password='" + password + '\'' +
                ", name=" + name +
                ", id=" + id +
                '}';
    }
}