package cn.com.creditloans.model;

import java.io.Serializable;

/**
 * Created by apple on 2018/7/10.
 */

public class Banner implements Serializable{

    /**
     * banner_logo_url : http://47.92.124.253:8086/img/bannerimg/1525926648782/KGqXiW7wdT
     * banner_url : http://www.apyl520.com/download/?channel=a3kwMDE=
     * banner_name : 棋牌游戏
     */

    private String banner_logo_url;
    private String banner_url;
    private String banner_name;

    public String getBanner_logo_url() {
        return banner_logo_url;
    }

    public void setBanner_logo_url(String banner_logo_url) {
        this.banner_logo_url = banner_logo_url;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }
}
