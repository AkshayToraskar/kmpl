package com.ak.kmpl.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ak.kmpl.R;
import com.ak.kmpl.app.AppConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FuelPricesActivity extends AppCompatActivity {

    @BindView(R.id.wvFuelPrice)
    WebView webView;
    @BindView(R.id.llNoRecord)
    LinearLayout linearLayout;
ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_prices);

        ButterKnife.bind(this);

       // pb=(ProgressBar)findViewById(R.id.progressBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fuel Price");
        if (!AppConfig.isNetworkAvailable(this, webView)) {
            webView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            webView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

            final ProgressDialog pd = ProgressDialog.show(FuelPricesActivity.this, "", "Fetching data...", true);



            webView.getSettings().setJavaScriptEnabled(false); // enable javascript

            webView.getSettings().setLoadWithOverviewMode(false);
            webView.getSettings().setUseWideViewPort(false);
            webView.getSettings().setBuiltInZoomControls(false);


            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(FuelPricesActivity.this, description, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    pd.show();
                }


                @Override
                public void onPageFinished(WebView view, String url) {
                    pd.dismiss();

                    String webUrl = webView.getUrl();

                }



        });

            webView.loadUrl("http://kmpl.ml/getFuelPrice.php");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:

                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}
