package com.example.vefkhistkaosani


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment


class EseebiFragment : Fragment() {

    var mWebView: WebView? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //BACK PRESS HANDLING IN WEBVIEW
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mWebView?.canGoBack()!!){
                    mWebView?.goBack();
                }else {
                    activity!!.finish()
                }
            }
        })

        val v: View = inflater.inflate(R.layout.fragment_eseebi, container, false)
        mWebView = v.findViewById<View>(R.id.view_main_eseebi) as WebView
        val id = Login.logged;
        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=eseebi&userid=$id")

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
                ?.setActionBarTitle("ესეები")
    }
}