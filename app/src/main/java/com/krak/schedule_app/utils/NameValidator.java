package com.krak.schedule_app.utils;

public class NameValidator {
    public static boolean isValidUserName(String name){
        return name.matches("\\w*");
    }
}
