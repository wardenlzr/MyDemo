package bg.com.myftp.act;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import bg.com.myftp.R;
import bg.com.myftp.util.CameraUtils;
import bg.com.myftp.util.L;
import bg.com.myftp.util.MaterialDialog;

public class MainActivity extends BaseActivity {

    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.tv)
    TextView tv;
    ProgressDialog pd;
    @BindView(R.id.iv)
    ImageView iv;

    private MaterialDialog mMaterialDialog;
    private PopupWindow mPopWindow;
    private CameraUtils cameraUtils;
    private ArrayList<String> mImgList;
    private GVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pd = new ProgressDialog(context);
        setAdapter();
    }

    private void setAdapter() {
        mImgList = new ArrayList<>();
        mImgList.add("/mnt/sdcard/Pictures/Screenshots/Screenshot_2016-12-08-10-06-23.png");
        mAdapter = new GVAdapter(mImgList);
        gv.setAdapter(mAdapter);
    }

    public void takePhoto(View view) {
        cameraUtils = new CameraUtils(this);
        cameraUtils.openGallery(new CameraUtils.CameraCallBack() {
            @Override
            public void ok(List< PhotoInfo> resultList) {
                L.t(context, resultList.size() + "张");
                mImgList.clear();
                ArrayList<String> imgs = new ArrayList<>();
                for (PhotoInfo photoInfo : resultList) {
                    imgs.add(photoInfo.getPhotoPath());
                }
                mAdapter.ref(imgs);
            }

            @Override
            public void fail(String errorMsg) {

            }
        });
        /*GalleryFinal.openCamera(OPEN_CAMERE, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == OPEN_CAMERE) {
                    String photoPath = resultList.get(0).getPhotoPath();
                    L.i("photoPath:" + photoPath);
                    Glide.with(context)
                            .load(photoPath)
                            .into(iv);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });*/
    }

    public void httpdownload(View view) {
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("MaterialDialog")
                .setMessage(
                        "Hi! This is a MaterialDialog. It's very easy to use, you just new and show() it " +
                                "then the beautiful AlertDialog will show automatically. It is artistic, conforms to Google Material Design." +
                                " I hope that you will like it, and enjoy it. ^ ^")
                //mMaterialDialog.setBackgroundResource(R.drawable.background);
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Ok",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("CANCEL",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                Toast.makeText(MainActivity.this,
                                        "Cancel", Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                .setCanceledOnTouchOutside(false)
                // You can change the message anytime.
                .setTitle("提示")
                .setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Toast.makeText(MainActivity.this,
                                        "onDismiss",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }

    public void httpupload(View view) {
        startActivity(new Intent(context, Main1Activity.class));
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void next2(View view) {
        //共享元素
        ImageView share = (ImageView) findViewById(R.id.share);
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("transition", "share");

        //将原先的跳转改成如下方式，注意这里面的第三个参数决定了ActivityTwo 布局中的android:transitionName的值，它们要保持一致
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, share, "shareTransition").toBundle());

    }

    class GVAdapter extends BaseAdapter {
        private ArrayList<String> mDatas;

        public GVAdapter(ArrayList<String> imgList) {
            mDatas = imgList;
        }

        public void ref(ArrayList<String> imgList) {
            if(imgList != null && imgList.size()!=0) {
                mDatas.clear();
                mDatas.addAll(imgList);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        @Override
        public Object getItem(int i) {
            return mDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null)
            view = new ImageView(MainActivity.this);
            view.setLayoutParams(new AbsListView.LayoutParams(200,200));
            L.i(i+"path"+mDatas.get(i));
            loadImg((ImageView) view,mDatas.get(i));
            return view;
        }
    }
    public void loadImg(ImageView iv,String path){
        Glide.with(context).load(path).into(iv);
    }
    private void showPopupWindow() {
        //设置contentView
        final View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_home, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView tv1 = (TextView) contentView.findViewById(R.id.id_num);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                L.t(context,"人生如此美好");

            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.TOP | Gravity.RIGHT, 20, 80);
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopWindow != null && mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
                return false;
            }
        });
    }

    LocationListener ll = new LocationListener() {
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
//            Location l = lm.getLastKnownLocation(provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                Toast.makeText(MainActivity.this, reqeustCode + "", Toast.LENGTH_SHORT).show();
                L.i(resultList.get(0).getPhotoPath());
//                mPhotoList.addAll(resultList);
//                mChoosePhotoListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            L.i(requestCode + errorMsg);
        }
    };


    public static final String FTP_CONNECT_SUCCESSS = "ftp连接成功";
    public static final String FTP_CONNECT_FAIL = "ftp连接失败";
    public static final String FTP_DISCONNECT_SUCCESS = "ftp断开连接";
    public static final String FTP_FILE_NOTEXISTS = "ftp上文件不存在";

    public static final String FTP_UPLOAD_SUCCESS = "ftp文件上传成功";
    public static final String FTP_UPLOAD_FAIL = "ftp文件上传失败";
    public static final String FTP_UPLOAD_LOADING = "ftp文件正在上传";

    public static final String FTP_DOWN_LOADING = "ftp文件正在下载";
    public static final String FTP_DOWN_SUCCESS = "ftp文件下载成功";
    public static final String FTP_DOWN_FAIL = "ftp文件下载失败";

    /*progressBar.setVisibility(View.VISIBLE);
        byte[] imgBinary = L.image2byte(getSDPath() + "aaa.jpg");
        String s = Base64.encodeToString(imgBinary, Base64.DEFAULT);
        RequestParams params = new RequestParams();
        params.addBodyParameter("pic",s);
        params.addBodyParameter("fileName","aaa.jpg");
        params.addBodyParameter("uploadTime","2016/12/01");
        params.addBodyParameter("tbDriverInfoId","sim000001");
        String url = "http://221.6.22.178:5000/WebService.asmx/UploadFileByByte";
        new HttpUtils(5000).send(HttpRequest.HttpMethod.POST, url,params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        progressBar.setVisibility(View.GONE);
                        L.t(context, "onSuccess" + responseInfo.result);
                        L.i("onSuccess" + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        progressBar.setVisibility(View.GONE);
                        L.t(context, "onFailure" + msg);
                        L.i("onFailure" + error.getMessage());
                    }
                });*/

    /*pd.setMessage("获取位置中...");
           *//* pd.setButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });*//*
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.cancel();
                    }
                });
            }
        }).start();
        showPopupWindow();
        LocationUtils.register(context, 1000 * 60, 1, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
//                L.t(context,"getLastKnownLocation..."+location.getProvider()+location);
                L.i("getLastKnownLocation..."+location.getProvider());
            }

            @Override
            public void onLocationChanged(Location location) {
                L.i("onLocationChanged..."+location.getProvider());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                L.i("onStatusChanged..."+provider);
            }
        });
        ArrayList<String> ll = LocationUtils.getLL(context, MainActivity.this.ll);
//        L.t(context,"ll:"+ll.get(0)+",   "+ll.get(1));
        if(ll != null && ll.size() > 1) {
            Address address = LocationUtils.getAddress(context, Double.parseDouble(ll.get(0)), Double.parseDouble(ll.get(1)));
            String s = address.getCountryName() + address.getLocality() + address.getFeatureName();
            tv.setText(s);
        }*/
    private final int REQUEST_CODE_GALLERY = 1001;
}
