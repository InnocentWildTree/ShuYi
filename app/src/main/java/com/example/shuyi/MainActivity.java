package com.example.shuyi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.data.data_department.AndroidFilesysOperator;
import com.data.data_department.ImageOperator;
import com.net.net_department.OkHTTPOrderer;
import com.net.net_department.WebTools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import safe.safe_department.PermissionTool;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    ImageView img;
    TextView txt;
    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap)msg.obj;
            img.setImageBitmap(bitmap);//显示图片
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debugtest);

        button=(Button)this.findViewById(R.id.button);
        img=(ImageView)this.findViewById(R.id.imageView);
        txt=(TextView)this.findViewById(R.id.textView);

        button.setOnClickListener(this);
        PermissionTool.requestStoragePermission(1,this);

    }

    /**
     * 拍完照后自动调用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ImageOperator.takingPhotoIsOk(resultCode,this))
        {
            try {
                Bitmap a=ImageOperator.getBitmap(temp,this);
                ImageOperator.displayBitmap(img,a);
                //AndroidFilesysOperator.saveJPGInside(a,"a.jpg",this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 权限申请后自动调用
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionTool.isPermitted(0,grantResults))
        {

        }
    }

    Uri temp;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
        //File temp=new File(this.getExternalMediaDirs()[0],"out.jpg");
        //OkHTTPOrderer.PostJpg("http://192.168.6.109:5000/check_in/",new CheckInOrderer(null,txt),temp,"out.jpg");

        //temp=ImageOperator.takePhoto(this.getExternalMediaDirs()[0],"out.jpg","com.example.cameraalbumtest.fileprovider",1,this);

        //OkHTTPOrderer.Get("http://192.168.6.109:5000/static/images/out.jpg",new CheckInOrderer(null,this,handler));
        txt.setText(String.valueOf(WebTools.isConnectIsNomarl(this)));
    }

}
