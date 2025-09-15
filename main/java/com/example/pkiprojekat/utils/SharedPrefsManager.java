package com.example.pkiprojekat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pkiprojekat.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
//    private PREF variables
    private static final String PREF_NAME = "TrenuciZaPamcenje";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String CURRENT_USER = "current_user";
    private static final String ALL_USERS = "all_users";

    public SharedPrefsManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void register(User user){
        String userJSON = gson.toJson(user);
        editor.putString(CURRENT_USER, userJSON);
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();

        addUser(user);
    }

    public void login(User user){
        String userJSON = gson.toJson(user);
        editor.putString(CURRENT_USER, userJSON);
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();

        // Also add to all users list
        addUser(user);
    }

    public void logout() {
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.remove(CURRENT_USER);
        editor.apply();
    }

    public void addUser(User user){
        if (getUserByUsername(user.getUsername()) == null){
            List<User> users = getAllUsers();
            users.add(user);
            String usersJson = gson.toJson(users);
            editor.putString(ALL_USERS, usersJson);
            editor.apply();
        }
        // TODO: vidi da li treba da se hendla greska za username
    }

    public List<User> getAllUsers(){
        String usersJSON = sharedPreferences.getString(ALL_USERS, null);
        if (usersJSON != null) {
            Type userListType = new TypeToken<List<User>>(){}.getType();
            return gson.fromJson(usersJSON, userListType);
        }
        return new ArrayList<>();
    }

    public User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        for (User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
