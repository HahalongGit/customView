package com.lll.beizertest.ipc;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Created by longlong on 2018/4/9.
 *
 * @ClassName: JobWakeupService
 * @Description: 设置一个定时任务，一定时间过来检查。 Android 7.0 上默认的最短时间15min .没有低于7.0的手机没法测试
 * @Date 2018/4/9
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeupService extends JobService {

    private static final String TAG = "JobWakeupService";

    private final int jobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        //开始一个轮询
        JobInfo.Builder builder = new JobInfo.Builder(jobWakeUpId,new ComponentName(this,JobWakeupService.class));
        builder.setPeriodic(2000);//设置轮询时间
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG,"onStartJob");
        //开启定时任务，定时轮询，看QQMessageService 有没有被杀死
        //如果杀死了启动。轮询onStartJob
        //查看服务有没有被杀死：
        boolean qqServiceAlive = isServiceWork(QQMessageService.class.getName());
        Log.e(TAG,"onStartJob-qqServiceAlive:"+qqServiceAlive);
        if(qqServiceAlive){
            //没有启动的话就启动QQMessageService 时默认的Service
            startService(new Intent(this,QQMessageService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是不是运行
     * @param serviceName
     * @return
     */
    public boolean isServiceWork(String serviceName){
        boolean isWork = false;
        ActivityManager myAm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = myAm.getRunningServices(100);
        if(runningServices.size()<0){
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            String mName = runningServices.get(i).service.getClassName().toString();
            if(mName.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
