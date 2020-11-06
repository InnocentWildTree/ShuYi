package com.net.net_department;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 下单端口管理员：Okhttp实现
 * 1,需要在app文件夹下的build.gradle文件中 声明
 * implementation 'com.squareup.okhttp3:okhttp:3.10.0'，
 * 2,该工具需要 在 AndroidManifest.xml文件中 最外层获取网络权限，即
 * <uses-permission android:name="android.permission.INTERNET">
 * 3,然后gradle sync一下才能使用此类
 */
public class OkHTTPOrderer {
    /**
     *下单信息包，最后获取的okhttp3.Response信息包 在 callback的onFinish函数中 处理
     * @param address
     * 目标网址
     * @param listener
     * 线程跟进员，其需要重载2个函数，onFinish（获得返回okhttp3.Response信息包时调用）和onError（出错时调用）
     */
    public static void Get(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();//信息包下单员
        Request request = new Request.Builder() //填写邮单
                .get()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);//线程跟进员 在 等 邮局 消息
    }

    /**
     * 以表单项形式发货JSON信息包（"JSONStr":JSONStr），其内容为JSONStr，最后获取的okhttp3.Response信息包 在 callback的onFinish函数中 处理
     * @param address
     * 目标网址
     * @param listener
     * 线程跟进员，其需要重载2个函数，onFinish（获得返回okhttp3.Response信息包时调用）和onError（出错时调用）
     * @param JSONStr
     * JSON信息包内容
     */
    public static void PostJSONStr(final String address, final okhttp3.Callback callback, final String JSONStr) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//信息包发货员

                RequestBody body = new MultipartBody.Builder()//装货
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("JSONStr",JSONStr)
                        .build();

                Request request = new Request.Builder()//填写邮单
                        .url(address)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(callback);//线程跟进员 在 等 邮局 消息
            }
        });
        t1.start();
    }

    /**
     * 发货jpg图片信息包，其内容为file，最后获取的okhttp3.Response信息包 在 callback的onFinish函数中 处理
     * 注：千万注意，File只能从 安卓文件系统 中读取，不能从 AS所处windows系统中读，因为 最终这些代码是在 安卓系统上 运行的
     * @param address
     * 目标网址
     * @param listener
     * 线程跟进员，其需要重载2个函数，onFinish（获得返回okhttp3.Response信息包时调用）和onError（出错时调用）
     * @param file
     * 图片信息包内容
     * @param file_name
     * 图片全称
     */
    public static void PostJpg(final String address, final okhttp3.Callback callback, final File file, final String file_name) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//信息包发货员

                RequestBody image = RequestBody.create(MediaType.parse("image/jpg"), file);
                RequestBody body = new MultipartBody.Builder()//装货
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file",file_name,image)
                        .build();


                Request request = new Request.Builder()//填写邮单
                        .url(address)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(callback);//线程跟进员 在 等 邮局 消息
            }
        });
        t1.start();
    }

    /**
     * 将返回的信息包response化为文本内容返回
     * @param response
     * @return
     */
    public static String getResponse(okhttp3.Response response) throws IOException {
        assert response.body() != null;
        return response.body().string();
    }
}
