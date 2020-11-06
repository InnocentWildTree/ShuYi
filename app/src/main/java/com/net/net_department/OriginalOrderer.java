package com.net.net_department;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * T类信息包采购员，需要重载onFinish（获得返回okhttp3.Response信息包时调用）和onError（出错时调用）
 */
public class OriginalOrderer<T> implements okhttp3.Callback{
    /**
     * 目标url
     */
    String url="";
    /**
     * 下单时需要传输的data
     */
    T data=null;
    /**
     * 下单信息包
     */
    public void Get()
    {
        OkHTTPOrderer.Get(url,this);
    }

    /**
     * 将传入的data转换为JSON文本 以表单项形式发货（"JSONStr":JSONStr）
     */
    public void PostJSONStr()
    {
        OkHTTPOrderer.PostJSONStr(url,this,new JSONTools<T>().getJSONStr(data));
    }

    /**
     * 重新设置下单时需要传输的data
     * @param data
     */
    public void setData(T data)
    {
        this.data=data;
    }
    /*-----------------------------------下面函数需要重载*/
    public OriginalOrderer(String url,T data)
    {
        this.url=url;
        this.data=data;
    }
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
