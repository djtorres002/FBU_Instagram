package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jOePW501qKwxoyyWt0XZKDd44C9kLiAokSjNmxGz")
                .clientKey("0HZvy1ToEcHhMzruY7GuEFkNfCYOTct1fD4HE2co")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
