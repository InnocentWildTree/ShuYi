package com.net.net_department;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * JSON相关工具
 * 1,需要在app文件夹下的build.gradle文件中 声明
 * implementation 'com.google.code.gson:gson:2.7'
 */
public class JSONTools<T> {
    /*------------------------------------------------------下面是json文本获取*/
    /**
     * 将 json字典列表list 转换为JSON对应的JSON文本 返回
     * @param list
     * Map<String, String>对象是1个JSON字典，其列表List<Map<String, String>>即为 json字典列表，即list
     * @return
     */
    public static String getJSONStr(List<Map<String, String>> list)
    {
        JSONArray jsonarray = new JSONArray(list);
        return jsonarray.toString();
    }
    /**
     * 将 json字典dict 转换为JSON对应的JSON文本 返回
     * @param dict
     * Map<String, String>对象是1个JSON字典
     * @return
     */
    public static String getJSONStr(Map<String, String> dict) {
        JSONObject jsonObj = new JSONObject(dict);
        return jsonObj.toString();
    }

    /**
     * 将想要传输实体trans打包成JSON文本返回，与本类中的getTrans(String JSONStr,Class<T> tClass)搭配使用
     * @param trans
     * @return
     */
    public String getJSONStr(T trans)
    {
        return new Gson().toJson(trans);
    }

    /*------------------------------------------------------下面是json文本处理*/
    /**
     * 解析JSON文本，返回其JSON字典数组
     * @param JSONStr
     * JSON文本
     * @return
     * @throws JSONException
     */
    public static JSONArray getJSONArray(String JSONStr) throws JSONException {
        return new JSONArray(JSONStr);
    }

    /**
     * 解析JSON文本，返回其对应的对象列表
     * 注：T必须是整个JSON文本对应数组 的 数组元素的类型
     * @param JSONStr
     * JSON文本
     * @return
     */
    public List<T> getList(String JSONStr){
        return new Gson().fromJson(JSONStr, new TypeToken<List<T>>() {}.getType());
    }

    /**
     * 解析JSON文本，返回其对应的对象，与本工具中的getJSONStr(T trans)搭配使用
     * 注：T必须是整个JSON文本对应的那个传输实体类
     * @param JSONStr
     * JSON文本
     * @param tClass
     * 传输实体类.class
     * @return
     */
    public T getTrans(String JSONStr,Class<T> tClass){
        return new Gson().fromJson(JSONStr,tClass);
    }
}
