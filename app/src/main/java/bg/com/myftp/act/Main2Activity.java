package bg.com.myftp.act;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bg.com.myftp.R;
import bg.com.myftp.bean.TestBean;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        String transition = getIntent().getStringExtra("transition");

        switch (transition) {
            case "share":
                break;
        }

        // 所有操作在设置内容视图之前
        setContentView(R.layout.activity_main2);

    }

    public void share(View view){
       onBackPressed();
    }

}
