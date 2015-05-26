package net.grandpepper.caiena.grandpepper.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import net.grandpepper.caiena.grandpepper.R;

public class WebViewActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView webView;
    private SwipeRefreshLayout swipeLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        context = this;
        webView = (WebView) findViewById(R.id.webview_site);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            Toast.makeText(context,"sem conexão ativa",Toast.LENGTH_LONG).show();
        }

        webView.loadUrl("http://www.davidpedoneze.com/gp/");

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    swipeLayout.setRefreshing(false);
                }
            }
        });

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.green), getResources().getColor(R.color.yellow), getResources().getColor(R.color.orange),
                getResources().getColor(R.color.blue));
        swipeLayout.setRefreshing(true);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRefresh() {
        if (!isNetworkAvailable()) {
            Toast.makeText(context,"sem conexão ativa",Toast.LENGTH_LONG).show();
            return;
        }
        webView.reload();
    }
}
