package cn.com.creditloans.intr;

import org.json.JSONObject;

/**
 * Created by apple on 2018/4/13.
 */

public interface OnRequestDataListener {

    void requestSuccess(int code, JSONObject json);

    void requestFailure(int code, String msg);
}
