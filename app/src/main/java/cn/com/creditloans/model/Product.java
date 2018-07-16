package cn.com.creditloans.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/7/10.
 */

public class Product implements Serializable{

    private List<JrrmBean> jrrm;
    private List<BzsxBean> bzsx;

    public List<JrrmBean> getJrrm() {
        return jrrm;
    }

    public void setJrrm(List<JrrmBean> jrrm) {
        this.jrrm = jrrm;
    }

    public List<BzsxBean> getBzsx() {
        return bzsx;
    }

    public void setBzsx(List<BzsxBean> bzsx) {
        this.bzsx = bzsx;
    }

    public static class JrrmBean implements Serializable{
        /**
         * number : 85930
         * img : http://47.92.124.253:8086/img/productimg/1530781824565/wJ6Axj5yTZ
         * minrate : 0.01%
         * product_id : 22
         * quota : 10000元 - 50000元
         * name : 榕树贷款
         * maxrate : 0.03%
         * url : https://www.rongshu.cn/promotion/m/c/index.html?c=dk36524
         */

        private int number;
        private String img;
        private String minrate;
        private String product_id;
        private String quota;
        private String name;
        private String maxrate;
        private String url;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMinrate() {
            return minrate;
        }

        public void setMinrate(String minrate) {
            this.minrate = minrate;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getQuota() {
            return quota;
        }

        public void setQuota(String quota) {
            this.quota = quota;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaxrate() {
            return maxrate;
        }

        public void setMaxrate(String maxrate) {
            this.maxrate = maxrate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BzsxBean implements Serializable{
        /**
         * number : 99109
         * img : http://47.92.124.253:8086/img/productimg/1523590132678/ZFFJrSMqfn
         * minrate : 0.01%
         * product_id : 9
         * quota : 2000元 - 5000元
         * name : 微粒信
         * maxrate : 0.048%
         * url : http://app.welixin.net/index.php/home/login/r/sign/d165187e6ba76536021c7d23a9c7d062
         * product_title : 极速信用贷，有身份证就能贷
         */

        private int number;
        private String img;
        private String minrate;
        private String product_id;
        private String quota;
        private String name;
        private String maxrate;
        private String url;
        private String product_title;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMinrate() {
            return minrate;
        }

        public void setMinrate(String minrate) {
            this.minrate = minrate;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getQuota() {
            return quota;
        }

        public void setQuota(String quota) {
            this.quota = quota;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaxrate() {
            return maxrate;
        }

        public void setMaxrate(String maxrate) {
            this.maxrate = maxrate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }
    }
}
