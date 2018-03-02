package com.lll.beizertest.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by longlong on 2018/3/1.
 *
 * @ClassName: DaoSupport
 * @Description: 实现一个自己的数据库操作。也可以使用其他第三方的库实现增删改查
 * @Date 2018/3/1
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private SQLiteDatabase mSqLiteDatabase;

    private Class<T> mClazz;

    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz){
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
        //创建表
        StringBuffer ss = new StringBuffer();
        ss.append("create table if not exists ")
                .append(DaoUtil.getTableName(clazz))
                .append("(id integer primary key autoincrement ,");
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            ss.append(name);
            String simpleName = field.getType().getSimpleName();// int String boolean
            // type 有三种类型，需要转换 成 数据库的类型
            String type = DaoUtil.getColumnType(simpleName);
            ss.append(type);
            ss.append(", ");
        }
        ss.replace(ss.length()-2,ss.length(),")");
        Log.e(TAG,"DaoSupport-sql:"+ss.toString());
        mSqLiteDatabase.execSQL(ss.toString());
    }

    @Override
    public long insert( List<T> list) {
        long size=0;
        for (T t : list) {
           size += insert(t);
        }
        Log.e("TAG","insert( List<T> list):"+size);
        return size;
    }

    @Override
    public long insert(T t) {//android 的代码是如何插入的？此处如何写
        //mSqLiteDatabase.insert()
        ContentValues contentValues =contentValuesByObj(t);
        //contentValues.put();
        Log.e(TAG,"insert(T t)："+contentValues);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz),null,contentValues);
    }


    /**
     * obj 转换成ContentValues
     * @param obj
     * @return
     */
    private ContentValues contentValuesByObj(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();//获取字段名
            Log.e(TAG,"contentValuesByObj-key:"+key);
            try {
                Object value = field.get(obj);//获取值，类型是Object // 转换类型\
                Log.e(TAG,"contentValuesByObj-value:"+value);
                //contentValues.put(name,);//需要一个字段类型
                Method method = ContentValues.class.getDeclaredMethod("put",String.class,value.getClass());//获取方法，方法名，参数名类型，参数值类型
                method.invoke(contentValues,key,value);//类对象，参数名，参数值
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }
}
