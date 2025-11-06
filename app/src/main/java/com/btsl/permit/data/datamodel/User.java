package com.btsl.permit.data.datamodel;

import com.google.gson.annotations.SerializedName;

public class User {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  // Constructors
  public User() {
  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
