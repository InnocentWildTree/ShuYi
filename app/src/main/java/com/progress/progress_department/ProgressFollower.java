package com.progress.progress_department;

import android.os.Handler;
import android.os.Message;

/**
 * 线程跟进员
 * 本类是1个示范类，
 * 在任意线程内调用下面方法，其中handler是ProgressFollower类的对象，
 * Message msg = new Message();
 * msg.obj = bitmap;
 * handler.sendMessage(msg);
 * 那么在handler被定义的线程内，handler将自动调用1次public void handleMessage(Message msg)
 */
public class ProgressFollower extends Handler{
    public void handleMessage(Message msg) {
        //bitmap = msg.obj;
    }
}
