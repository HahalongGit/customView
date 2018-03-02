package com.lll.beizertest.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by longlong on 2018/3/1.
 *
 * @ClassName: DaoSupportFactory
 * @Description: 数据库 Dao 工厂
 * @Date 2018/3/1
 */

public class DaoSupportFactory {

    private SQLiteDatabase mSQLiteDatabase;

    private static   DaoSupportFactory mDaoSupportFactory;

    private DaoSupportFactory() {
        //把数据库放到内存卡中，不是应用的空间，防止卸载的时候删除数据库。
//        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//                +File.separator+"llTest"+File.separator+"datanase");
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        Log.e("TAG","dbRoot:"+dbRoot.exists());
        File file = new File(dbRoot,"ll_test.db");
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file,null);

    }


    public static  DaoSupportFactory getInstance(){
        if(mDaoSupportFactory==null){
            synchronized (DaoSupportFactory.class){
                if(mDaoSupportFactory==null){
                    mDaoSupportFactory = new DaoSupportFactory();
                }
            }
        }
        return mDaoSupportFactory;
    }


    public <T>IDaoSupport<T> getDao(Class<T> clazz ){
        IDaoSupport<T> daoSupport = new DaoSupport<T>();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }

}
