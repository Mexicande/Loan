package cn.com.creditloans.ui.actiivty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.view.editext.PowerfulEditText;

/**
 * @author apple
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ed_phone)
    PowerfulEditText edPhone;
    @BindView(R.id.ed_pw)
    PowerfulEditText edPw;
    @BindView(R.id.bt_login)
    SuperButton btLogin;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.login);
    }

    @OnClick({R.id.register, R.id.bt_login,R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                break;
            case R.id.bt_login:
                break;
            case R.id.layout_top_back:
                break;
            default:
                break;
        }
    }
}
