package com.example.vefkhistkaosani

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("DEPRECATION")
class VefxFragment : Fragment() {


    var mWebView: WebView? = null


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

        setHasOptionsMenu(true)
        val v: View = inflater.inflate(R.layout.fragment_vefx, container, false)



        val id = Login.logged;
        mWebView = v.findViewById<View>(R.id.view_main_vefx) as WebView



        mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?userid=$id")

        // Enable Javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")



        mWebView!!.webViewClient = object: WebViewClient(){
            var sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
            val highScore = sharedPref.getString("SCROLLTO", null)
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                super.onPageStarted(view, url, favicon)
            }
            override fun onPageFinished(view: WebView?, url: String?) {


                if (highScore != null){
                    mWebView!!.evaluateJavascript("scrollToThat('$highScore');", null);
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
                    with(sharedPref.edit()) {
                        putString("SCROLLTO", null)
                        apply()
                    }
                }
                super.onPageFinished(view, url)
            }

        }

        // Force links and redirects to open in the WebView instead of in a browser

        return v

    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun scrollToThat(id: String){



    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as Dashboard?)
                ?.setActionBarTitle("ვეფხისტყაოსანი")
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





