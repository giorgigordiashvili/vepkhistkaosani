package com.example.vefkhistkaosani


import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_sarchevi.*
import kotlinx.android.synthetic.main.fragment_sarchevi.view.*
import java.util.*

class SarcheviFragment : Fragment() {

    var mWebView: WebView? = null

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun showToast(text: String?)
        {
            (activity as Dashboard?)
                    ?.changeView(text)

        }
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val v: View = inflater.inflate(R.layout.fragment_sarchevi, container, false)

      mWebView = v.findViewById<View>(R.id.view_main_sarc) as WebView
        val id = Login.logged;
        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=sarchevi&userid=$id")
        mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")

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
                ?.setActionBarTitle("სარჩევი")
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
                mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=sarchevi&userid=$id")
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
                    if (newText.length > 0) {
                        val id = Login.logged;
                        mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=sarchevi&q=$newText&userid=$id")
                    }
                };
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)

    }

}