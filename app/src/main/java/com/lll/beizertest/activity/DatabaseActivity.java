package com.lll.beizertest.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.db.DaoSupport;
import com.lll.beizertest.db.DaoSupportFactory;
import com.lll.beizertest.db.IDaoSupport;
import com.lll.beizertest.db.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * android 数据库 工具测试
 */
public class DatabaseActivity extends AppCompatActivity {

    private final String[] permissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE };

    private List<Person> list = new ArrayList<>();

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        //需要动态授权， 郭霖的数据库是如何动态授权的？？
        //面向接口编程，方便以后替换，可以使用第三方实现。方便修改。
        for(int i=0;i<100;i++){
            list.add(new Person("Darling",20+i));
        }
        person = new Person("my Darling",22);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,permissions,1);
        }else{
            IDaoSupport<Person> iDaoSupport = DaoSupportFactory.getInstance().getDao(Person.class);

            Log.e("TAG","person:"+person);
            iDaoSupport.insert(person);
            Log.e("TAG","currentTime-start:"+System.currentTimeMillis());
            iDaoSupport.insert(list);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1
                || grantResults.length == 0
                || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "需要读写权限", Toast.LENGTH_SHORT).show();
        } else {
            IDaoSupport<Person> iDaoSupport = DaoSupportFactory.getInstance().getDao(Person.class);
            iDaoSupport.insert(person);
        }
    }
}
