package cn.com.creditloans.ui.actiivty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.creditloans.R;
import cn.com.creditloans.base.BaseActivity;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.common.ApiService;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.model.ProductDetail;

/**
 * @author apple
 *         <p>
 *         产品详情
 */
public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.product_introduce)
    TextView productIntroduce;
    @BindView(R.id.conditions)
    TextView conditions;
    private ProductDetail productDetail;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        String id = getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            getData(id);
        }
    }



    private void initView() {
        layoutTopBack.setVisibility(View.VISIBLE);
    }
    private void getData(String id) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("product_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.STATUS.GETPRODUCT_DETAIL, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    Gson gson=new Gson();
                    productDetail = gson.fromJson(data, ProductDetail.class);
                    fillDetail(productDetail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    private void fillDetail(ProductDetail productDetail) {
        tvTitle.setText(productDetail.getName());
        productIntroduce.setText(productDetail.getProduct_introduce());
        conditions.setText(productDetail.getApplication_conditions());
        RequestOptions requestOptions=new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(productDetail.getImg())
                .apply(requestOptions)
                .into(ivLogo);

    }



    @OnClick({R.id.layout_top_back, R.id.apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.apply:
                if(productDetail!=null&&!TextUtils.isEmpty(productDetail.getUrl())){
                    Intent intent=new Intent(this,HtmlActivity.class);
                    intent.putExtra("html",productDetail.getUrl());
                    intent.putExtra("title",productDetail.getName());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
