package com.qrbarcode.creator.feature.tabs

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.qrbarcode.creator.AdsConsent
import com.qrbarcode.creator.AdsLoader
import com.qrbarcode.creator.BannerHelper
import com.qrbarcode.creator.BuildConfig
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.feature.tabs.create.CreateBarcodeFragment
import com.qrbarcode.creator.feature.tabs.history.BarcodeHistoryFragment
import com.qrbarcode.creator.feature.tabs.scan.ScanBarcodeFromCameraFragment
import com.qrbarcode.creator.feature.tabs.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomTabsActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val ACTION_CREATE_BARCODE = "${BuildConfig.APPLICATION_ID}.CREATE_BARCODE"
        private const val ACTION_HISTORY = "${BuildConfig.APPLICATION_ID}.HISTORY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tabs)

        supportEdgeToEdge()
        initBottomNavigationView()
       loadBannerads()



        if (savedInstanceState == null) {
            showInitialFragment()
        }
    }

    private fun loadBannerads() {
        // UMP consent first; banner + preloaded interstitial only once ads may be requested.
        AdsConsent.gather(this) {
            AdsLoader.displayInterstitial(this)
            adView.post { BannerHelper.load(this, adView) }
        }
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == bottom_navigation_view.selectedItemId) {
            return false
        }
        showFragment(item.itemId)
        return true
    }

    override fun onBackPressed() {
        if (bottom_navigation_view.selectedItemId == R.id.item_scan) {
            super.onBackPressed()
        } else {
            bottom_navigation_view.selectedItemId = R.id.item_scan
        }
    }


    private fun supportEdgeToEdge() {

        bottom_navigation_view.applySystemWindowInsets(applyBottom = true)
    }

    private fun initBottomNavigationView() {
        bottom_navigation_view.apply {
            setOnNavigationItemSelectedListener(this@BottomTabsActivity)
        }
    }

    private fun showInitialFragment() {
        when (intent?.action) {
            ACTION_CREATE_BARCODE -> bottom_navigation_view.selectedItemId = R.id.item_create
            ACTION_HISTORY -> bottom_navigation_view.selectedItemId = R.id.item_history
            else -> showFragment(R.id.item_scan)
        }
    }

    private fun showFragment(bottomItemId: Int) {
        val fragment = when (bottomItemId) {
            R.id.item_scan -> ScanBarcodeFromCameraFragment()
            R.id.item_create -> CreateBarcodeFragment()
            R.id.item_history -> BarcodeHistoryFragment()
            R.id.item_settings -> SettingsFragment()
            else -> null
        }
        fragment?.apply(::replaceFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_fragment_container, fragment)
            .setReorderingAllowed(true)
            .commit()
    }

}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.adView: android.widget.FrameLayout get() = findViewById(com.qrbarcode.creator.R.id.adView)
private val androidx.fragment.app.Fragment.adView: android.widget.FrameLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.adView)
private val android.app.Dialog.adView: android.widget.FrameLayout get() = findViewById(com.qrbarcode.creator.R.id.adView)
private val android.app.Activity.bottom_navigation_view: com.google.android.material.bottomnavigation.BottomNavigationView get() = findViewById(com.qrbarcode.creator.R.id.bottom_navigation_view)
private val androidx.fragment.app.Fragment.bottom_navigation_view: com.google.android.material.bottomnavigation.BottomNavigationView get() = requireView().findViewById(com.qrbarcode.creator.R.id.bottom_navigation_view)
private val android.app.Dialog.bottom_navigation_view: com.google.android.material.bottomnavigation.BottomNavigationView get() = findViewById(com.qrbarcode.creator.R.id.bottom_navigation_view)
private val android.app.Activity.layout_fragment_container: android.widget.FrameLayout get() = findViewById(com.qrbarcode.creator.R.id.layout_fragment_container)
private val androidx.fragment.app.Fragment.layout_fragment_container: android.widget.FrameLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.layout_fragment_container)
private val android.app.Dialog.layout_fragment_container: android.widget.FrameLayout get() = findViewById(com.qrbarcode.creator.R.id.layout_fragment_container)
