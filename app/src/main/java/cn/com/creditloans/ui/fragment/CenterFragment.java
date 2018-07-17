package cn.com.creditloans.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.creditloans.R;
import cn.com.creditloans.intr.LoginListener;
import cn.com.creditloans.model.LoginEvent;
import cn.com.creditloans.ui.actiivty.AboutActivity;
import cn.com.creditloans.ui.actiivty.LoginActivity;
import cn.com.creditloans.ui.actiivty.RegisterActivity;
import cn.com.creditloans.utils.ActivityUtils;
import cn.com.creditloans.utils.LogUtils;
import cn.com.creditloans.utils.SPUtil;
import cn.com.creditloans.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment{


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.quit)
    SuperTextView quit;
    Unbinder unbinder;
    private final int REQUESTION_CODE=1000;
    private final int RESULT_CODE=200;

    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    private void initView() {
        String token = SPUtil.getString("token");
        String phone = SPUtil.getString("phone");
        if(!TextUtils.isEmpty(token)){
            String below = phone.substring(0, 3);
            String above = phone.substring(phone.length() - 4, phone.length());
            mPhone.setText(below+"****"+above);
            quit.setVisibility(View.VISIBLE);
            ivHeader.setImageResource(R.mipmap.iv_user_head);
        }

    }

    @OnClick({R.id.iv_header, R.id.super_about, R.id.super_chat, R.id.quit,R.id.phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
            case R.id.phone:
                String token = SPUtil.getString("token");
                if(TextUtils.isEmpty(token)){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,REQUESTION_CODE);
                }
                break;
            case R.id.super_about:
                ActivityUtils.startActivity(AboutActivity.class);
                break;
            case R.id.super_chat:
                ChatFragment chatFragment=new ChatFragment();
                chatFragment.show(getFragmentManager(),"wechatFragment");
                break;
            case R.id.quit:
                SPUtil.clear();
                mPhone.setText("请登录");
                quit.setVisibility(View.GONE);
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,REQUESTION_CODE);
                ivHeader.setImageResource(R.mipmap.defualt_header);

                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTION_CODE){
            if(resultCode==RESULT_CODE){
                String tele = data.getStringExtra("phone");
                String below = tele.substring(0, 3);
                String above = tele.substring(tele.length() - 4, tele.length());
                mPhone.setText(below+"****"+above);
                quit.setVisibility(View.VISIBLE);
                ivHeader.setImageResource(R.mipmap.iv_user_head);

            }
        }
    }
    @Subscribe
    public void LoginMessage(LoginEvent event){
        String telephone = event.getTelephone();
        String below = telephone.substring(0, 3);
        String above = telephone.substring(telephone.length() - 4, telephone.length());
        mPhone.setText(below+"****"+above);
        quit.setVisibility(View.VISIBLE);
        ivHeader.setImageResource(R.mipmap.iv_user_head);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
