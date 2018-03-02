package com.lll.beizertest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

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

    /**
     * 模拟View的创建 AppCompatViewInflater sConstructorSignature 的参数
     */
    private static final Object[] mPutMethodArgs = new Object[2];

    /**
     * contentValuesByObj 缓存 方法 Method method = ContentValues.class.getDeclaredMethod("put",String.class,value.getClass());
     */
    private static final Map<String, Method> mMethod = new ArrayMap<>();

    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
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
        ss.replace(ss.length() - 2, ss.length(), ")");
        Log.e(TAG, "DaoSupport-sql:" + ss.toString());
        mSqLiteDatabase.execSQL(ss.toString());
    }

    @Override
    public long insert(List<T> list) {
        //批量插入采用事物
        mSqLiteDatabase.beginTransaction();
        long size = 0;
        for (T t : list) {
            size += insert(t);
        }
        Log.e("TAG", "insert( List<T> list):" + size);
        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
        return size;
    }

    @Override
    public long insert(T t) {//android 的代码是如何插入的？此处如何写
        //mSqLiteDatabase.insert()
        ContentValues contentValues = contentValuesByObj(t);
        //contentValues.put();
        Log.e(TAG, "insert(T t)：" + contentValues);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, contentValues);
    }


    /**
     * obj 转换成ContentValues
     *
     * @param obj
     * @return
     */
    private ContentValues contentValuesByObj(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        ContentValues contentValues = new ContentValues();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();//获取字段名
                Log.e(TAG, "contentValuesByObj-key:" + key);
                mPutMethodArgs[0] = key;
                Object value = field.get(obj);//获取值，类型是Object // 转换类型\
                mPutMethodArgs[1] = value;
                Log.e(TAG, "contentValuesByObj-value:" + value);
                String fieldMethod = field.getType().getName();//获取参数类型
                Method method = mMethod.get(fieldMethod);
                if (method == null) {
                    //contentValues.put(name,);//需要一个字段类型 ,优化，提高云心效率：缓存方法
                    method = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());//获取方法，方法名，参数名类型，参数值类型
                    mMethod.put(fieldMethod,method);
                }
                method.invoke(contentValues, mPutMethodArgs);//类对象，参数名，参数值
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }
        }
        return contentValues;
    }
}
