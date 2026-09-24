# AdMob native bridge for Cosmopedia TWA

Your web app (`index.html`) now calls `window.CosmoAds.showRewarded(kind, onReward)` when the user taps a
hint/skip/wallpaper-unlock button, and falls back to a simulated countdown ad if that bridge doesn't exist
(so the web preview and un-wrapped PWA still work). These files add the real bridge inside the Android
project that Bubblewrap/PWABuilder generates for the TWA.

## 1. Generate the base Android project

```
npm i -g @bubblewrap/cli
bubblewrap init --manifest https://<your-site>/manifest.json
```//
This creates an Android Studio project (Kotlin, Gradle). Open it in Android Studio for the next steps.

## 2. Add the Google Mobile Ads SDK

In `app/build.gradle`, inside `dependencies { ... }`, add:

```
implementation 'com.google.android.gms:play-services-ads:23.2.0'
```

## 3. Declare your App ID

In `app/src/main/AndroidManifest.xml`, inside `<application>`, add:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-1294432140413899~2920845178"/>
```

## 4. Add AdBridge.kt

Copy `AdBridge.kt` (in this folder) into `app/src/main/java/<your/package/name>/`. Update the `packageName` line
at the top to match your actual package.

## 5. Wire the bridge into the TWA's WebView

Bubblewrap's generated `LauncherActivity` uses a Trusted Web Activity, which does NOT expose its WebView to
your code directly — TWAs render in the browser process, not an in-app WebView. To use a JS bridge like this,
you need to swap the pure-TWA launcher for a WebView-based Activity instead. Copy `MainActivity.kt` (in this
folder) as a replacement launcher Activity: it loads your site in a real WebView, registers `AdBridge` as
`window.CosmoAds`, and shows/hides a real AdMob banner + rewarded ad.

Update `AndroidManifest.xml`'s `<activity>` entry to point at this new `MainActivity` instead of the generated
TWA launcher, and add the internet permission:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

Note: switching from TWA to a plain WebView means you lose Chrome's TWA perf/PWA benefits (no more
"looks like a real browser tab to Play" trust signal) — but it's the standard trade-off for native ad SDKs in
a wrapped web app. This is genuinely native Android work; treat these files as a working starting point, not
a drop-in-and-done — test on a real device before shipping.

## 6. Rebuild and test

```
./gradlew assembleDebug
```
Install the debug APK on a device, open the app, confirm the banner shows (use AdMob **test ads** first — see
the `TEST_*` IDs left as comments in AdBridge.kt — swap to your real IDs only once test ads render correctly).
