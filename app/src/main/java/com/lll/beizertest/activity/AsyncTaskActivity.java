package com.lll.beizertest.activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lll.beizertest.R;

/**
 * AsyncTask 分析
 * AsyncTask是这么执行的？AsyncTask是如何实现异步执行，把数据切换到主线程的。
 *
 */
public class AsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        MyAsyncTask myAsyncTask = new MyAsyncTask();

        myAsyncTask.execute("url");//执行Task

    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            // 后台线程
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //进度
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //主线程
        }
    }

}
