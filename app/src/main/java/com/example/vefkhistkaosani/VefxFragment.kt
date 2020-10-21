package com.example.vefkhistkaosani

import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment


@Suppress("DEPRECATION")
class VefxFragment : Fragment() {

    var mWebView: WebView? = null


    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        val v: View = inflater.inflate(R.layout.fragment_vefx, container, false)


        val id = Login.logged;
        mWebView = v.findViewById<View>(R.id.view_main_vefx) as WebView
        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?userid=$id")

        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true


        // Force links and redirects to open in the WebView instead of in a browser
        mWebView!!.webViewClient = WebViewClient()
        return v

    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun scrollToThat(id: String){


    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as Dashboard?)
                ?.setActionBarTitle("        ვეფხისტყაოსანი")
    }

    private fun callJavaScript(view: WebView, methodName: String, vararg params: Any) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("javascript:try{")
        stringBuilder.append(methodName)
        stringBuilder.append("(")
        var separator = ""
        for (param in params) {
            stringBuilder.append(separator)
            separator = ","
            if (param is String) {
                stringBuilder.append("'")
            }
            stringBuilder.append(param.toString().replace("'", "\\'"))
            if (param is String) {
                stringBuilder.append("'")
            }
        }
        stringBuilder.append(")}catch(error){console.error(error.message);}")
        val call = stringBuilder.toString()
        view.loadUrl(call)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                val id = Login.logged;
                mWebView?.loadUrl("http://vefxistyaosani.ge/android/?userid=$id")
                return true
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus();

                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length > 1) {
                        val id = Login.logged;
                        mWebView?.loadUrl("http://vefxistyaosani.ge/android/?q=$newText?userid=$id")
                    }
                };
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)

    }




}





