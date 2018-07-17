package cn.com.creditloans.ui.fragment;


import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.creditloans.App;
import cn.com.creditloans.R;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.common.ApiService;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.intr.SelectListener;
import cn.com.creditloans.model.Banner;
import cn.com.creditloans.model.Product;
import cn.com.creditloans.ui.actiivty.HtmlActivity;
import cn.com.creditloans.ui.actiivty.LoginActivity;
import cn.com.creditloans.ui.actiivty.ProductDetailActivity;
import cn.com.creditloans.ui.adapter.HomePagerAdapter;
import cn.com.creditloans.ui.adapter.MyViewPagerAdapter;
import cn.com.creditloans.ui.adapter.ProductAdapter;
import cn.com.creditloans.utils.FixedSpeedScroller;
import cn.com.creditloans.utils.LogUtils;
import cn.com.creditloans.utils.SPUtil;
import cn.com.creditloans.utils.ToastUtils;
import cn.com.creditloans.view.VerticalViewPager;
import cn.com.creditloans.view.indicator.CircleIndicator;
import cn.com.creditloans.view.vertical.VerticalBannerView;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.fragment_home_rv)
    RecyclerView mFragmentHomeRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private HomePagerAdapter homePagerAdapter;
    private ProductAdapter mProductAdapter;
    private SelectListener mLisenter;
    private Timer timer;
    private int currentIndex;
    private String productId;
    private final int REQUESTION_CODE=10000;
    private final int RESULT_CODE=200;
    private  List<Banner> banners1;
    private int bannerIndex;
    // 消息滚动滚动
    Handler h = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currentIndex);// 设置此次要显示的pager

        }

    };
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getDate();
        setListener();
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLisenter=(SelectListener)context;
    }

    private void setListener() {

        mRefreshLayout.setEnableLoadMore(false);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDate();
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLisenter.selectTab(1);
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                if (cmb != null) {
                    cmb.setText("口袋查询");
                }
                getWechatApi();
            }
        });
        mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String token = SPUtil.getString("token");
                productId = mProductAdapter.getData().get(position).getProduct_id();
                if(!TextUtils.isEmpty(token)){
                    Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("id",productId);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("id",productId);
                    startActivity(intent);
                }
            }
        });
        newFragmentBanner.setDelegate(new BGABanner.Delegate<ImageView, Banner>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, Banner model, int position) {
                String token = SPUtil.getString("token");
                if (!TextUtils.isEmpty(model.getBanner_url())) {
                    if(TextUtils.isEmpty(token)){
                        bannerIndex=position;
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent,REQUESTION_CODE);
                    }else {
                        Intent intent = new Intent(getActivity(), HtmlActivity.class);
                        intent.putExtra("html",model.getBanner_url());
                        intent.putExtra("title",model.getBanner_name());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * 微信跳转
     */
    private void getWechatApi() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtils.showToast("检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    private void initView() {

        mProductAdapter = new ProductAdapter(null);
        mFragmentHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentHomeRv.setAdapter(mProductAdapter);
        mProductAdapter.addHeaderView(getHeader());
        mProductAdapter.addFooterView(getFooter());

    }


    private TextView tvMore;
    private LinearLayout wechat;
    private View getFooter() {
        View inflate = View.inflate(getActivity(), R.layout.home_foot_layout, null);
        tvMore=inflate.findViewById(R.id.tv_more);
        wechat=inflate.findViewById(R.id.wechat);
        return inflate;

    }

    private BGABanner newFragmentBanner;
    private VerticalViewPager mViewPager;
    private TextView fragment_loan_count;
    private ViewPager viewpage;
    private MyViewPagerAdapter myViewPagerAdapter;
    private CircleIndicator mCircleIndicator;
    private View getHeader() {

        View inflate = View.inflate(getActivity(), R.layout.header_layout, null);

        newFragmentBanner=inflate.findViewById(R.id.new_fragment_banner);
        mViewPager=inflate.findViewById(R.id.new_fragment_vvp);

        viewpage=inflate.findViewById(R.id.viewPager);
        mCircleIndicator=inflate.findViewById(R.id.indicator_default);
        fragment_loan_count=inflate.findViewById(R.id.new_fragment_loan_count);
        newFragmentBanner.setAdapter(new BGABanner.Adapter<ImageView, Banner>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Banner model, int position) {

                RequestOptions requestOptions=new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(App.getApp())
                        .load(model.getBanner_logo_url())
                        .apply(requestOptions)
                        .into(itemView);
            }
        });

        /**
         * 调整viewpager的切换速度
         */
            Field mScroller = null;
        try {
            mScroller = ViewPager.class.getDeclaredField("mScroller");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            if (mScroller != null) {
                mScroller.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ) );
                mScroller.set( mViewPager, scroller);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return inflate;
    }


    /**
     * 循环线程
     */
    private void startCycle() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mViewPager != null) {
                    if (mViewPager.getCurrentItem() == mProductAdapter.getData().size() - 1) {
                        currentIndex = 0;
                    } else {
                        currentIndex = mViewPager.getCurrentItem() + 1;// 下一个页
                    }
                    h.sendEmptyMessage(0);// 在此线程中，不能操作ui主线程
                }
            }
        }, 4000, 4000);

    }

    private void getDate() {
        ApiService.GET_SERVICE(Api.HOME_LIST, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {

                try {
                    String data = json.getString("data");
                    Gson gson=new Gson();
                    Product product = gson.fromJson(data, Product.class);
                    mProductAdapter.setNewData(product.getBzsx());
                    //消息
                    mViewPager.setPageTransformer(false,new DefaultTransformer());
                    homePagerAdapter = new HomePagerAdapter(product.getBzsx());
                    mViewPager.setAdapter(homePagerAdapter);


                    ArrayList<Fragment>mfragments=new ArrayList<>();
                    List<Product.JrrmBean> jrrm = product.getJrrm();
                    int size = product.getJrrm().size();
                    int i = size / 4;
                    for(int j=0;j<i; j++){
                        ArrayList<Product.JrrmBean>list=new ArrayList<>();
                        for(int k=j*4;k<j*4+4;k++){
                            list.add(jrrm.get(k));
                        }
                        HotFragment hotFragment = HotFragment.newInstance(list);
                        mfragments.add(hotFragment);
                    }
                    myViewPagerAdapter=new MyViewPagerAdapter(getChildFragmentManager(),mfragments);
                    viewpage.setAdapter(myViewPagerAdapter);
                    mCircleIndicator.setViewPager(viewpage);
                    myViewPagerAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
                    startCycle();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mRefreshLayout.isEnableRefresh()){
                    mRefreshLayout.finishRefresh();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                if(mRefreshLayout.isEnableRefresh()){
                    mRefreshLayout.finishRefresh();
                }
            }
        });
        ApiService.GET_SERVICE(Api.BANNER, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    Gson gson=new Gson();
                    Banner[] banners = gson.fromJson(data, Banner[].class);
                    banners1 = Arrays.asList(banners);
                    newFragmentBanner.setData(banners1,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });

    }
    //VerticalViewPager的切换
    public class DefaultTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                alpha = position + 1;
            }
            //view.setAlpha(alpha);
            view.setTranslationX(view.getWidth() * -position);
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTION_CODE){
            if(resultCode==RESULT_CODE){
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("html",banners1.get(bannerIndex).getBanner_url());
                intent.putExtra("title",banners1.get(bannerIndex).getBanner_name());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
