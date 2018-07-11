package cn.com.creditloans.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/7/11.
 */

public class RecommendProduct implements Serializable {


    private List<Product.BzsxBean> tj;

    public List<Product.BzsxBean> getTj() {
        return tj;
    }

    public void setTj(List<Product.BzsxBean> tj) {
        this.tj = tj;
    }

}
