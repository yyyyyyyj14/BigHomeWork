package com.android.shortvideoapp.utils;

import java.text.DecimalFormat;

public class Utils {
    public static String convertInt(int num) {
        DecimalFormat df=new DecimalFormat("0.0");
        if (num > 10000)
            return String.valueOf(df.format((float)num/10000))+"w";
        else
            return String.valueOf(num);
    }
}
