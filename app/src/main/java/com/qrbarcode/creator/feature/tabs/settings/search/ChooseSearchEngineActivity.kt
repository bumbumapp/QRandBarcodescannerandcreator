package com.qrbarcode.creator.feature.tabs.settings.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.settings
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.unsafeLazy
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.feature.common.view.SettingsRadioButton
import com.qrbarcode.creator.model.SearchEngine

class ChooseSearchEngineActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChooseSearchEngineActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val buttons by unsafeLazy {
        listOf(button_none, button_ask_every_time, button_bing, button_duck_duck_go, button_google, button_qwant, button_yahoo, button_yandex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_search_engine)
        supportEdgeToEdge()
        initToolbar()
        showInitialValue()
        handleSettingsChanged()
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun showInitialValue() {
        when (settings.searchEngine) {
            SearchEngine.NONE -> button_none.isChecked = true
            SearchEngine.ASK_EVERY_TIME -> button_ask_every_time.isChecked = true
            SearchEngine.BING -> button_bing.isChecked = true
            SearchEngine.DUCK_DUCK_GO -> button_duck_duck_go.isChecked = true
            SearchEngine.GOOGLE -> button_google.isChecked = true
            SearchEngine.QWANT -> button_qwant.isChecked = true
            SearchEngine.YAHOO -> button_yahoo.isChecked = true
            SearchEngine.YANDEX -> button_yandex.isChecked = true
        }
    }

    private fun handleSettingsChanged() {
        button_none.setCheckedChangedListener(SearchEngine.NONE)
        button_ask_every_time.setCheckedChangedListener(SearchEngine.ASK_EVERY_TIME)
        button_bing.setCheckedChangedListener(SearchEngine.BING)
        button_duck_duck_go.setCheckedChangedListener(SearchEngine.DUCK_DUCK_GO)
        button_google.setCheckedChangedListener(SearchEngine.GOOGLE)
        button_qwant.setCheckedChangedListener(SearchEngine.QWANT)
        button_yahoo.setCheckedChangedListener(SearchEngine.YAHOO)
        button_yandex.setCheckedChangedListener(SearchEngine.YANDEX)
    }

    private fun SettingsRadioButton.setCheckedChangedListener(searchEngine: SearchEngine) {
        setCheckedChangedListener { isChecked ->
            if (isChecked) {
                uncheckOtherButtons(this)
                settings.searchEngine = searchEngine
            }
        }
    }

    private fun uncheckOtherButtons(checkedButton: View) {
        buttons.forEach { button ->
            if (checkedButton !== button) {
                button.isChecked = false
            }
        }
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_ask_every_time: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_ask_every_time)
private val androidx.fragment.app.Fragment.button_ask_every_time: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_ask_every_time)
private val android.app.Dialog.button_ask_every_time: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_ask_every_time)
private val android.app.Activity.button_bing: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_bing)
private val androidx.fragment.app.Fragment.button_bing: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_bing)
private val android.app.Dialog.button_bing: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_bing)
private val android.app.Activity.button_duck_duck_go: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_duck_duck_go)
private val androidx.fragment.app.Fragment.button_duck_duck_go: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_duck_duck_go)
private val android.app.Dialog.button_duck_duck_go: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_duck_duck_go)
private val android.app.Activity.button_google: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_google)
private val androidx.fragment.app.Fragment.button_google: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_google)
private val android.app.Dialog.button_google: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_google)
private val android.app.Activity.button_none: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_none)
private val androidx.fragment.app.Fragment.button_none: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_none)
private val android.app.Dialog.button_none: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_none)
private val android.app.Activity.button_qwant: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_qwant)
private val androidx.fragment.app.Fragment.button_qwant: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_qwant)
private val android.app.Dialog.button_qwant: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_qwant)
private val android.app.Activity.button_yahoo: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_yahoo)
private val androidx.fragment.app.Fragment.button_yahoo: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_yahoo)
private val android.app.Dialog.button_yahoo: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_yahoo)
private val android.app.Activity.button_yandex: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_yandex)
private val androidx.fragment.app.Fragment.button_yandex: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_yandex)
private val android.app.Dialog.button_yandex: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_yandex)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
