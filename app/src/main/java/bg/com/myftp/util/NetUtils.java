package bg.com.myftp.util;

import android.content.Context;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by pyj on 2016/12/5.
 */

public class NetUtils {

    private OkHttpClient mOkHttpClient;

    public NetUtils(Context mContext){
        //创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();
    }
    public interface HttpCall{
        public void ok(String result);
        public void fail(String result);
    }
    public void get(String url, final HttpCall hc){
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)//"https://github.com/hongyangAndroid"
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                hc.fail(request.toString());
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                hc.ok(response.body().string());
            }

        });
    }
}
