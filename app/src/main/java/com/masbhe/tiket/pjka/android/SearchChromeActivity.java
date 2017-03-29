package com.masbhe.tiket.pjka.android;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class SearchChromeActivity extends AppCompatActivity {
    private String _alamat;
    private String _destin;
    private String _email;
    private String _kereta;
    private String _ktp;
    private String _nama;
    private String _origin;
    private String _telp;
    private String _tgl;
    private int count;
    private String firsSearchScript;
    private String ngisiData;
    private String pilih;
    private WebView webView;
    private String server;

    class C02031 implements Runnable {

        class C02021 extends WebViewClient {
            C02021() {
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                SearchChromeActivity.this.loadJavascript("var leng = $(\"input[name='train_name'][value='" + SearchChromeActivity.this._kereta + "']\").length;JavascriptInjector.loopAtauLanjut(leng);", view);
            }
        }

        C02031() {
        }

        public void run() {
            SearchChromeActivity.this.webView.setWebViewClient(new C02021());
        }
    }

    class C02042 implements ValueCallback<String> {
        C02042() {
        }

        @TargetApi(11)
        public void onReceiveValue(String s) {
        }
    }

    class JavascriptInjector {

        class C02051 implements Runnable {
            C02051() {
            }

            public void run() {
                SearchChromeActivity.this.loadJavascript("JavascriptInjector.lanjutkan('ff');", SearchChromeActivity.this.webView);
            }
        }

        class C02062 implements Runnable {
            C02062() {
            }

            public void run() {
                Log.i("Loop: ", "========> Pencarian ke: " + SearchChromeActivity.this.count);
                SearchChromeActivity.this.loadJavascript(SearchChromeActivity.this.firsSearchScript, SearchChromeActivity.this.webView);
                if (SearchChromeActivity.this.count > 0) {
                    Toast.makeText(SearchChromeActivity.this, "Loop: Pencarian ke: " + SearchChromeActivity.this.count,
                            Toast.LENGTH_SHORT).show();
                }
                SearchChromeActivity.this.count = SearchChromeActivity.this.count + 1;
            }
        }

        class C02083 implements Runnable {

            class C02071 extends WebViewClient {
                C02071() {
                }

                public void onPageFinished(WebView wv, String url) {
                    super.onPageFinished(wv, url);
                    SearchChromeActivity.this.loadJavascript("var lanj = $(\"input[name='persetujuan'][type='checkbox']\").click();$(\"input[name='booking'][type='submit'][value='Lanjutkan']\").click();JavascriptInjector.lanjutkanNext(1);", wv);
                }
            }

            C02083() {
            }

            public void run() {
                SearchChromeActivity.this.webView.setWebViewClient(new C02071());
            }
        }

        class C02094 implements Runnable {
            C02094() {
            }

            public void run() {
                SearchChromeActivity.this.loadJavascript(SearchChromeActivity.this.pilih, SearchChromeActivity.this.webView);
            }
        }

        class C02115 implements Runnable {

            class C02101 extends WebViewClient {
                C02101() {
                }

                public void onPageFinished(WebView wv, String url) {
                    super.onPageFinished(wv, url);
                    SearchChromeActivity.this.loadJavascript(SearchChromeActivity.this.ngisiData, wv);
                    Toast.makeText(SearchChromeActivity.this, "Silakan Input Captcha.........!!!",
                            Toast.LENGTH_SHORT).show();
                }

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            }

            C02115() {
            }

            public void run() {
                SearchChromeActivity.this.webView.setWebViewClient(new C02101());
            }
        }

        class C02136 implements Runnable {

            class C02121 extends WebViewClient {
                C02121() {
                }

                public void onPageFinished(WebView wv, String url) {
                    super.onPageFinished(wv, url);
                    Toast.makeText(SearchChromeActivity.this, "Monggo untuk melanjutkan secara manual ke proses selanjutnya sampai pembayaran, Semoga Selamat sampai tujuan. Terimakasih",
                            Toast.LENGTH_SHORT).show();
                }
            }

            C02136() {
            }

            public void run() {
                SearchChromeActivity.this.webView.setWebViewClient(new C02121());
            }
        }

        JavascriptInjector() {
        }

        @JavascriptInterface
        public void loopAtauLanjut(int leng) {
            if (leng > 0) {
                SearchChromeActivity.this.runOnUiThread(new C02051());
            } else {
                SearchChromeActivity.this.runOnUiThread(new C02062());
            }
        }

        @JavascriptInterface
        public void lanjutkan(int leng) {
            SearchChromeActivity.this.runOnUiThread(new C02083());
            SearchChromeActivity.this.runOnUiThread(new C02094());
        }

        @JavascriptInterface
        public void lanjutkanNext(int param) {
            SearchChromeActivity.this.runOnUiThread(new C02115());
        }

        @JavascriptInterface
        public void selesai(int param) {
            SearchChromeActivity.this.runOnUiThread(new C02136());
        }
    }

    public SearchChromeActivity() {
        this.firsSearchScript = BuildConfig.FLAVOR;
        this.pilih = BuildConfig.FLAVOR;
        this.ngisiData = BuildConfig.FLAVOR;
        this.count = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_search_chrome);
        retreiveDataSkoNgarep();
        this.webView = (WebView) findViewById(R.id.webView2);
        setUpWebViewDefaults(this.webView);
        this.webView.loadUrl(this.server);
        initKumpulanScript();
        runOnUiThread(new C02031());
        Toast.makeText(this, "Mohon Tunggu Sejenak, Jangan tekan2 dulu ya :D",
                Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Search pertama agak lama",
                Toast.LENGTH_SHORT).show();
    }

    public void initKumpulanScript() {
        this.firsSearchScript = "document.getElementsByName('tanggal')[0].selectedIndex = " + this._tgl + ";" + "document.getElementsByName('origination')[0].value = \"" + this._origin + "\";" + "document.getElementsByName('destination')[0].value = \"" + this._destin + "\";" + "$(\"select[name='adult']\").val(\"1\");" + "$(\"input[type='submit'][name='Submit'][value='Tampilkan']\").click();";
        this.pilih = "var tmp = 5000000; var buto=\"ButoIjo\"; $(\"input[name='train_name'][value='" + this._kereta + "']\").each(function(){ " + "   var farel = $(this).parent().find(\"input[name='fare_adult']\");" + "   if(farel.val()<tmp){tmp=farel.val();buto=$(this).parent().attr('id');}                " + "}); console.log(buto);$('#'+buto).find(\"input[type='submit']\").click();";
        this.ngisiData = "var lanex= document.getElementById(\"txt_first_name_adult_1\").value=\"" + this._nama + "\";" + "document.getElementById(\"txt_passport_1\").value=\"" + this._ktp + "\";" + "document.getElementById(\"txt_contact_first_name\").value=\"" + this._nama + "\";" + "document.getElementById(\"txt_contact_email\").value=\"" + this._email + "\";" + "document.getElementById(\"txt_contact_phone\").value=\"" + this._telp + "\";" + "document.getElementById(\"txt_contact_home_address\").value=\"" + this._alamat + "\";" + "JavascriptInjector.selesai(1);";
    }

    public void loadJavascript(String javascript, WebView wv) {
        if (VERSION.SDK_INT >= 19) {
            wv.evaluateJavascript(javascript, new C02042());
        }
    }

    @TargetApi(11)
    private void setUpWebViewDefaults(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JavascriptInjector(), "JavascriptInjector");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        if (VERSION.SDK_INT > 11) {
            settings.setDisplayZoomControls(false);
        }
        if (VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    public void retreiveDataSkoNgarep() {
        this._origin = getIntent().getStringExtra("origin");
        this._destin = getIntent().getStringExtra("destination");
        this._tgl = getIntent().getStringExtra("tgl");
        this._kereta = getIntent().getStringExtra("kereta");
        this._nama = getIntent().getStringExtra("nama");
        this._ktp = getIntent().getStringExtra("noktp");
        this._telp = getIntent().getStringExtra("notelp");
        this._email = getIntent().getStringExtra("email");
        this._alamat = getIntent().getStringExtra("alamat");
        this.server = getIntent().getStringExtra("server");
    }

    public void onBackPressed() {
        if (this.webView != null) {
            this.webView.loadUrl("about:blank");
        }
        super.onBackPressed();
    }
}
