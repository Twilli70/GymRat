package edu.towson.cosc412.martindale.gymrat.fragments;



public class CustomerAccount {

    private String userName;
    private String fName;
    private String lName;
    private int id;
    private int age;
    private int height;
    private int weight;


    public CustomerAccount(String userName, int id, int age, String fName, String lName, int height, int weight) {
        this.userName = userName;
        this.id = id;
        this.age = age;
        this.fName = fName;
        this.lName = lName;
        this.height = height;
        this.weight = weight;
    }

    public String toString(){
        return ("id= " + id + ", userName=" + userName + ", fName= " + fName + ", lName= " + lName + ", age = " + age + ", height= " + height + ", weight= " + weight);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

