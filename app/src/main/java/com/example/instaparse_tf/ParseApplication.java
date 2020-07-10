package com.example.instaparse_tf;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rigo-parstagram") // should correspond to APP_ID env variable
                .clientKey("CodepathMoveFast")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://rigo-parstagram.herokuapp.com/parse/").build());
    }
}
