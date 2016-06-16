package com.example.weizhang.activityandfragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MyWebViewFragment extends Fragment {

    public static final String TAG = MyWebViewFragment.class.getSimpleName();
    public static final String ACTIVITY_URL = "https://developer.android.com/reference/android/app/Activity.html";

    public static final String TITLE = "title";

    public static final String URL = "url";

    private WebView webView;
    private TextView webViewTitle;

    private String title;
    private String url;

    private Bundle webViewState;

    public static MyWebViewFragment createInstance(Bundle args) {
        MyWebViewFragment myWebViewFragment = new MyWebViewFragment();
        myWebViewFragment.setArguments(args);
        return myWebViewFragment;
    }

    public static MyWebViewFragment createInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(URL, url);
        return createInstance(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_fragment_layout, container, false);
        webView = (WebView)view.findViewById(R.id.webview);
        webViewTitle = (TextView)view.findViewById(R.id.webview_title);

        initArguments(getArguments());
        initWebView();

        if (webViewState != null) {
            webView.restoreState(webViewState);
        } else if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            loadUrl(url);
        }
        webViewTitle.setText(title);
        return view;
    }

    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        // use webViewState to save WebView state
        webViewState = new Bundle();
        webView.saveState(webViewState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) {
            webView.saveState(outState);
        }
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private void initArguments(Bundle args) {
        if (args == null) {
            args = new Bundle();
        }

        title = args.getString(TITLE);
        url = args.getString(URL);
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("internal")) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(this.getContext().getCacheDir().getAbsolutePath());
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (ACTIVITY_URL.equals(url)) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, MyWebViewFragment.createInstance("Fragment TWO", url))
                            .addToBackStack(MyWebViewFragment.TAG)
                            .commit();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    private void loadUrl(String url) {
        webView.loadUrl(url);
    }
}
