package com.net.net_department;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 网页跳转执行员
 */
public class JumpOperator extends WebViewClient {
    AppCompatActivity front;//所处前台
    JumpOperator(AppCompatActivity a)
    {
        front=a;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url == null) return false;

        try{
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                front.startActivity(intent);
                return true;
            }
        }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }

        // TODO Auto-generated method stub
        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
        view.loadUrl(url);
        return true;
    }
}
