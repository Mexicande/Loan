package cn.com.creditloans.ui.actiivty;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.common.ApiService;
import cn.com.creditloans.common.Contacts;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.utils.CaptchaTimeCount;
import cn.com.creditloans.utils.CommonUtil;
import cn.com.creditloans.utils.StatusBarUtil;
import cn.com.creditloans.utils.ToastUtils;
import cn.com.creditloans.view.editext.PowerfulEditText;

/**
 * @author apple
 *         注册
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ed_phone)
    PowerfulEditText edPhone;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.bt_register)
    SuperButton btRegister;
    @BindView(R.id.ed_pw)
    PowerfulEditText edPw;
    private Boolean flag=false;
    private CaptchaTimeCount captchaTimeCount;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.white));
    }
    private void initView() {
        captchaTimeCount = new CaptchaTimeCount(Contacts.Times.MILLIS_IN_TOTAL, Contacts.Times.COUNT_DOWN_INTERVAL, btCode, this);

        layoutTopBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.register);
    }

    private void setListener() {

        edCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()==6&&flag){
                    edPw.setVisibility(View.VISIBLE);
                }else {
                    edPw.setVisibility(View.GONE);
                }
                if(charSequence.toString().length()==6&&edPw.getText().toString().length()>5&&edPhone.getText().toString().length()==11){
                    btRegister.setEnabled(true);
                    btRegister.setUseShape();
                }else {
                    btRegister.setEnabled(false);
                    btRegister.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edPhone.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().length()==11&&edPw.getText().toString().length()>5&&edCode.getText().toString().length()==6){
                    btRegister.setEnabled(true);
                    btRegister.setUseShape();
                }else {
                    btRegister.setEnabled(false);
                    btRegister.setUseShape();
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
                if(s.toString().length()>5&&edCode.getText().toString().length()==6&&edPhone.getText().toString().length()==11){
                    btRegister.setEnabled(true);
                    btRegister.setUseShape();
                }else {
                    btRegister.setEnabled(false);
                    btRegister.setUseShape();
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


    @OnClick({R.id.layout_top_back, R.id.bt_code, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.bt_code:
                String phone = edPhone.getText().toString();
                boolean b = CommonUtil.checkPhone(phone, true);
                if(b){
                    getCode(phone);
                }
                break;
            case R.id.bt_register:
                register();
                break;
            default:
                break;
        }
    }

    private void getCode(String phone) {
        captchaTimeCount.start();
        JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("phone",phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ApiService.GET_SERVICE(Api.LOGIN.GETCODE, jsonObject, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject json) {
                    flag=true;
                    ToastUtils.showToast("发送成功");
                }

                @Override
                public void requestFailure(int code, String msg) {
                    ToastUtils.showToast(msg);
                }
            });
    }

    private void register() {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();

        final String phone = edPhone.getText().toString();
        String code = edCode.getText().toString();
        String pw = edPw.getText().toString();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone",phone);
            jsonObject.put("password",pw);
            jsonObject.put("message_yzm",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiService.GET_SERVICE(Api.LOGIN.REGISTER, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                if(hud.isShowing()){
                    hud.dismiss();
                }
                Intent intent=new Intent();
                intent.putExtra("phone",phone);
                setResult(100,intent);
                finish();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                if(hud.isShowing()){
                    hud.dismiss();
                }
            }
        });
    }
}
