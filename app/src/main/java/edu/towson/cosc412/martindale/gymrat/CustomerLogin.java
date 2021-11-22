package edu.towson.cosc412.martindale.gymrat;

public class CustomerLogin {

    private String userName;
    private String password;


    public CustomerLogin(String userName, String password) {
        this.userName = userName;
        this.password = password;

    }
    public String toString(){
        return ( "userName= " + userName + ", password= " + password );

    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
