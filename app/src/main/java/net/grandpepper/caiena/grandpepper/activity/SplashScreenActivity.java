package net.grandpepper.caiena.grandpepper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import net.grandpepper.caiena.grandpepper.R;

public class SplashScreenActivity extends Activity {

    private WebView webView;
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        webView = (WebView) findViewById(R.id.webview_splash);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadDataWithBaseURL("file:///android_res/drawable/", "<img align='middle' src='gif_carregando.gif' width='100%' />", "text/html", "utf-8", null);

        final WebView webViewSite = new WebView(this);
        webViewSite.loadUrl("http://www.davidpedoneze.com/gp/");
//        webViewSite.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress == 100) {
//                    Intent mainIntent = new Intent(SplashScreenActivity.this,MyActivity.class);
//                    SplashScreenActivity.this.startActivity(mainIntent);
//                    SplashScreenActivity.this.finish();
//                }
//            }
//        });
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this,MyActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
