package com.entity;

/**
 * @author mengtianluofan
 * @version 1.0
 * @description: TODO
 * @date 2023/6/6 08:35
 */

public class Person {
    private String name;
    private String personID;
    private int gender;
    private long tel;

    public Person() {
    }

    public Person(String name, String personID, int gender, long tel) {
        this.name = name;
        this.personID = personID;
        this.gender = gender;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }


}
