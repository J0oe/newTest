package com.ex.admin.databclass;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 14.12.2017.
 */

@IgnoreExtraProperties
public class Post {


    public String post;
    public String nickname;
    public Integer idPost;


    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
    }

    public Post(String nickname, String post, Integer id) {

        this.post = post;
        this.nickname = nickname;
        this.idPost = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();


        result.put("post", post);

        return result;
    }
}
