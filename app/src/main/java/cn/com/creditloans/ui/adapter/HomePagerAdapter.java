package cn.com.creditloans.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.creditloans.App;
import cn.com.creditloans.R;
import cn.com.creditloans.model.Product;
import cn.com.creditloans.view.vertical.BaseBannerAdapter;
import cn.com.creditloans.view.vertical.VerticalBannerView;

/**
 *
 * @author apple
 * @date 2018/7/11
 */

public class HomePagerAdapter extends PagerAdapter {
    private List<Product.BzsxBean> mDatas;

    public HomePagerAdapter(List<Product.BzsxBean> datas) {
        this.mDatas=datas;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(App.getApp()).inflate(R.layout.home_page_item,null);
        TextView name = (TextView) view.findViewById(R.id.show_loan_tv1);
        TextView tePhone = (TextView) view.findViewById(R.id.phone);
        TextView money = (TextView) view.findViewById(R.id.money);
        ImageView logo  = (ImageView) view.findViewById(R.id.show_loan_iv);
        name.setText(mDatas.get(position).getName());
        String[] phone = {"3", "5", "8", "7"};
        String phone1 = "1" + phone[new Random().nextInt(4)] + new Random().nextInt(10) + "****" + (new Random().nextInt(8999) + 1000);
        tePhone.setText(phone1);
        String number = (new Random().nextInt(10) + 1) * 1000 + "";
        money.setText(number);

        RequestOptions requestOptions=new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(App.getApp())
                .load(mDatas.get(position).getImg())
                .apply(requestOptions)
                .into(logo);
        // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
