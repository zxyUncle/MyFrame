package com.normal.zbase.utils.tools;

import android.graphics.Bitmap;

import androidx.collection.LruCache;

/**
 * 缓存管理
 */
public class LCacheManager {
    private static LCacheManager instance;

    private LruCache<String, Bitmap> mLruCache;


    private LCacheManager() {
        //得到当前应用程序的内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //内存缓存为当前应用程序的8分之1
        int cacheMemory = maxMemory / 8;
        //进行初使化
        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//自定义bitmap数据大小的计算方式
            }
        };
    }

    public static LCacheManager getInstance() {
        if (instance == null) {
            synchronized (LCacheManager.class) {
                instance = new LCacheManager();
            }
        }
        return instance;
    }



    /**
     * 保存图片到内存缓存
     *
     * @param key    图片的url
     * @param bitmap 图片
     */
    public void savePicToMemory(String key, Bitmap bitmap) {
        try {
            mLruCache.put(key, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key值得到缓存的图片
     *
     * @param key 图片的url地址
     * @return Bitmap 或 null
     */
    public Bitmap getPicFromMemory(String key) {
        Bitmap bitmap = null;
        try {
            //通过key获取图片
            bitmap = mLruCache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
