package com.eq.EQSuperPlayer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/14.
 */
public class ProgramNameItemManager {
    // 存储sharedpreferences
    public static void setSharedPreference(Context context, int program_name_count) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("program_name_count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("program_name_count", program_name_count);


        editor.commit();// 提交修改
    }

    // 清除sharedpreferences的数据
    public static void removeSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("program_name_count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("program_name_count");
        editor.commit();// 提交修改
    }

    // 获得sharedpreferences的数据
    public static int getSahrePreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("program_name_count", Context.MODE_PRIVATE);
        int password = sharedPreferences.getInt("program_name_count", 1);

        return password;
    }


}

