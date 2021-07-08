package com.example.instagram;

import android.app.Application;

import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Registers the post model to our app
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jOePW501qKwxoyyWt0XZKDd44C9kLiAokSjNmxGz")
                .clientKey("0HZvy1ToEcHhMzruY7GuEFkNfCYOTct1fD4HE2co")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
