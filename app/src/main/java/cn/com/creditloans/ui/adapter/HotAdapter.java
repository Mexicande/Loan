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

/**
 *
 * @author apple
 * @date 2018/7/17
 * 热门推荐
 */


public class HotAdapter extends BaseQuickAdapter<Product.JrrmBean, BaseViewHolder> {

    public HotAdapter( @Nullable List<Product.JrrmBean> data) {
        super(R.layout.hot_product_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product.JrrmBean item) {
        String quota = item.getQuota();
        int indexOf = quota.indexOf("-");
        String substring = quota.substring(indexOf+1, quota.length());
        helper.setText(R.id.name,item.getName())
                .setText(R.id.quota,substring)
                .setText(R.id.minrate,item.getMinrate());
        RequestOptions requestOptions=new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(App.getApp())
                .load(item.getImg())
                .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.logo));
        int adapterPosition = helper.getAdapterPosition();
        if(adapterPosition%2==0){
            helper.setVisible(R.id.lion,true);
        }else {
            helper.setVisible(R.id.lion,false);
        }
    }
}
