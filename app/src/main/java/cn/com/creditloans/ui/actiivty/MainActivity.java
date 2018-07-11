package cn.com.creditloans.ui.actiivty;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.intr.SelectListener;
import cn.com.creditloans.ui.adapter.MyViewPagerAdapter;
import cn.com.creditloans.ui.adapter.NoTouchViewPager;
import cn.com.creditloans.ui.fragment.CenterFragment;
import cn.com.creditloans.ui.fragment.FindFragment;
import cn.com.creditloans.ui.fragment.HomeFragment;
import cn.com.creditloans.ui.fragment.LotteryFragment;
import cn.com.creditloans.utils.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

/**
 * @author apple
 */
public class MainActivity extends BaseActivity implements  SelectListener{

    @BindView(R.id.viewPager)
    NoTouchViewPager viewPager;
    @BindView(R.id.table)
    PageBottomTabLayout table;
    private ArrayList<Fragment> mFragments;
    private MyViewPagerAdapter myViewPagerAdapter;
    private NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 114,null);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new LotteryFragment());
        mFragments.add(new FindFragment());
        mFragments.add(new CenterFragment());

        navigationController = table.custom()
                .addItem(newItem(R.mipmap.iv_home, R.mipmap.iv_home_select, "首页"))
                .addItem(newItem(R.mipmap.iv_loan, R.mipmap.iv_loan_select, "贷款大全"))
                .addItem(newItem(R.mipmap.iv_find, R.mipmap.iv_find_select, "发现"))
                .addItem(newItem(R.mipmap.iv_center, R.mipmap.iv_center_select, "我的"))
                .build();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(myViewPagerAdapter);
        navigationController.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(mFragments.size());

    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.theme_color));
        return normalItemView;
    }

    @Override
    public void selectTab(int index) {
        navigationController.setSelect(index);
    }
}
