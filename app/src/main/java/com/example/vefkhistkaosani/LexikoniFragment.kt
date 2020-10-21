package com.example.vefkhistkaosani


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class LexikoniFragment : Fragment() {

    var mWebView: WebView? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_lexikoni, container, false)
        mWebView = v.findViewById<View>(R.id.view_main_lexikoni) as WebView
        mWebView!!.loadUrl("https://vefxistyaosani.ge/iOS/?page=lexikoni")

        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView!!.webViewClient = WebViewClient()
        return v
    }
    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as Dashboard?)
                ?.setActionBarTitle("ლექსიკონი")
    }
}