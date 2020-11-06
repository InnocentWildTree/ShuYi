package safe.safe_department;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 权限工具
 */
public class PermissionTool {
    /**
     * 判断front是否具有权限permission
     * eg：isPermitted(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
     * @param permission
     * @param front
     * @return
     */
    public static boolean isPermitted(String permission,AppCompatActivity front)
    {
        return ContextCompat.checkSelfPermission(front, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 由front发出权限申请，申请列表为permission[]，申请号码为requestCode
     * 之后 front.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) 将自动调用
     * @param permission
     * @param requestCode
     * @param front
     */
    public static void requestPermission(String permission[],int requestCode,AppCompatActivity front)
    {
        ActivityCompat.requestPermissions(front,
                permission,
                requestCode);
    }

    /**
     * 判断PermissionTool.requestPermission后， 权限申请列表中 第i+1个权限的被允许情况
     * @param i
     * @param grantResults
     * @return
     */
    public static boolean isPermitted(int i,int[] grantResults)
    {
        return grantResults[i] == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请内存读写权限，申请号码为requestCode
     * 之后 front.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) 将自动调用
     * 其中 读权限 是 权限申请列表中 第1个权限，
     * 写权限 是 权限申请列表中 第2个权限
     * SD卡权限 是 权限申请列表中 第3个权限
     * @param requestCode
     * @param front
     */
    public static void requestStoragePermission(int requestCode,AppCompatActivity front)
    {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };
        PermissionTool.requestPermission(PERMISSIONS_STORAGE,requestCode,front);
    }

    /**
     * 申请网络权限，申请号码为requestCode
     * 之后 front.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) 将自动调用
     * ，其中 网络权限 是 权限申请列表中 第1个权限
     * @param requestCode
     * @param front
     */
    public static void requestInternetPermission(int requestCode,AppCompatActivity front)
    {
        String[] PERMISSIONS_INTERNET = {
                Manifest.permission.INTERNET
        };
        PermissionTool.requestPermission(PERMISSIONS_INTERNET,requestCode,front);
    }

    /**
     * 申请拨号权限，申请号码为requestCode
     * 之后 front.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) 将自动调用
     * ，其中 拨号权限 是 权限申请列表中 第1个权限
     * @param requestCode
     * @param front
     */
    public static void requestICallingPermission(int requestCode,AppCompatActivity front)
    {
        String[] PERMISSIONS_INTERNET = {
                Manifest.permission.CALL_PHONE
        };
        PermissionTool.requestPermission(PERMISSIONS_INTERNET,requestCode,front);
    }

    /**
     * 申请拍照权限，申请号码为requestCode
     * 之后 front.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) 将自动调用
     * ，其中 拍照权限 是 权限申请列表中 第1个权限
     * @param requestCode
     * @param front
     */
    public static void requestCameraPermission(int requestCode,AppCompatActivity front)
    {
        String[] PERMISSIONS_INTERNET = {
                Manifest.permission.CAMERA
        };
        PermissionTool.requestPermission(PERMISSIONS_INTERNET,requestCode,front);
    }

}
