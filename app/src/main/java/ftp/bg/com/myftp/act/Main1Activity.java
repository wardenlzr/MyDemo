package ftp.bg.com.myftp.act;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import ftp.bg.com.myftp.R;
import ftp.bg.com.myftp.util.L;
import rx.Observable;
import rx.Subscriber;

public class Main1Activity extends BaseActivity {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private List<String> mDatas;
    private static final String TAG = "Main1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initRecyclerView();
//        init();
    }

    private void init() {
        loadImg(iv, "http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg");
        /*Luban.get(this)
                .load(new File(L.getSDPath()+"aaa.jpg"))                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }
                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                    }
                }).launch();    //启动压缩*/
//        setPd(true);


        /*OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {//开发时放bean对象
            @Override
            public void onError(Request request, Exception e) {
                setPd(false);
                L.i(request.body().toString());
            }

            @Override
            public void onResponse(final String response) {
                L.i(response);
                Main1Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(response);
                    }
                });
            }

        });*/

        /*NetUtils netUtils = new NetUtils(context);//http://221.6.204.6:2000/WebService.asmx/GetSysVERSIONS
        netUtils.get("https://www.baidu.com/", new NetUtils.HttpCall() {
            @Override
            public void ok(final String result) {
                ((Main1Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                        tv.setText(result);
                    }
                });

            }

            @Override
            public void fail(String result) {
                pd.cancel();
                L.t(context,"网络不太好，请稍后重试！");
            }
        });*/
    }
    private String imgPath = "http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg";
    private void initRecyclerView() {

        mDatas = new ArrayList<String>();
       mDatas.add(imgPath);
       mDatas.add(imgPath);
        L.i("mDatas.size():" + mDatas.size());
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

    }

/*
* Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {

                // 从mipmap取出一张图片作为Drawable对象
                Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.sad);

                // 把Drawable对象发送出去
                subscriber.onNext(
                        drawable);

                subscriber.onCompleted();
            }
        })
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.toString());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        // 接收到Drawable对象，显示在ImageView上
                        ivLogo.setImageDrawable(drawable);
                    }
                });
* */
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
            Glide.with(Main1Activity.this).load(mDatas.get(position)).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            ImageView iv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                iv = (ImageView) view.findViewById(R.id.iv);
            }
        }
    }
}
