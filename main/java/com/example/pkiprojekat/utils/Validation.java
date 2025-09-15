package com.example.pkiprojekat.utils;

public class Validation {

    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 4;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^06[0-9]{7,8}$");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isFieldEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }

    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    public static String getFieldError(String fieldName, String value) {
        if (isFieldEmpty(value)) {
            return fieldName + " je obavezno polje";
        }

        switch (fieldName.toLowerCase()) {
            case "korisničko ime":
            case "username":
                if (!isValidUsername(value)) {
                    return "Korisničko ime mora imati najmanje 4 karaktera";
                }
                break;
            case "lozinka":
            case "password":
                if (!isValidPassword(value)) {
                    return "Lozinka mora imati najmanje 6 karaktera";
                }
                break;
            case "telefon":
            case "phone":
                if (!isValidPhone(value)) {
                    return "Telefon mora biti u formatu 06XXXXXXXX";
                }
                break;
            case "email":
                if (!isValidEmail(value)) {
                    return "Email adresa nije validna";
                }
                break;
        }

        return null;
    }
}
