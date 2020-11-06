package com.data.data_department;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.Response;

public class ImageOperator {

    /**
     * 拍照完毕后，将在front的类中的onActivityResult函数中产生反馈，拍照结果已存入imageUri处
     * @param front
     * @return
     */
    public static void takePhoto(Uri imageUri,int requestCode,AppCompatActivity front)
    {
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//拍照结果存入imageUri处
        front.startActivityForResult(intent, requestCode);
    }

    /**
     * 先 在/storage/emulated/0/Android/media/<本app对应标识名>/ 下创建1个空文件，provider_authorities是本app共享仓库的标识名，然后
     * 拍照，拍照完毕后，将在front的类中的onActivityResult函数中产生反馈，拍照结果已存入 空文件 处，最后返回该空文件的Uri
     * 使用本函数，需要在manifest.xml中，application层下做类似下面的声明：
     * <!--android:authorities是共享仓库标识名-->
     *         <provider
     *             android:name="androidx.core.content.FileProvider"
     *             android:authorities="com.example.cameraalbumtest.fileprovider"
     *             android:exported="false"
     *             android:grantUriPermissions="true">
     *             <!--下面定义共享仓库的存储路径-->
     *             <meta-data
     *                 android:name="android.support.FILE_PROVIDER_PATHS"
     *                 android:resource="@xml/file_paths" />
     *         </provider>
     *其中，xml/file_paths的内容为
     * <?xml version="1.0" encoding="utf-8"?>
     * <paths xmlns:android="http://schemas.android.com/apk/res/android">
     *     <external-path name="my_images" path="" />
     * </paths>
     * @param filename
     * @param provider_authorities
     * 本app共享仓库的标识名
     * @param requestCode
     * @param front
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Uri takePhoto(String filename, String provider_authorities, int requestCode, AppCompatActivity front)
    {
        Uri temp=AndroidFilesysOperator.createEmptyFileUri(filename,provider_authorities,front);
        ImageOperator.takePhoto(temp,requestCode,front);
        return temp;
    }

    /**
     * 先 在filePath 下创建1个空文件，provider_authorities是本app共享仓库的标识名，然后
     * 拍照，拍照完毕后，将在front的类中的onActivityResult函数中产生反馈，拍照结果已存入 空文件 处，最后返回该空文件的Uri
     * 使用本函数，需要在manifest.xml中，application层下做类似下面的声明：
     * <!--android:authorities是共享仓库标识名-->
     *         <provider
     *             android:name="androidx.core.content.FileProvider"
     *             android:authorities="com.example.cameraalbumtest.fileprovider"
     *             android:exported="false"
     *             android:grantUriPermissions="true">
     *             <!--下面定义共享仓库的存储路径-->
     *             <meta-data
     *                 android:name="android.support.FILE_PROVIDER_PATHS"
     *                 android:resource="@xml/file_paths" />
     *         </provider>
     *其中，xml/file_paths的内容为
     * <?xml version="1.0" encoding="utf-8"?>
     * <paths xmlns:android="http://schemas.android.com/apk/res/android">
     *     <external-path name="my_images" path="" />
     * </paths>
     * @param filename
     * @param provider_authorities
     * 本app共享仓库的标识名
     * @param requestCode
     * @param front
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Uri takePhoto(File filePath, String filename, String provider_authorities, int requestCode, AppCompatActivity front)
    {
        Uri temp=AndroidFilesysOperator.createEmptyFileUri(filePath,filename,provider_authorities,front);
        ImageOperator.takePhoto(temp,requestCode,front);
        return temp;
    }


    /**
     * 判断拍照是否成功
     * @param resultCode
     * @param front
     * @return
     */
    public static boolean takingPhotoIsOk(int resultCode,AppCompatActivity front)
    {
        return resultCode==front.RESULT_OK;
    }

    /**
     * 打开相册
     * @param front
     */
    public static void openAlbum(int requestCode,AppCompatActivity front)
    {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        front.startActivityForResult(intent, requestCode); // 打开相册
    }

    /**
     * front前台从imageUri处读出bitmap图片
     * @param front
     * @param imageUri
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap getBitmap(Uri imageUri,AppCompatActivity front) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeStream(front.getContentResolver().openInputStream(imageUri));
        return bitmap;
    }

    /**
     * 返回okhttp中 Get请求报下 服务器返回的图片
     * @param response
     * @return
     */
    public static Bitmap getBitmap(Response response)
    {
        InputStream inputStream = response.body().byteStream();//得到图片的流
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    /**
     * imgview显示bm图片
     * @param imgview
     * @param bm
     */
    public static void displayBitmap(ImageView imgview,Bitmap bm)
    {
        imgview.setImageBitmap(bm);
    }


}
