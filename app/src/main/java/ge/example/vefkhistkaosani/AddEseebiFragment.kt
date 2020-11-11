package ge.example.vefkhistkaosani


import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.example.vefkhistkaosani.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddEseebiFragment : BottomSheetDialogFragment() {

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun loggg()
        {
            (activity as Dashboard?)
                    ?.logOut()

        }
    }
    var mWebView: WebView? = null


    @RequiresApi(Build.VERSION_CODES.KITKAT)
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
        val v: View = inflater.inflate(R.layout.fragment_addeseebi, container, false)
        val close_add = v!!.findViewById(R.id.close_addeseebi) as Button

        close_add.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        val share_ese = v!!.findViewById(R.id.share_ese) as Button
        share_ese.setOnClickListener(View.OnClickListener {
            println(mWebView!!.evaluateJavascript("validateForm();") {
                if(it.equals("false")){
                    Toast.makeText(context, "გთხოვთ დაასათაუროთ და შეიყვანოთ მინიმუმ 150 სიტყვა", Toast.LENGTH_LONG)
                }else {
                    mWebView!!.evaluateJavascript("document.getElementById(\"submit\").click();", null)
                    Toast.makeText(context, "ესე წარმატებით გაიგზავნა", Toast.LENGTH_LONG)
                    dismiss()
                }


            })

        })
        mWebView = v.findViewById<View>(R.id.view_main_addeseebi) as WebView
        val sp = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)

        val id = Login.logged;
        if (!DetectConnection.checkInternetConnection(this.context)) {
            (activity as Dashboard?)?.NoInternet()
        } else {
            mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=addeseebi&userid=$id")
        }

        mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")

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
            behavior.isDraggable = false
            behavior.isHideable = false
        }
    }
    override fun getTheme(): Int  = R.style.Theme_NoWiredStrapInNavigationBar


}