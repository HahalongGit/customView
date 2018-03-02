package com.lll.beizertest.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by longlong on 2018/3/1.
 *
 * @ClassName: IDaoSupport
 * @Description: 面向接口编程
 * @Date 2018/3/1
 */

public interface IDaoSupport<T> {

    /**
     * 插入数据库
     * @param t
     * @return
     */
    public long  insert(T t);

    /**
     * 初始化参数
     * @param sqLiteDatabase
     * @param clazz
     */
    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    /**
     * 插入
     * @param list
     */
    long insert(List<T> list);

}
