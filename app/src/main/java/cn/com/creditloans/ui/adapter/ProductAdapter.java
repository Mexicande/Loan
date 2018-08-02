package cn.com.creditloans.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.creditloans.App;
import cn.com.creditloans.R;
import cn.com.creditloans.model.Product;
import cn.com.creditloans.view.GlideRoundTransform;

/**
 * - @Author:
 * - @Time:  2018/5/23 下午6:10
 * - @Email whynightcode@gmail.com
 */
public class ProductAdapter extends BaseQuickAdapter<Product.BzsxBean, BaseViewHolder> {

    public ProductAdapter(@Nullable List<Product.BzsxBean> data) {
        super(R.layout.new_product_vvp_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product.BzsxBean item) {

        String quota = item.getQuota();
        int indexOf = quota.indexOf("-");
        String substring = quota.substring(indexOf+1, quota.length());
        helper.setText(R.id.name,item.getName())
                .setText(R.id.product_title,item.getProduct_title())
                .setText(R.id.minrate,item.getMinrate())
                .setText(R.id.quota,substring);
        RequestOptions requestOptions=new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(mContext,10))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(App.getApp())
                .load(item.getImg())
                .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.show_loan_iv));

    }
}
