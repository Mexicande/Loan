package cn.com.creditloans.model;

/**
 * Created by apple on 2018/7/13.
 */

public class ProductDetail {

    /**
     * number : 77571
     * img : http://47.92.124.253:8086/img/productimg/1524909442443/8Zhy0wJbLW
     * face_people : 面向人群
     * product_introduce : 1、18-55周岁（学生除外）
     2、芝麻分600分以上
     3、手机实名6个月以上
     4、客服电话：400-222-444
     * minrate : 0.01%
     * product_id : 2
     * quota : 500元 - 2991元
     * product_logo_url : http://47.92.124.253:8086/img/productimg/1524909442443/8Zhy0wJbLW
     * name : 51应急贷
     * maxrate : 0.05%
     * application_conditions : 申请条件
     * url : http://t.cn/Rrdm9E5
     */

    private int number;
    private String img;
    private String face_people;
    private String product_introduce;
    private String minrate;
    private int product_id;
    private String quota;
    private String product_logo_url;
    private String name;
    private String maxrate;
    private String application_conditions;
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

    public String getFace_people() {
        return face_people;
    }

    public void setFace_people(String face_people) {
        this.face_people = face_people;
    }

    public String getProduct_introduce() {
        return product_introduce;
    }

    public void setProduct_introduce(String product_introduce) {
        this.product_introduce = product_introduce;
    }

    public String getMinrate() {
        return minrate;
    }

    public void setMinrate(String minrate) {
        this.minrate = minrate;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getProduct_logo_url() {
        return product_logo_url;
    }

    public void setProduct_logo_url(String product_logo_url) {
        this.product_logo_url = product_logo_url;
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

    public String getApplication_conditions() {
        return application_conditions;
    }

    public void setApplication_conditions(String application_conditions) {
        this.application_conditions = application_conditions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
