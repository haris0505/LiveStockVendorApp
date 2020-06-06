package com.example.livestockvendorapp.Model;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static User currentuser;
    public static List<Orderlist> lists = new ArrayList<>();

    public static String login="LOGIN";
    public static String checklogin="Check_Login";

    public static double longitude=0;
    public static double latitude=0;

    public static void insert(Orderlist value) {
        for (Orderlist i : lists) {

            lists.add(value);
        }


    }


}
