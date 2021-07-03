package com.wj.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataChange {

    public static List<String> listTagNameorID(String tagsName){
        List<String> name = new ArrayList<>();

        if (!"".equals(tagsName) && tagsName != null) {
            String[] namearray = tagsName.split(",");
            for (int i=0; i < namearray.length;i++) {
                name.add(namearray[i]);
            }
        }

        return name;
    }

    public static String titleChange(String title , int limit){
        if(title!=null){
            String s = title;
            if(title.length()>limit){
                s =title.substring(0,limit)+"......";
            }
            return s;
        }
        return "";

    }
    public static String getFormat(String title){
        String s = "";
        if(title!=null){
            List<String> list = Arrays.asList(title.split("\\."));
           if(list!=null){
               s=list.get(list.size()-1);
           }
        }
        return s;
    }

}
