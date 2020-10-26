package com.example.vefkhistkaosani

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.UrlQuerySanitizer
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_aporizmebi.*


class AporizmebiFragment : Fragment() {

    var mWebView: WebView? = null
    val v: View? = null

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun copyText(text: String){


            val clipboard: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(null, text)
            clipboard.setPrimaryClip(clip)
        }
        @android.webkit.JavascriptInterface
        fun shareText(text: String){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        println("STARTED AGAIN")
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

        val colors = intArrayOf(
                Color.parseColor("#838383"),
                Color.parseColor("#DAB983")

        )
        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
        val bottomNav: BottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.itemIconTintList = ColorStateList(states, colors)
        bottomNav.itemTextColor = ColorStateList(states, colors)


        setHasOptionsMenu(true)
        val v: View = inflater!!.inflate(R.layout.fragment_aporizmebi, container, false)



        var sort_dialog = v!!.findViewById(R.id.sort_dialog) as View
        sort_dialog.visibility = View.GONE

        val id = Login.logged;
        val button_anbani = v!!.findViewById(R.id.button_anbani) as Button
        val button_tavi = v!!.findViewById(R.id.button_tavi) as Button
        val button_tema = v!!.findViewById(R.id.button_tema) as Button
        val button_close = v!!.findViewById(R.id.button_close) as Button
        sort_dialog.setOnClickListener(View.OnClickListener {
            sort_dialog.visibility = View.GONE
            navBar.visibility = View.VISIBLE
        })
        button_close.setOnClickListener(View.OnClickListener {

            sort_dialog.visibility = View.GONE

            navBar.visibility = View.VISIBLE
        })
        button_anbani.setOnClickListener(View.OnClickListener {
            mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&in=anbani&userid=$id")
            sort_dialog.visibility = View.GONE
            navBar.visibility = View.VISIBLE
        })
        button_tavi.setOnClickListener(View.OnClickListener {
            mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&in=tavi&userid=$id")
            sort_dialog.visibility = View.GONE
            navBar.visibility = View.VISIBLE
        })
        button_tema.setOnClickListener(View.OnClickListener {
            mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&in=tema&userid=$id")
            sort_dialog.visibility = View.GONE
            navBar.visibility = View.VISIBLE
        })






        mWebView = v.findViewById<View>(R.id.view_main_apor) as WebView

        if (!DetectConnection.checkInternetConnection(this.context)) {
            (activity as Dashboard?)?.NoInternet()
        } else {
            mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&userid=$id")
        }


        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")



        mWebView!!.webViewClient = object: WebViewClient(){
            var sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                super.onPageStarted(view, url, favicon)
            }
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onPageFinished(view: WebView?, url: String?) {

                println("REFRESHED")

                super.onPageFinished(view, url)
            }

        }
        fun next(){
            mWebView!!.loadUrl("http://google.com")
        }


        // Force links and redirects to open in the WebView instead of in a browser

        return v
    }

    override fun onResume() {
        super.onResume()


        (activity as Dashboard?)
                ?.setActionBarTitle("            აფორიზმები")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        inflater.inflate(R.menu.sort_two_menu, menu)


        val searchItem = menu?.findItem(R.id.search)
        val sortItem = menu?.findItem(R.id.sort_button)
        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
        val searchView = searchItem?.actionView as SearchView
        searchView.setQueryHint("ძებნა")


        sortItem.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                sort_dialog.visibility = View.VISIBLE
                navBar.visibility = View.GONE

                return true
            }
        })
        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                sortItem.setVisible(false)
                navBar.visibility = View.GONE

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                ActivityCompat.invalidateOptionsMenu(activity);
                navBar.visibility = View.VISIBLE


                val url = mWebView?.url
                val sanitizer = UrlQuerySanitizer(url)
                val inAp = sanitizer.getValue("in")
                val id = Login.logged;
                mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&in=$inAp&userid=$id")
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus();

                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {

                val url = mWebView?.url

                val sanitizer = UrlQuerySanitizer(url)

                val inAp = sanitizer.getValue("in")


                if (newText != null) {
                    if (newText.length > 0) {
                        val id = Login.logged;
                        mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=aporizmebi&in=$inAp&q=$newText&userid=$id")
                    }
                };
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)

    }
    fun showSort(){

        val button = view!!.findViewById<View>(R.id.sort_dialog) as View
        button.visibility = View.VISIBLE
    }




}