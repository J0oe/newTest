package com.ex.admin.databclass;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Person {


    public String password;
    public String name;
    public String nickname;


    public Map<String, Boolean> stars = new HashMap<>();

    public Person() {
    }

    public Person(String password, String name, String nickname) {

        this.password = password;

        this.name = name;
        this.nickname = nickname;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();


        result.put("password", password);
        result.put("nickname", nickname);

        return result;
    }

}