package cn.com.creditloans.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.creditloans.R;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.common.ApiService;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.model.RecommendProduct;
import cn.com.creditloans.ui.actiivty.LoginActivity;
import cn.com.creditloans.ui.actiivty.ProductDetailActivity;
import cn.com.creditloans.ui.adapter.ProductAdapter;
import cn.com.creditloans.utils.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         推荐
 */
public class RecommendFragment extends Fragment {


    @BindView(R.id.fragment_home_rv)
    RecyclerView mFragmentHomeRv;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    private ProductAdapter mProductAdapter;
    private String productId;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getData();
        setListener();
        return view;
    }

    private void initView() {
        mProductAdapter = new ProductAdapter(null);
        mFragmentHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentHomeRv.setAdapter(mProductAdapter);
        View inflate = View.inflate(getActivity(), R.layout.fragment_header, null);
        mProductAdapter.addHeaderView(inflate);
    }

    private void getData() {
        ApiService.GET_SERVICE(Api.RECOMMEND_LIST, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {

                try {
                    String data = json.getString("data");
                    Gson gson = new Gson();
                    RecommendProduct quickProduct = gson.fromJson(data, RecommendProduct.class);
                    mProductAdapter.setNewData(quickProduct.getTj());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
