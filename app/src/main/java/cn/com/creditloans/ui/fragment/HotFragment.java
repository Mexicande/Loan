package cn.com.creditloans.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.creditloans.R;
import cn.com.creditloans.model.Product;
import cn.com.creditloans.ui.actiivty.LoginActivity;
import cn.com.creditloans.ui.actiivty.ProductDetailActivity;
import cn.com.creditloans.ui.adapter.HotAdapter;
import cn.com.creditloans.utils.GridSpacingItemDecoration;
import cn.com.creditloans.utils.LogUtils;
import cn.com.creditloans.utils.SPUtil;
import cn.com.creditloans.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    Unbinder unbinder;
    private HotAdapter mHotAdapter;
    public HotFragment() {
        // Required empty public constructor
    }

    static HotFragment newInstance(List<Product.JrrmBean> s){
        HotFragment myFragment = new HotFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) s);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getData();
        setListener();
        return view;
    }

    private void setListener() {
        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String token = SPUtil.getString("token");
                String productId = mHotAdapter.getData().get(position).getProduct_id();
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

    }

    private void getData() {
        Bundle arguments = getArguments();
        if(arguments!=null){
            ArrayList<Product.JrrmBean> list = (ArrayList<Product.JrrmBean>) arguments.getSerializable("list");
            if (list != null) {
                mHotAdapter.addData(list);
            }
        }
    }

    private void initView() {

        mRecyler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mHotAdapter=new HotAdapter(null);
        mRecyler.setAdapter(mHotAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
