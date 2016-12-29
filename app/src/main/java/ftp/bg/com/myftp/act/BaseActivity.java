package ftp.bg.com.myftp.act;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BaseActivity extends Activity {

    public Activity context;
    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        pd = new ProgressDialog(context);
        pd.setMessage("正在拼命加载中...");
    }
    public void loadImg(ImageView iv,String path){
        Glide.with(context).load(path).into(iv);
    }
    public void setPd(boolean flag){
        if(flag)
            pd.show();
        else
            pd.cancel();
    }
    public void setPd(boolean flag,String msg){
        if(flag) {
            pd.setMessage(msg);
            pd.show();
        }
        else
            pd.cancel();
    }
}
