package com.example.apicalls

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.ValueCallback
import androidx.appcompat.app.AppCompatActivity

class Graph2 : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph2)
        webView = findViewById(R.id.webview1)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Inject JavaScript to check if the graph element exists
                view?.evaluateJavascript(
                    "(function() { return document.getElementById('national-electricity-capacity') !== null; })();",
                    object : ValueCallback<String> {
                        override fun onReceiveValue(value: String?) {
                            if (value == "true") {
                                // If the element exists, get its HTML
                                view?.evaluateJavascript(
                                    "(function() { return document.getElementById('national-electricity-capacity').outerHTML; })();",
                                    object : ValueCallback<String> {
                                        override fun onReceiveValue(html: String?) {
                                            html?.let {
                                                // Load the extracted HTML in the WebView
                                                webView.loadData(it, "text/html", "UTF-8")
                                            }
                                        }
                                    }
                                )
                            } else {
                                // Handle the case where the element does not exist
                                println("Graph element not found")
                            }
                        }
                    }
                )
            }
        }
        // Load the web page
        webView.loadUrl("https://robbieandrew.github.io/india/index.html#daily_power")
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}