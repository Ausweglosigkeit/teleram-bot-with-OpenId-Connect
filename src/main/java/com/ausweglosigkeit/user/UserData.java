package com.ausweglosigkeit.user;


import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static final Map<String, Map<String, String>> dataOfUser = new HashMap<>();

    public static boolean addUser(String id, Map<String, String> data) {
        if (dataOfUser.containsKey(id)) {
            return false;
        }
        dataOfUser.put(id, data);
        return true;
    }


    public static String getLogin(String id) {
        return dataOfUser.get(id).get("login");
    }

    public static void getInfo(String id) {
        dataOfUser.get(id).forEach((key, value) -> System.out.println(key + "=>" + value));
    }
}
