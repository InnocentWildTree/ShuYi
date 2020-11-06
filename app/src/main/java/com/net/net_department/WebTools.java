package com.net.net_department;

import androidx.appcompat.app.AppCompatActivity;//前台类的原始版

import android.content.Context;
import android.content.Intent;//广播单
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 一些网络功能的实现
 * 注意：
 * 1,该工具需要 在 AndroidManifest.xml文件中 最外层获取网络权限，即
 * <uses-permission android:name="android.permission.INTERNET">
 * 2,需要在 AndroidManifest.xml文件 中 application层 加入 android:usesCleartextTraffic="true"
 */
public class WebTools{
    /**
     * 在webview控件中打开目标网页
     * @param front
     * 当前前台
     * @param webviewID
     * webView控件的ID
     * @param site
     * 目标网址
     */
    public static void openWeb(int webviewID,String site,AppCompatActivity front) {
        WebView webView = (WebView) front.findViewById(webviewID);//获取webview控件部门
        webView.getSettings().setJavaScriptEnabled(true);//webview控件部门 的 配置部门 将 jsp支持设为true
        webView.setWebViewClient(new JumpOperator(front));//webview控件部门 设置 网页跳转执行员
        webView.loadUrl(site);
    }

    /**
     * 判断当前前台是否联网
     * @param front
     * @return
     */
    public static boolean isConnectIsNomarl(AppCompatActivity front) {
        ConnectivityManager connectivityManager = (ConnectivityManager) front.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
