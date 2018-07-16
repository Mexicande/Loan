package cn.com.creditloans.utils;

import android.content.Context;
import android.widget.Toast;

import cn.com.creditloans.App;

/**
 * Created by apple on 2017/4/6.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast( String message) {
        if (toast == null) {
            toast = Toast.makeText(App.getApp(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
