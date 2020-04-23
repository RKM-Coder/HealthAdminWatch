package com.goalsr.homequarantineTracker.service;

import android.content.Context;
import android.provider.UserDictionary;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;

public class MysyncServiceWorkmanager extends Worker {
    Context mContext;

    public MysyncServiceWorkmanager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        ApiBackGround apiBackGround=new ApiBackGround(mContext);
        apiBackGround.makesyncCall();
        return Result.success();
    }
}
