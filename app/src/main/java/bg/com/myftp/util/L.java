package bg.com.myftp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 打印日志 转换时间等 工具类
 * Created by yb on 2016/10/31.
 */

public class L {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "BG";

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug)
            android.util.Log.e(TAG, msg);
    }  // 下面四个是默认tag的函数
    public static void i(String tag,String msg)
    {
        if (isDebug)
            android.util.Log.e(tag, msg);
    }
    public static void t(Context c,String s){
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }

    public interface HttpCallBack{
        void ok(String result);
        void fail(String msg);
    }
    //图片到byte数组
    public static byte[] image2byte(String path){
        Bitmap bitmap = convertToBitmap(path,480,800);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return  byteArray;
    }
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }
    /**
     * 获取内存卡根目录
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString() + "/";
    }
    // 上传图片到服务器
    public static void uploadImg(String picPath , final HttpCallBack httpCallBack) {
        RequestParams params = new RequestParams();
        String[] p = picPath.split("/");
        params.addBodyParameter("filename", p[p.length-1]);
        params.addBodyParameter("content-type", "image/jpeg");
        params.addBodyParameter("file", new File(picPath));
        String url = "http://10.32.1.137:9999";
        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException e, String msg) {
                L.i( e.getExceptionCode() + "===上传图片==" + msg);
                httpCallBack.fail(msg);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                L.i( "==上传图片==upload_=====" + responseInfo.result);
                httpCallBack.ok(responseInfo.result);
//                Gson gson = new Gson();
//                UpLoadPicBean upLoadPicBean = gson.fromJson(responseInfo.result, UpLoadPicBean.class);
//                //拼接 类型
//                String imgUrl = upLoadPicBean.getResult().getEntities().get(0).getUrl();
//                L.i("返回的图片链接：" + imgUrl);

            }
        });
    }
    /**
     * 隐藏软键盘
     * @param c
     * @param v
     */
    public static void hideKeyBoard(Context c){
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity)c).getWindow().getDecorView().getWindowToken(),0);
    }
    /**
     * 判断是否是今天
     * @return 是true, 否false
     */

    public static boolean isToday(String time)  {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = format.parse(time);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH) + 1;
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH) + 1;
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            if (year1 == year2 && month1 == month2 && day1 == day2) {
                return true;
            }
            return false;
        }catch (ParseException pe){
            L.i("ParseException..."+pe.getMessage());
        }
        return false;
    }

    //两位小数
    public static String decimal2(double d){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    public static String formatChangData(long m){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(m);
        String data = formatter.format(calendar.getTime());
        return data;
    }


    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public static   boolean isChinese(char c) {
        boolean result = false;
        if (c >= 19968 && c <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
            result = true;
        }
        return result;
        /* 中文或者中文字符
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;*/
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    public static  boolean checkChinese(String name)
    {
        boolean res = false;
        String regex = "[\u4E00-\u9FA5]+";
        if(name.matches(regex)){
            res=true;
        }
        /*中文符号无法检测
        boolean res=true;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(!isChinese(cTemp[i]))
            {
                res=false;
                break;
            }
        }*/
        return res;
    }

}
