package cn.com.creditloans.ui.actiivty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.utils.AppUtils;

/**
 * @author apple
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutTopBack.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        String appVersionName = AppUtils.getAppVersionName();
        StringBuffer sb=new StringBuffer();
        sb.append(getResources().getString(R.string.app_name));
        sb.append("V");
        sb.append(appVersionName);
        version.setText(sb);
    }


    @Override
    public int getLayoutResource() {
        return R.layout.activity_about;
    }

    @OnClick(R.id.layout_top_back)
    public void onViewClicked() {
        finish();
    }
}
