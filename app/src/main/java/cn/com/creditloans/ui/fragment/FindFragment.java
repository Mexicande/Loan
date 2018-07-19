package cn.com.creditloans.ui.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.creditloans.App;
import cn.com.creditloans.R;
import cn.com.creditloans.common.Api;
import cn.com.creditloans.ui.actiivty.HtmlActivity;
import cn.com.creditloans.utils.DownAPKService;
import cn.com.creditloans.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {


    @BindView(R.id.bar)
    ProgressBar bar;
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.loadUrl(Api.FIND_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (parseScheme(url)) {

                } else {

                    WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                        view.loadUrl(url);
                        return true;
                    }
                }
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                    //加载完网页进度条消失
                } else {
                    bar.setVisibility(View.VISIBLE);
                    //开始加载网页时显示进度条
                    bar.setProgress(newProgress);
                    //设置进度值
                }
            }
        });
    }
    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                ToastUtils.showToast("请安装最新版支付宝");
            }
            return true;
        } else if (url.contains("qqapi")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                startActivity(intent);
            } catch (Exception e) {
                ToastUtils.showToast("请安装最新版腾讯QQ");
            }
            return true;
        } else if (url.contains("tmast://appdetails?")) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请安装最新版应用宝", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (url.endsWith(".apk")) {
            downloadApk(url);
            return true;

        } else {
            return false;
        }

    }
    /**
     * 应用内拦截下载
     */
    private void downloadApk(String url) {

        Intent intent = new Intent(getActivity(), DownAPKService.class);
        intent.putExtra("apk_url", url);
        App.getApp().startService(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
