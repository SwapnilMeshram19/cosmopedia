package com.example.cosmopedia // TODO: replace with your actual package name

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var bannerAd: AdView
    private val SITE_URL = "https://<your-site>.netlify.app/index.html" // TODO: your real Netlify URL
    private val BANNER_UNIT_ID = "ca-app-pub-3940256099942544/9214589741" // Google test banner unit
    // Swap to your real banner once test ads work: ca-app-pub-1294432140413899/7378045065

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // needs a layout with webview (id: webview) + ad container (id: ad_container)

        MobileAds.initialize(this)

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true // required — the app persists state via localStorage
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(
            AdBridge(this) { js -> runOnUiThread { webView.evaluateJavascript(js, null) } },
            "CosmoAdsNative"
        )
        webView.loadUrl(SITE_URL)

        bannerAd = AdView(this)
        bannerAd.setAdSize(AdSize.BANNER)
        bannerAd.adUnitId = BANNER_UNIT_ID
        findViewById<android.widget.LinearLayout>(R.id.ad_container).addView(bannerAd)
        bannerAd.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroy() {
        bannerAd.destroy()
        super.onDestroy()
    }
}
