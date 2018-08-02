package cn.com.creditloans.common;

/**
 * Created by apple on 2018/5/18.
 */

public interface Api {

     String HOST="http://www.yqatm.com/";

    /**banner **/
    String BANNER=HOST+"ios/olap/getbanner";
    /**产品 **/
     String HOME_LIST=HOST+"ios/olap/firstpage";
    /**推荐 **/
    String RECOMMEND_LIST=HOST+"ios/olap/producttj";
    /** 急速**/
    String QUICK_LIST=HOST+"ios/olap/productjs";
    String FIND_URL="http://kjdwebv2.kjdai.net/loginchannelV3.html?channel=xiaoxiannv&flavor=xiaoxiannv";
    interface  LOGIN{
        /** 验证码获取**/
        String GETCODE=HOST+"ios/olap/getLoginCode";
        /** 完善信息**/
        String REGISTER =HOST+"ios/olap/register";
        /**登陆**/
        String LOGIN=HOST+"ios/olap/login";
    }

    interface  STATUS{
        /** 产品详情**/
        String GETPRODUCT_DETAIL=HOST+"ios/olap/productDetail";
        /**版本更新**/
        String UPDATE=HOST+"vest/version";

        /** 公众号名称 **/
        String WEICHAT=HOST+"ios/olap/getPublicNumber";
    }


}
