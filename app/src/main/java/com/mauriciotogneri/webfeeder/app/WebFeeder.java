package com.mauriciotogneri.webfeeder.app;

import android.app.Application;
import android.os.StrictMode;

import com.mauriciotogneri.webfeeder.BuildConfig;

import java.lang.Thread.UncaughtExceptionHandler;

public class WebFeeder extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());

        enableStrictMode();
    }

    private void enableStrictMode()
    {
        if (BuildConfig.DEBUG)
        {
            StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
            threadBuilder.detectAll();
            threadBuilder.penaltyLog();
            StrictMode.setThreadPolicy(threadBuilder.build());

            StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
            vmBuilder.detectAll();
            vmBuilder.penaltyLog();
            StrictMode.setVmPolicy(vmBuilder.build());
        }
    }

    public class CustomExceptionHandler implements UncaughtExceptionHandler
    {
        private final UncaughtExceptionHandler defaultHandler;

        public CustomExceptionHandler()
        {
            this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        @Override
        public void uncaughtException(Thread t, Throwable e)
        {
            // TODO Logger.error(e);
            defaultHandler.uncaughtException(t, e);
        }
    }
}