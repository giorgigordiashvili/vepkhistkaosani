package ge.example.vefkhistkaosani


import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.vefkhistkaosani.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SanishniFragment : Fragment() {

    var mWebView: WebView? = null

    private inner class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        fun shareText(text: String){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        @android.webkit.JavascriptInterface
        fun copyText(text:String){


            val clipboard: ClipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(null, text)
            clipboard.setPrimaryClip(clip)
        }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val states = arrayOf( intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

        val colors = intArrayOf(
                Color.parseColor("#838383"),
                Color.parseColor("#838383")

        )

        val bottomNav: BottomNavigationView = activity!!.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.itemIconTintList = ColorStateList(states, colors)
        bottomNav.itemTextColor = ColorStateList(states,colors)
        setHasOptionsMenu(true)
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
        val v: View = inflater.inflate(R.layout.fragment_sanishni, container, false)
        mWebView = v.findViewById<View>(R.id.view_main_sanishni) as WebView
        val id = Login.logged;

        if (!DetectConnection.checkInternetConnection(this.context)) {
            (activity as Dashboard?)?.NoInternet()
        } else {
            mWebView!!.loadUrl("http://vefxistyaosani.ge/android/?page=sanishni&userid=$id")
        }
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
                ?.setActionBarTitle("სანიშნი")
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setQueryHint("ძებნა")
        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_nav_view)
        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                navBar.visibility = View.GONE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                navBar.visibility = View.VISIBLE
                val id = Login.logged;
                mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=sanishni&userid=$id")
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
                        mWebView?.loadUrl("http://vefxistyaosani.ge/android/?page=sanishni&q=$newText&userid=$id")
                    }
                };
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)

    }
}