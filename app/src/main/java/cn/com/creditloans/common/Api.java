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

    interface  LOGIN{
        /** 新or老用户**/
        String isOldUser=HOST+"quick/isOldUser";
        /** 验证码获取**/
        String CODE=HOST+"sms/getcode";
        /** 验证码效验**/
        String  CHECKCODE=HOST+"sms/checkCode";
        /** 登陆**/
        String  QUICKLOGIN=HOST+"quick/login";
        /** 完善信息**/
        String IDENTITY =HOST+"quick/addBasicIdentity";

    }

    interface  STATUS{
        /** 状态**/
        String getStatus=HOST+"vest/getStatus";
        /**版本更新**/
        String UPDATE=HOST+"vest/version";
    }


}
