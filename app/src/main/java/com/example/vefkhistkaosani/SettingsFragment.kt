package com.example.vefkhistkaosani


import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingsFragment : BottomSheetDialogFragment() {

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun logOut()
        {
            (activity as Dashboard?)
                    ?.logOut()

        }
    }
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
        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)
        mWebView = v.findViewById<View>(R.id.view_main_settings) as WebView
        val sp = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)

        val id = Login.logged;
        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=settings&userid=$id")


        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView!!.webViewClient = WebViewClient()
        return v
    }
    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

        }
    }
    override fun getTheme(): Int  = R.style.Theme_NoWiredStrapInNavigationBar

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as Dashboard?)
                ?.setActionBarTitle("კონფიგურაცია")
    }
}