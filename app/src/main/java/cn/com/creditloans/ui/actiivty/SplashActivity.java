package cn.com.creditloans.ui.actiivty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import cn.com.creditloans.R;
import cn.com.creditloans.utils.ActivityUtils;
import cn.com.creditloans.utils.StatusBarUtil;


/**
 * 闪屏页
 *
 * @author apple
 */
public class SplashActivity extends AppCompatActivity {

    private SwitchHandler mHandler = new SwitchHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        StatusBarUtil.setColor(this,getResources().getColor(R.color.white));
      //  setWelcome();
        mHandler.sendEmptyMessageDelayed(1, 1000);

    }


    private static class SwitchHandler extends Handler {

        private WeakReference<SplashActivity> mWeakReference;

        SwitchHandler(SplashActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        ActivityUtils.startActivity(MainActivity.class);
                        activity.finish();
                        break;
                    default:
                        break;

                }
            }
        }
    }
  /*  private void setWelcome( ){
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            GuideActivity.launch(this);
            finish();
        }else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }

    }*/
    /**
     * startActivity屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);

    }
}
