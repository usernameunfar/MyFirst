package com.example.administrator.myfirstapp.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    private Context mContext;

    public CrashHandler() {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mCrashHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器处理
            mCrashHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //推出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null)
            mCrashHandler = new CrashHandler();
        return mCrashHandler;
    }

    /**
     * 初始化数据
     *
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private boolean handleException(Throwable t) {
        if (t == null) {
            return false;
        }
        //使用toast来显示异常
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将推出", Toast.LENGTH_LONG).show();
                Looper.loop();
                //获取程序信息以及取出异常信息一文件的形式保存到本地然后上传到服务器

            }
        }.start();
        return true;
    }

}
