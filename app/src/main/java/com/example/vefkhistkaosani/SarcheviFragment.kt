package com.example.vefkhistkaosani


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView


class SarcheviFragment : Fragment() {
    private var v: View? = null


    var mWebView: WebView? = null
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }
    private inner class JavascriptInterface
    {

        @android.webkit.JavascriptInterface
        fun showToast(text: String?)
        {
            val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
            navBar.visibility = View.VISIBLE
            activity?.runOnUiThread(Runnable { navController.navigate(R.id.nav_vefx) })




            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString("SCROLLTO", text)
                apply()
            }

        }

    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

        val colors = intArrayOf(
                Color.parseColor("#838383"),
                Color.parseColor("#DAB983")

        )

        val bottomNav: BottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.itemIconTintList = ColorStateList(states, colors)
        bottomNav.itemTextColor = ColorStateList(states, colors)

        setHasOptionsMenu(true)

            v = inflater.inflate(R.layout.fragment_sarchevi, container, false)
            mWebView = v?.findViewById<View>(R.id.view_main_sarc) as WebView
            val id = Login.logged;
        if (!DetectConnection.checkInternetConnection(this.context)) {
            (activity as Dashboard?)?.NoInternet()
        } else {
            mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=sarchevi&userid=$id")
        }



            // Enable Javascript
            val webSettings = mWebView!!.settings


            webSettings.javaScriptEnabled = true
            mWebView?.addJavascriptInterface(JavascriptInterface(), "javascript_bridge")

            // Force links and redirects to open in the WebView instead of in a browser
            mWebView!!.webViewClient = WebViewClient()





        return v
    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as Dashboard?)
                ?.setActionBarTitle("                 სარჩევი")
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView


        searchView.setQueryHint("ძებნა")

        //BOTTOMSHEET


        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                navBar.visibility = View.GONE

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {

                navBar.visibility = View.VISIBLE
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