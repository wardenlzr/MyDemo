package ftp.bg.com.myftp.act;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.commonadapter.viewgroup.ViewGroupUtils;
import com.mcxtzhang.commonadapter.viewgroup.adapter.single.SingleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ftp.bg.com.myftp.R;
import ftp.bg.com.myftp.bean.TestBean;
import ftp.bg.com.myftp.view.RefreshLayout;

public class Main2Activity extends BaseActivity {

    @BindView(R.id.mLinearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.mRefreshLayout)
    RefreshLayout mRefreshLayout;

    private List<TestBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mDatas.add(new TestBean("张三", "1388888888"));
        //单一ItemView
        ViewGroupUtils.addViews(mLinearLayout, new SingleAdapter<TestBean>(this, mDatas, R.layout.item_home) {
            @Override
            public void onBindView(ViewGroup parent, View itemView, TestBean data, int pos) {
                ((TextView) itemView.findViewById(R.id.id_num)).setText(data.name);
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.add(new TestBean("张三222", "2228888888"));
                mDatas.add(new TestBean("张三222", "2228888888"));
                mDatas.add(new TestBean("张三222", "2228888888"));
            }
        });
    }

}
