package cn.com.creditloans;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.MobclickAgent;

import cn.com.creditloans.common.Contacts;

/**
 * Created by apple on 2018/6/29.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();


    }

    private void init() {
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, Contacts.UMENG_KEY
                ,channel));
        HttpParams params=new HttpParams();
        String name = getString(R.string.app_name);
        params.put("name", name);
        params.put("market", channel);
        OkGo.getInstance().init(this)
                .addCommonParams(params);
    }

    public static App getApp(){
        return instance;
    }

}
