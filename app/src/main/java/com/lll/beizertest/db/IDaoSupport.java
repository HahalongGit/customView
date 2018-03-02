package com.lll.beizertest.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by longlong on 2018/3/1.
 *
 * @ClassName: IDaoSupport
 * @Description: 面向接口编程 为什么使用接口：1.使用的时候不关心实现，2.便于后期扩展
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

    /**
     * 查询所有
     * @return
     */
    List<T> query();

    /**
     * 删除 delete
     * @param whereClause
     * @param whereArgs
     * @return
     */
    int delete(String whereClause,String[] whereArgs);

    /**
     * update
     * @param obj
     * @param whereCause
     * @param whereArgs
     * @return
     */
    public int update(T obj,String whereCause,String... whereArgs );

}
