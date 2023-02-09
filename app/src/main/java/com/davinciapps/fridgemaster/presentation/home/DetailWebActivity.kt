package com.davinciapps.fridgemaster.presentation.home

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.davinciapps.fridgemaster.databinding.ActivityDetailWebBinding


class DetailWebActivity : AppCompatActivity() {
    private val TAG = DetailWebActivity::class.java.simpleName

    private lateinit var binding: ActivityDetailWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webSettings = binding.webView.settings
        webSettings.apply {
            loadWithOverviewMode = true
            setSupportZoom(true)
            javaScriptEnabled = false
            displayZoomControls = true
        }

        binding.webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar2.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        })

        val url = intent.getStringExtra("url")
        if (url != null) {
            binding.webView.loadUrl(url)
        }
    }
}