package com.xue_hao.iqingclone.ui.activity;

import android.app.Application;
import android.graphics.Bitmap;

import com.basestonedata.credit.http.ImageDownLoader;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by haox on 2015/9/6.
 */
public class BaseApplication extends Application {
    private ArrayList<BaseActivity> Acivitys;//app自己管理的activity栈
    private static String CAHE_DIR = "/CMS/imageCache";//缓存目录相对路径,缓存图片
    private File Cache;    //缓存目录的根目录

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        Cache = new File(getSDPath(), CAHE_DIR);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                        // 设置图片不缓存于内存中
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                        // 设置图片的质量
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new FadeInBitmapDisplayer(500))//淡入动画
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new UsingFreqLimitedMemoryCache(15 * 1024 * 1024))
                        // 15M内存缓存
                .diskCache(new UnlimitedDiskCache(Cache))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .imageDownloader(
                        new ImageDownLoader(getApplicationContext(),
                                1 * 1000, 30 * 1000)).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 返回app的缓存目录
     *
     * @return
     */
    public File getAPPCachePath() {
        File sdDir;
        sdDir = getCacheDir();
        return sdDir;
    }

}
