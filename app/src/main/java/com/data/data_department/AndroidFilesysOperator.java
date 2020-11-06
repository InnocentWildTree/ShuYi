package com.data.data_department;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 安卓文件系统操作员
 * 如果存入目标文件夹没有反应，手动在目标文件夹下面随便新建一个文件看看，兴许就卡了一个bug，出来了
 * 注:1，使用本类时，请在manifest.xml文件的manifest层声明<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 以获取sd卡写入权限，要么就自己动态获取权限，在com.safe.safe_department中有PermissionTool完成此操作
 */
public class AndroidFilesysOperator {

    /**
     * 将 inputText字符串存储到 安卓文件系统中的（内部缓存中） data/data/<当前app标识符(eg:com.example.ShuYi)>/files/<filename> 处
     * 即写入内部存储，与loadInside搭配使用
     * @param inputText
     * 存入的字符串
     * @param filename
     * 目标文件名
     * @param front
     * 当前前台
     * @param IsCover
     * 是否覆盖写入
     */
    public static void saveInside(String inputText, String filename, boolean IsCover, AppCompatActivity front) {
        FileOutputStream out = null;//写入流
        BufferedWriter writer = null;//写入缓存操作员
        try {
            if(IsCover)
            {
                out = front.openFileOutput(filename, Context.MODE_PRIVATE);//获取写入流，覆盖写入
            }else
            {
                out = front.openFileOutput(filename, Context.MODE_APPEND);//获取写入流，在后面写入
            }
            writer = new BufferedWriter(new OutputStreamWriter(out));//先生成 写入流操作员，再生成 写入缓存操作员
            writer.write(inputText);//流操作员写入数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将 安卓文件系统中的（内部缓存中） data/data/<当前app标识符(eg:com.example.ShuYi)>/files/<filename> 处的文件以 字符串方式读出
     * ，与saveInside搭配使用
     * @param filename
     * 文件名
     * @param front
     * 当前前台
     * @return
     */
    public static String loadInside(String filename, AppCompatActivity front) {
        FileInputStream in = null;//读出流
        BufferedReader reader = null;//读出缓存操作员
        StringBuilder content = new StringBuilder();//字符串操作员
        try {
            in = front.openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));//先生成 读出流操作员，再生成 读出缓存操作员
            //下面读出字符串
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);content.append("\n");//字符串操作员 进行 拼接
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 获取SD卡的根目录路径
     * @return
     */
    public static String getSDCARDPATH()
    {
        String SDCARDPATH = Environment.getExternalStorageDirectory().getPath();//获取sd卡的根目录路径
        return SDCARDPATH;
    }

    /**
     * 判断是否存在SD卡
     * @return
     */
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 没有的话，创建1个filePath路径下的文件夹，返回该dir
     * @param filePath
     */
    public static File createFolder(String filePath)
    {
        //通过创建对应路径的下是否有相应的文件夹。
        File dir = new File(filePath);
        if (!dir.exists()) {// 判断文件目录是否存在
            //如果文件不存在则创建文件夹。
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 在filePath下创建1个空文件filename，将覆盖原文件
     * @param filePath
     * @param filename
     */
    public static File createEmptyFile(String filePath,String filename)
    {
        File temp = new File(filePath, filename);//在app外部缓存路径下创建1个空文件
        try {
            if (temp.exists()) {
                temp.delete();
            }
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 在filePath下创建1个空文件filename，将覆盖原文件
     * @param filePath
     * @param filename
     */
    public static File createEmptyFile(File filePath,String filename)
    {
        File temp = new File(filePath, filename);//在app外部缓存路径下创建1个空文件
        try {
            if (temp.exists()) {
                temp.delete();
            }
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 返回 front对应的app外缓存路径（sd卡中），不会被自动清除，除非卸载该app
     * @param front
     * @return
     */
    public static String getExternalCacheDir(AppCompatActivity front)
    {
        return front.getExternalCacheDir().toString();
    }

    /**
     * 返回 front对应的app内缓存路径（内部缓存），一般为data/data/<当前app标识符(eg:com.example.ShuYi)>/cache/，可能被自动清楚
     * @param front
     * @return
     */
    public static String getCacheDir(AppCompatActivity front)
    {
        return front.getCacheDir().toString();
    }

    /**
     *在 /storage/emulated/0/Android/media/<本app对应标识名>/ 下创建1个空文件，并且返回该文件的Uri，
     * provider_authorities是本app共享仓库的标识名
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
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Uri createEmptyFileUri(String filename, String provider_authorities, AppCompatActivity front)
    {
        File output = AndroidFilesysOperator.createEmptyFile(front.getExternalMediaDirs()[0], filename);//在/storage/emulated/0/Android/media/<本app对应标识名>/ 下创建1个空文件
        Uri imageUri;
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(output);
        } else {
            imageUri = FileProvider.getUriForFile(front, provider_authorities, output);
        }
        return imageUri;
    }

    /**
     *在 filePath 下创建1个空文件，并且返回该文件的Uri，
     * provider_authorities是本app共享仓库的标识名
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
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Uri createEmptyFileUri(File filePath,String filename, String provider_authorities, AppCompatActivity front)
    {
        File output = AndroidFilesysOperator.createEmptyFile(filePath, filename);//在/storage/emulated/0/Android/media/<本app对应标识名>/ 下创建1个空文件
        Uri imageUri;
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(output);
        } else {
            imageUri = FileProvider.getUriForFile(front, provider_authorities, output);
        }
        return imageUri;
    }

    public File getDataDirectory()
    {
        return Environment.getDataDirectory();
    }

    /**
     * 将 bitmap以jpg的形式 存储到 安卓文件系统中的（内部缓存中） data/data/<当前app标识符(eg:com.example.ShuYi)>/files/<filename> 处，
     * 其文件名为filename
     * @param bitmap
     * @param filename
     * @param front
     * @throws IOException
     */
    public static void saveJPGInside(Bitmap bitmap, String filename, AppCompatActivity front) throws IOException {
        FileOutputStream out= front.openFileOutput(filename, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
        out.flush();
        out.close();
    }

    /**
     * 把pic_uri处的图片存入filePath/filename下，并且载入相册，失败
     * @param filePath
     * @param filename
     * @param pic_uri
     * @param front
     * @throws IOException
     */
    public static void saveJPGToGallery(File filePath,String filename,Uri pic_uri,AppCompatActivity front) throws IOException
    {
        File output = AndroidFilesysOperator.createEmptyFile(filePath, filename);
        Bitmap bmp=ImageOperator.getBitmap(pic_uri,front);
        FileOutputStream fos = new FileOutputStream(output);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        //fos.flush();
        //fos.close();

        // 把file里面的图片插入到系统相册中
        //MediaStore.Images.Media.insertImage(front.getContentResolver(), output.getAbsolutePath(), filename, null);
        //ImageOperator.flushAlbum(pic_uri,front);
    }

}