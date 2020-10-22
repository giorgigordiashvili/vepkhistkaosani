package com.example.vefkhistkaosani


import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LexikoniFragment : Fragment() {

    var mWebView: WebView? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val v: View = inflater.inflate(R.layout.fragment_lexikoni, container, false)
        mWebView = v.findViewById<View>(R.id.view_main_lexikoni) as WebView
        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=lexikoni")

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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                navBar.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                navBar.visibility = View.VISIBLE
                val id = Login.logged;
                mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=lexikoni&userid=$id")
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
                        mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=lexikoni&q=$newText&userid=$id")
                    }
                };
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)

    }
}