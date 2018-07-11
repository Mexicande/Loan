package cn.com.creditloans.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/7/11.
 */

public class QuickProduct implements Serializable {

    private List<Product.BzsxBean> js;

    public List<Product.BzsxBean> getJs() {
        return js;
    }

    public void setJs(List<Product.BzsxBean> js) {
        this.js = js;
    }
}
