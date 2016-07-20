package com.masbhe.tiket.pjka.android;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class SearchTicket extends AppCompatActivity {
    private String _alamat;
    private String _destin;
    private String _email;
    private String _kereta;
    private String _ktp;
    private String _nama;
    private String _origin;
    private String _telp;
    private String _tgl;
    private String js;
    private Context mapp;
    private String pilih;
    private WebView webView;
    private String server;

    class C02141 implements ValueCallback<String> {
        C02141() {
        }

        public void onReceiveValue(String s) {
            Log.d("Debug", "====================>" + s);
        }
    }

    class C02152 extends WebViewClient {
        C02152() {
        }

        public void onPageFinished(WebView wv, String url) {
            super.onPageFinished(wv, url);
            wv.loadUrl("javascript:var leng = $(\"input[name='train_name'][value='" + SearchTicket.this._kereta + "']\").length;JSInterface.setElement(leng);");
        }
    }

    class C02173 extends WebViewClient {

        class C02161 implements ValueCallback<String> {
            C02161() {
            }

            public void onReceiveValue(String s) {
                Log.d("Debug", "====================>" + s);
            }
        }

        C02173() {
        }

        public void onPageFinished(WebView wv, String url) {
            super.onPageFinished(wv, url);
            if (VERSION.SDK_INT >= 19) {
                wv.evaluateJavascript(SearchTicket.this.js, new C02161());
            } else {
                wv.loadUrl(SearchTicket.this.js);
            }
        }
    }

    class JSInterface {

        class C02181 extends WebViewClient {
            C02181() {
            }

            public void onPageFinished(WebView wv, String url) {
                super.onPageFinished(wv, url);
                wv.loadUrl("javascript:var lanj = $(\"input[name='persetujuan'][type='checkbox']\").click();$(\"input[name='booking'][type='submit'][value='Lanjutkan']\").click();JSInterface.lanjutkanNext(1);");
            }
        }

        class C02192 extends WebViewClient {
            C02192() {
            }

            public void onPageFinished(WebView wv, String url) {
                super.onPageFinished(wv, url);
                wv.loadUrl("javascript:var lanex= document.getElementById(\"txt_first_name_adult_1\").value=\"" + SearchTicket.this._nama + "\";" + "document.getElementById(\"txt_passport_1\").value=\"" + SearchTicket.this._ktp + "\";" + "document.getElementById(\"txt_contact_first_name\").value=\"" + SearchTicket.this._nama + "\";" + "document.getElementById(\"txt_contact_email\").value=\"" + SearchTicket.this._email + "\";" + "document.getElementById(\"txt_contact_phone\").value=\"" + SearchTicket.this._telp + "\";" + "document.getElementById(\"txt_contact_home_address\").value=\"" + SearchTicket.this._alamat + "\";" + "JSInterface.selesai(1);");
                Toast.makeText(SearchTicket.this, "Silakan Input Captcha.........!!!", Toast.LENGTH_SHORT).show();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }

        class C02203 extends WebViewClient {
            C02203() {
            }

            public void onPageFinished(WebView wv, String url) {
                super.onPageFinished(wv, url);
                Toast.makeText(SearchTicket.this,
                        "Monggo untuk melanjutkan secara manual ke proses selanjutnya sampai pembayaran, Semoga Selamat sampai tujuan. Terimakasih",
                        Toast.LENGTH_SHORT).show();
            }
        }

        JSInterface() {
        }

        @JavascriptInterface
        public void setElement(int leng) {
            if (leng > 0) {
                SearchTicket.this.webView.loadUrl("javascript:var lanj = $(\"input[name='persetujuan'][type='checkbox']\").length;JSInterface.lanjutkan(lanj);");
                return;
            }
            SearchTicket.this.webView.loadUrl(SearchTicket.this.js);
            Log.i("Loop: ", "========> Tiket Belum Ketemu");
        }

        @JavascriptInterface
        public void lanjutkan(int leng) {
            SearchTicket.this.webView.setWebViewClient(new C02181());
            SearchTicket.this.webView.loadUrl(SearchTicket.this.pilih);
        }

        @JavascriptInterface
        public void lanjutkanNext(int param) {
            SearchTicket.this.webView.setWebViewClient(new C02192());
        }

        @JavascriptInterface
        public void selesai(int param) {
            SearchTicket.this.webView.setWebViewClient(new C02203());
        }
    }

    public SearchTicket() {
        this.js = BuildConfig.FLAVOR;
        this.pilih = BuildConfig.FLAVOR;
        this.mapp = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_tiket);
        retreiveDataSkoNgarep();
        searchV2();
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

    public void executeJavascript(WebView wv, String script) {
        if (VERSION.SDK_INT >= 19) {
            wv.evaluateJavascript(script, new C02141());
        } else {
            wv.loadUrl(script);
        }
    }

    public void searchV2() {
        this.webView = (WebView) findViewById(R.id.webView);
        this.webView.addJavascriptInterface(new JSInterface(), "JSInterface");
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.loadUrl(server);
        this.webView.setWebChromeClient(new WebChromeClient());
        this.js = "javascript:var x = document.getElementsByName('tanggal')[0].selectedIndex = " + this._tgl + ";" + "document.getElementsByName('origination')[0].value = \"" + this._origin + "\";" + "document.getElementsByName('destination')[0].value = \"" + this._destin + "\";" + "$(\"select[name='adult']\").val(\"1\");" + "$(\"input[type='submit'][name='Submit'][value='Tampilkan']\").click();";
        this.pilih = "javascript: var tmp = 5000000; var buto=\"ButoIjo\"; $(\"input[name='train_name'][value='" + this._kereta + "']\").each(function(){ " + "   var farel = $(this).parent().find(\"input[name='fare_adult']\");" + "   if(farel.val()<tmp){tmp=farel.val();buto=$(this).parent().attr('id');}                " + "}); $('#'+buto).find(\"input[type='submit']\").click();";
        this.webView.setWebViewClient(new C02152());
    }

    public void search() {
        this.webView = (WebView) findViewById(R.id.webView);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.loadUrl(this.server);
        this.js = "javascript:var x = document.getElementsByName('tanggal')[0].selectedIndex = 88;document.getElementsByName('origination')[0].value = \"PSE#PASAR SENEN\";document.getElementsByName('destination')[0].value = \"YK#YOGYAKARTA\";$(\"select[name='adult']\").val(\"1\");$(\"input[type='submit'][name='Submit'][value='Tampilkan']\").click();";
        this.webView.loadUrl(this.js);
        this.webView.setWebViewClient(new C02173());
    }
}
