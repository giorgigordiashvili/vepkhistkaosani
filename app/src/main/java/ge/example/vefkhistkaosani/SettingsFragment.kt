package ge.example.vefkhistkaosani


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.vefkhistkaosani.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingsFragment : BottomSheetDialogFragment() {
    var PACKAGE_NAME: String? = null

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun rateApp()
        {
            PACKAGE_NAME = context?.getPackageName()



            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$PACKAGE_NAME")))

        }

        @android.webkit.JavascriptInterface
        fun loggg()
        {
            (activity as Dashboard?)
                    ?.logOut()

        }
    }
    var mWebView: WebView? = null
    var text: TextView? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //BACK PRESS HANDLING IN WEBVIEW

        val v: View = inflater.inflate(R.layout.fragment_settings, container, false)

        val close_add = v!!.findViewById(R.id.close_settings) as Button

        close_add.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        mWebView = v.findViewById<View>(R.id.view_main_settings) as WebView
        val sp = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)

        val id = Login.logged;
        if (!DetectConnection.checkInternetConnection(this.context)) {
            (activity as Dashboard?)?.NoInternet()
        } else {
            mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=settings&userid=$id")
        }

        mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")

        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView!!.webViewClient = object: WebViewClient(){


            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onPageFinished(view: WebView?, url: String?) {
                text = v.findViewById<View>(R.id.textView11) as TextView
                text!!.setText(mWebView!!.title)


                (view!!.getTitle());
                view?.isFocusableInTouchMode = true
                view?.requestFocus()
                view?.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP ) {
                        if(mWebView?.canGoBack()!!){
                            mWebView!!.goBack()
                        }else {
                            dismiss()
                        }



                        true
                    } else
                        false
                }


                super.onPageFinished(view, url)
            }

        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return


        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }






    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
            behavior.isHideable = false


        }
    }



    override fun getTheme(): Int  = R.style.Theme_NoWiredStrapInNavigationBar


}