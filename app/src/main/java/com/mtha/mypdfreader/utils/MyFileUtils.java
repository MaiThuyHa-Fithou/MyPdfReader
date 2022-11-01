package com.mtha.mypdfreader.utils;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.File;

public class MyFileUtils {
    public static String getPdfFromAssets(){
        return "MindOrks_Android_Online_Professional_Course-Syllabus.pdf";
    }

    public static String getPdfUrl(){
        return "https://mindorks.s3.ap-south-1.amazonaws.com/courses/MindOrks_Android_Online_Professional_Course-Syllabus.pdf";
    }

    public static String getRootDirPath(Context context){
        if(Environment.MEDIA_MOUNTED==Environment.getExternalStorageState()){
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        }else
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }
}
