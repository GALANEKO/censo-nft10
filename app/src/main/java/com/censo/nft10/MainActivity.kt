package com.censo.nft10
import android.content.*
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.loadUrl("file:///android_asset/index.html")
        val filter = IntentFilter()
        filter.addAction("nlscan.action.SCANNER_RESULT")
        registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(c: Context?, i: Intent?) {
                val code = i?.getStringExtra("SCAN_BARCODE1") ?: i?.getStringExtra("SCAN_BARCODE") ?: ""
                if(code.isNotEmpty()){
                    webView.evaluateJavascript("window.onScannerResult && window.onScannerResult('$code')", null)
                }
            }
        }, filter)
    }
}
