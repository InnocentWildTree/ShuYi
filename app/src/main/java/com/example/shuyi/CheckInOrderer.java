package com.example.shuyi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.data.data_department.ImageOperator;
import com.data.shuyi.Student;
import com.net.net_department.OriginalOrderer;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

public class CheckInOrderer extends OriginalOrderer<Student>{

    MainActivity front;
    Handler handler;
    //ImageView img;

    public CheckInOrderer(Student data, MainActivity front,Handler handler) {
        super("http://192.168.6.109:5000/check_in/", data);
        this.front = front;
        this.handler=handler;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }
    public Bitmap bitmap;
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        front.txt.clearComposingText();

        //img.setImageBitmap(ImageOperator.getBitmap(response));
        InputStream inputStream = response.body().byteStream();//得到图片的流
        bitmap = BitmapFactory.decodeStream(inputStream);
        Message msg = new Message();
        msg.obj = bitmap;
        handler.sendMessage(msg);
        front.txt.setText(bitmap.toString());
    }
}
