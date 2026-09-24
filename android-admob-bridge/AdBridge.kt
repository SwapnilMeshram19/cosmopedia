package com.example.cosmopedia // TODO: replace with your actual package name

import android.app.Activity
import android.webkit.JavascriptInterface
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError

/**
 * Exposed to the WebView as window.CosmoAdsNative (index.html wraps it as window.CosmoAds
 * with a proper JS callback — see the shim added near the top of index.html's script).
 */
class AdBridge(private val activity: Activity, private val runJs: (String) -> Unit) {

    // Swap to your real unit once test ads work: ca-app-pub-1294432140413899/5243845473
    private val REWARD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917" // Google test rewarded unit

    @JavascriptInterface
    fun showRewarded(kind: String, callbackId: String) {
        activity.runOnUiThread {
            val request = AdRequest.Builder().build()
            RewardedAd.load(activity, REWARD_UNIT_ID, request, object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {}
                    ad.show(activity) { runJs("window.__cosmoAdCb && window.__cosmoAdCb('$callbackId')") }
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    // Ad failed — still grant the reward so the user isn't blocked.
                    runJs("window.__cosmoAdCb && window.__cosmoAdCb('$callbackId')")
                }
            })
        }
    }
}
