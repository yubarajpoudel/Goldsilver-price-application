package com.yuvi.goldsilver;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class me extends Activity{
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.me);
	   String path = getIntent().getExtras().getString("link");

	   WebView browser =(WebView) findViewById(R.id.webview);
       
	   WebSettings webSettings = browser.getSettings();
	    webSettings.setJavaScriptEnabled(true);
	   browser.setWebViewClient(new WebClientClass());
	   browser.loadUrl(path);
	   
}

public class WebClientClass extends WebViewClient {
	  ProgressDialog pd = null;

	  @Override
	  public void onPageStarted(WebView view, String url, Bitmap favicon) {
	   super.onPageStarted(view, url, favicon);
	   pd = new ProgressDialog(me.this);
	   pd.setTitle("Please wait");
	   pd.setMessage("Page is loading..");
	   pd.show();
	  }

	  @Override
	  public void onPageFinished(WebView view, String url) {
	   super.onPageFinished(view, url);
	   pd.dismiss();
	  }
	 }
}
	