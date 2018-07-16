package cn.com.creditloans.common;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.com.creditloans.App;
import cn.com.creditloans.R;
import cn.com.creditloans.intr.OnRequestDataListener;
import cn.com.creditloans.utils.SPUtil;


/**
 * Created by apple on 2018/4/13.
 */

public class ApiService {
    /**
     * @param listener
     * banner
     */
    public static void GET_SERVICE(String url,JSONObject jsonObject, final OnRequestDataListener listener) {
        newExcuteJsonPost(url,jsonObject,listener);
    }

    private static void newExcuteJsonPost(String url, JSONObject jsonObject, final OnRequestDataListener listener){
        final String netError = App.getApp().getString(R.string.net_error);
        String token = SPUtil.getString("token");
        OkGo.<String>post(url)
                .tag(App.getApp())
                .headers("x-application",token)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body());
                                int code = jsonObject.getInt("code");
                                if(code==200){
                                    listener.requestSuccess(code, jsonObject);
                                }else {
                                    listener.requestFailure(code, jsonObject.getString("mgs"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                           listener.requestFailure(-1, netError);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                       listener.requestFailure(-1, netError);

                    }
                });


    }

}
