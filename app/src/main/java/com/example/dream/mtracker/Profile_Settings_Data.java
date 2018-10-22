package com.example.dream.mtracker;

import java.util.ArrayList;

public class Profile_Settings_Data {

    static ArrayList<Profile_Settings_ListItem> a=new ArrayList<>();

    static String name[]={"Home","Recurrance","New Category","Back up","Password","Clear Password","Rate us"};
//    static String phno[]={"1","2","3","4","5","6","7","8","9","10"};
//    static int img=R.mipmap.ic_launcher;
    public static ArrayList<Profile_Settings_ListItem> getData()
    {
        a.clear();
        Profile_Settings_ListItem item;
        for(int i=0;i<name.length;i++)
        {
            item=new Profile_Settings_ListItem();
            item.setName(name[i]);
            a.add(item);
        }
        return a;
    }
}
