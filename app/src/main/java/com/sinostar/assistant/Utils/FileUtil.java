package com.sinostar.assistant.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 文件工具类
 */
public class FileUtil {
    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    public static String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }

    /**
     * 获取本地缓存路径
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 判断文件是否存在
     * @param strFile
     * @return
     */
    public static boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

}
