package com.ex.admin.databclass;

/**
 * Created by Admin on 09.01.2018.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class AddNewHero {

    public String nickname;


    public Map<String, Boolean> stars = new HashMap<>();

    public AddNewHero() {
    }

    public AddNewHero(String nickname) {

        this.nickname = nickname;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();


        result.put("nickname", nickname);

        return result;
    }

}