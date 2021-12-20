package edu.towson.cosc412.martindale.gymrat.database.entities;

import java.time.LocalDate;

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public float height;
    private LocalDate birthdayDate;

    public LocalDate getBirthdayDate(){
        return birthdayDate;
    }

    public void setbirthday(int year, int month, int day){
        birthdayDate = LocalDate.of(year, month, day);
    }
}
