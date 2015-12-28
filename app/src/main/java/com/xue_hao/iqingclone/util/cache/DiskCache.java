package com.xue_hao.iqingclone.ui.util.cache;

import java.io.File;

/**
 * Created by hao on 2015/12/28.
 */
public interface DiskCache {
    /**
     * 返回Cache的目录
     *
     * @return
     */
    public File getCacheDir();

    /**
     * 清空缓存目录
     */
    public void clearCache();


    /**
     * 获取总共的大小
     */
    public void getTotalSize();


    /**
     * 是否已经被占满
     * @return 是否被占满
     */
    public boolean isCompletelyFilled();


    /**
     *
     * 添加一个
     * @param Key
     * @param entity
     * @return
     */
    public boolean addEntity(String Key,Object entity);




}
