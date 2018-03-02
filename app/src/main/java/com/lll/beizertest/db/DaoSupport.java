package com.lll.beizertest.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    @Override
    public List<T> query() { //查询，重点在于反射获取实例，设置数据
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz),null,null,null,null,null,null);
        return cursorToList(cursor);
    }

    @Override
    public int delete(String whereClause,String[] whereArgs){//删除
        return mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz),whereClause,whereArgs);
    }

    @Override
    public int update(T obj,String whereCause,String ... whereArgs ){//跟新
        ContentValues value = contentValuesByObj(obj);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz),value,whereCause,whereArgs);
    }

    /**
     * 转换Cursor to List
     * @param cursor
     * @return
     */
    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if(cursor!=null && cursor.moveToFirst()){
            do{
                try {
                    //反射创建实例，设置数据
                    T instance = mClazz.newInstance();//创建对象
                    Field[] fields = mClazz.getDeclaredFields();//获取字段
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        //获取字段在数据库的那一列
                        int index = cursor.getColumnIndex(name);
                        if(index ==-1){
                            continue;
                        }
                        Method cursorMethod = createMethod(field.getType());//根据字段类型获取方法(对象中字段的get/set 方法)
                        if(cursorMethod!=null){
                            //反射获取value
                            Object value = cursorMethod.invoke(cursor,index);
                            if(value==null){
                                continue;
                            }
                            //注入值（给对象设置数据）
                            field.set(instance,value);
                        }
                    }
                    list.add(instance);//添加集合
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 根据字段类型获取字段方法
     * @param type
     * @return
     * @throws NoSuchMethodException
     */
    private Method createMethod(Class<?> type) throws NoSuchMethodException {
        String methodName = getColumnMethodName(type);
        //转换 type--> String getString(index),int getInt(index),boolean getBoolean(index),
        Method method = Cursor.class.getMethod(methodName,int.class);
        return method;
    }

    /**
     * 根据字段类型获取cursor 的列方法
     * @param type
     * @return
     */
    private String getColumnMethodName(Class<?> type) {
        String typeName;
        if(type.isPrimitive()){//是否是java基础数据类型
            typeName = DaoUtil.capitalize(type.getName());
        }else {
            typeName = type.getSimpleName();
        }
        String methodName = "get"+typeName;
        if("getBoolean".equals(methodName)){
            methodName = "getInt";
        }else if("getChar".equals(methodName)||"getCharacter".equals(methodName)){
            methodName = "getString";
        }else if("getDate".equals(methodName)){
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
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
                Object value = field.get(obj);//获取值，类型是Object // 转换类型
                mPutMethodArgs[1] = value;
                Log.e(TAG, "contentValuesByObj-value:" + value);
                String fieldMethod = field.getType().getName();//获取参数类型名称
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
