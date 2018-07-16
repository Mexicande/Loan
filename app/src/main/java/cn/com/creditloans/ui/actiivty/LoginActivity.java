package cn.com.creditloans.ui.actiivty;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.common.ApiService;
import cn.com.creditloans.intr.LoginListener;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.model.LoginEvent;
import cn.com.creditloans.utils.ActivityUtils;
import cn.com.creditloans.utils.SPUtil;
import cn.com.creditloans.utils.StatusBarUtil;
import cn.com.creditloans.utils.ToastUtils;
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
    private String mproductId;
    private final int REQUSTION_CODE=1000;
    private final int RESULT_CODE=100;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mproductId = getIntent().getStringExtra("id");
        setListener();
    }
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.white));
    }
    private void setListener() {
        edPhone.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if(edPw.getText().toString().length()>5&&s.toString().length()==11){
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                }else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edPw.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if(edPhone.getText().toString().length()==11&&s.toString().length()>5){
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                }else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.login);
    }

    @OnClick({R.id.register, R.id.bt_login,R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,REQUSTION_CODE);
                break;
            case R.id.bt_login:
                intendedLogin();
                break;
            case R.id.layout_top_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void intendedLogin() {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();
        final String phone = edPhone.getText().toString();
        String pw = edPw.getText().toString();
        JSONObject object=new JSONObject();
        try {
            object.put("phone",phone);
            object.put("password",pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.LOGIN.LOGIN, object, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                if(hud.isShowing()){
                    hud.dismiss();
                }
                try {
                    JSONObject data = json.getJSONObject("data");
                    String token = data.getString("token");
                    SPUtil.putString("token",token);
                    SPUtil.putString("phone",phone);
                    if(!TextUtils.isEmpty(mproductId)){
                        EventBus.getDefault().post(new LoginEvent(phone));
                        Intent intent=new Intent(LoginActivity.this,ProductDetailActivity.class);
                        intent.putExtra("id", mproductId);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent();
                        intent.putExtra("phone",phone);
                        setResult(200,intent);
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                if(hud.isShowing()){
                    hud.dismiss();
                }
                ToastUtils.showToast(msg);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUSTION_CODE){
            if(resultCode==RESULT_CODE){
                String phone = data.getStringExtra("phone");
                edPhone.setText(phone);
            }
        }
    }
}
