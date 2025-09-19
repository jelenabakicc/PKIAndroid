package com.example.pkiprojekat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.pkiprojekat.models.CartItem;
import com.example.pkiprojekat.models.Comment;
import com.example.pkiprojekat.models.Notification;
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
    private static final String CART_ITEMS = "cart_items";
    private static final String COMMENTS = "comments";
    private static final String NOTIFICATIONS = "notifications";

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

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
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

    public User getCurrentUser() {
        String userJson = sharedPreferences.getString(CURRENT_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }

    public void saveUserData(User user){
        String userJson = gson.toJson(user);
        editor.putString(CURRENT_USER, userJson);
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();

        updateUserInList(user);
    }

    private void updateUserInList(User updatedUser) {
        List<User> users = getAllUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                break;
            }
        }

        String usersJson = gson.toJson(users);
        editor.putString(ALL_USERS, usersJson);
        editor.apply();
    }

    // Cart management
    public void saveCartItems(List<CartItem> cartItems) {
        String cartJson = gson.toJson(cartItems);
        editor.putString(CART_ITEMS, cartJson);
        editor.apply();
    }

    public List<CartItem> getCartItems() {
        String cartJson = sharedPreferences.getString(CART_ITEMS, null);
        if (cartJson != null) {
            Type type = new TypeToken<List<CartItem>>(){}.getType();
            return gson.fromJson(cartJson, type);
        }
        return new ArrayList<>();
    }

    public void clearCart() {
        editor.remove(CART_ITEMS);
        editor.apply();
    }

    public void removeCartItem(String itemId) {
        List<CartItem> items = getCartItems();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.removeIf(item -> item.getId().equals(itemId));
        }
        saveCartItems(items);
    }

    // Comments management
    public void saveComment(Comment comment) {
        List<Comment> comments = getAllComments();
        comments.add(comment);
        String commentsJson = gson.toJson(comments);
        editor.putString(COMMENTS, commentsJson);
        editor.apply();
    }

    public List<Comment> getComments(String eventId) {
        List<Comment> allComments = getAllComments();
        List<Comment> eventComments = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.getEventId().equals(eventId)) {
                eventComments.add(comment);
            }
        }
        return eventComments;
    }

    private List<Comment> getAllComments() {
        String commentsJson = sharedPreferences.getString(COMMENTS, null);
        if (commentsJson != null) {
            Type type = new TypeToken<List<Comment>>(){}.getType();
            return gson.fromJson(commentsJson, type);
        }
        return new ArrayList<>();
    }

    // Notifications management
    public void saveNotifications(List<Notification> notifications) {
        String notificationsJson = gson.toJson(notifications);
        editor.putString(NOTIFICATIONS, notificationsJson);
        editor.apply();
    }

    public List<Notification> getNotifications() {
        String notificationsJson = sharedPreferences.getString(NOTIFICATIONS, null);
        if (notificationsJson != null) {
            Type type = new TypeToken<List<Notification>>(){}.getType();
            return gson.fromJson(notificationsJson, type);
        }
        return new ArrayList<>();
    }
}
