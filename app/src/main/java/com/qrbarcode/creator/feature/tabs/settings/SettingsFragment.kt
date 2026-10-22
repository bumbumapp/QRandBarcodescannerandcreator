package com.qrbarcode.creator.feature.tabs.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.barcodeDatabase
import com.qrbarcode.creator.di.settings
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.showError
import com.qrbarcode.creator.feature.common.dialog.DeleteConfirmationDialogFragment
import com.qrbarcode.creator.feature.tabs.settings.camera.ChooseCameraActivity
import com.qrbarcode.creator.feature.tabs.settings.formats.SupportedFormatsActivity
import com.qrbarcode.creator.feature.tabs.settings.search.ChooseSearchEngineActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class SettingsFragment : Fragment(), DeleteConfirmationDialogFragment.Listener {
    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()
        handleButtonCheckedChanged()
        handleButtonClicks()
        showSettings()

    }

    override fun onDeleteConfirmed() {
        clearHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

    fun supportEdgeToEdge() {
        app_bar_layout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonCheckedChanged() {
        button_inverse_barcode_colors_in_dark_theme.setCheckedChangedListener { settings.areBarcodeColorsInversed = it }
        button_open_links_automatically.setCheckedChangedListener { settings.openLinksAutomatically = it }
        button_copy_to_clipboard.setCheckedChangedListener { settings.copyToClipboard = it }
        button_simple_auto_focus.setCheckedChangedListener { settings.simpleAutoFocus = it }
        button_flashlight.setCheckedChangedListener { settings.flash = it }
        button_vibrate.setCheckedChangedListener { settings.vibrate = it }
        button_continuous_scanning.setCheckedChangedListener { settings.continuousScanning = it }
        button_confirm_scans_manually.setCheckedChangedListener { settings.confirmScansManually = it }
        button_save_scanned_barcodes.setCheckedChangedListener { settings.saveScannedBarcodesToHistory = it }
        button_save_created_barcodes.setCheckedChangedListener { settings.saveCreatedBarcodesToHistory = it }
        button_do_not_save_duplicates.setCheckedChangedListener { settings.doNotSaveDuplicates = it }

    }

    private fun handleButtonClicks() {

        button_choose_camera.setOnClickListener { ChooseCameraActivity.start(requireActivity()) }
        button_select_supported_formats.setOnClickListener { SupportedFormatsActivity.start(requireActivity()) }
        button_clear_history.setOnClickListener { showDeleteHistoryConfirmationDialog() }
        button_choose_search_engine.setOnClickListener { ChooseSearchEngineActivity.start(requireContext()) }
        // GDPR: users must be able to change their ad consent (shown only where UMP requires it).
        button_privacy_options.visibility =
            if (com.qrbarcode.creator.AdsConsent.isPrivacyOptionsRequired(requireContext())) View.VISIBLE else View.GONE
        button_privacy_options.setOnClickListener { com.qrbarcode.creator.AdsConsent.showPrivacyOptions(requireActivity()) }

    }

    private fun clearHistory() {
        button_clear_history.isEnabled = false

        barcodeDatabase.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    button_clear_history.isEnabled = true
                },
                { error ->
                    button_clear_history.isEnabled = true
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showSettings() {
        settings.apply {
            button_inverse_barcode_colors_in_dark_theme.isChecked = areBarcodeColorsInversed
            button_open_links_automatically.isChecked = openLinksAutomatically
            button_copy_to_clipboard.isChecked = copyToClipboard
            button_simple_auto_focus.isChecked = simpleAutoFocus
            button_flashlight.isChecked = flash
            button_vibrate.isChecked = vibrate
            button_continuous_scanning.isChecked = continuousScanning
            button_confirm_scans_manually.isChecked = confirmScansManually
            button_save_scanned_barcodes.isChecked = saveScannedBarcodesToHistory
            button_save_created_barcodes.isChecked = saveCreatedBarcodesToHistory
            button_do_not_save_duplicates.isChecked = doNotSaveDuplicates

        }
    }

    private fun showDeleteHistoryConfirmationDialog() {
        val dialog = DeleteConfirmationDialogFragment.newInstance(R.string.dialog_delete_clear_history_message)
        dialog.show(childFragmentManager, "")
    }






}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val androidx.fragment.app.Fragment.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val android.app.Dialog.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val android.app.Activity.button_choose_camera: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_choose_camera)
private val androidx.fragment.app.Fragment.button_choose_camera: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_choose_camera)
private val android.app.Dialog.button_choose_camera: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_choose_camera)
private val android.app.Activity.button_choose_search_engine: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_choose_search_engine)
private val androidx.fragment.app.Fragment.button_choose_search_engine: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_choose_search_engine)
private val android.app.Dialog.button_choose_search_engine: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_choose_search_engine)
private val android.app.Activity.button_clear_history: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_clear_history)
private val androidx.fragment.app.Fragment.button_clear_history: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_clear_history)
private val android.app.Dialog.button_clear_history: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_clear_history)
private val android.app.Activity.button_confirm_scans_manually: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_confirm_scans_manually)
private val androidx.fragment.app.Fragment.button_confirm_scans_manually: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_confirm_scans_manually)
private val android.app.Dialog.button_confirm_scans_manually: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_confirm_scans_manually)
private val android.app.Activity.button_continuous_scanning: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_continuous_scanning)
private val androidx.fragment.app.Fragment.button_continuous_scanning: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_continuous_scanning)
private val android.app.Dialog.button_continuous_scanning: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_continuous_scanning)
private val android.app.Activity.button_copy_to_clipboard: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_copy_to_clipboard)
private val androidx.fragment.app.Fragment.button_copy_to_clipboard: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_copy_to_clipboard)
private val android.app.Dialog.button_copy_to_clipboard: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_copy_to_clipboard)
private val android.app.Activity.button_do_not_save_duplicates: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_do_not_save_duplicates)
private val androidx.fragment.app.Fragment.button_do_not_save_duplicates: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_do_not_save_duplicates)
private val android.app.Dialog.button_do_not_save_duplicates: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_do_not_save_duplicates)
private val android.app.Activity.button_flashlight: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_flashlight)
private val androidx.fragment.app.Fragment.button_flashlight: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_flashlight)
private val android.app.Dialog.button_flashlight: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_flashlight)
private val android.app.Activity.button_inverse_barcode_colors_in_dark_theme: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_inverse_barcode_colors_in_dark_theme)
private val androidx.fragment.app.Fragment.button_inverse_barcode_colors_in_dark_theme: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_inverse_barcode_colors_in_dark_theme)
private val android.app.Dialog.button_inverse_barcode_colors_in_dark_theme: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_inverse_barcode_colors_in_dark_theme)
private val android.app.Activity.button_open_links_automatically: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_open_links_automatically)
private val androidx.fragment.app.Fragment.button_open_links_automatically: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_open_links_automatically)
private val android.app.Dialog.button_open_links_automatically: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_open_links_automatically)
private val android.app.Activity.button_save_created_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_save_created_barcodes)
private val androidx.fragment.app.Fragment.button_save_created_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_save_created_barcodes)
private val android.app.Dialog.button_save_created_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_save_created_barcodes)
private val android.app.Activity.button_save_scanned_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_save_scanned_barcodes)
private val androidx.fragment.app.Fragment.button_save_scanned_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_save_scanned_barcodes)
private val android.app.Dialog.button_save_scanned_barcodes: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_save_scanned_barcodes)
private val android.app.Activity.button_select_supported_formats: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_select_supported_formats)
private val androidx.fragment.app.Fragment.button_select_supported_formats: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_select_supported_formats)
private val android.app.Dialog.button_select_supported_formats: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_select_supported_formats)
private val android.app.Activity.button_simple_auto_focus: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_simple_auto_focus)
private val androidx.fragment.app.Fragment.button_simple_auto_focus: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_simple_auto_focus)
private val android.app.Dialog.button_simple_auto_focus: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_simple_auto_focus)
private val android.app.Activity.button_vibrate: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_vibrate)
private val androidx.fragment.app.Fragment.button_vibrate: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_vibrate)
private val android.app.Dialog.button_vibrate: com.qrbarcode.creator.feature.common.view.SettingsButton get() = findViewById(com.qrbarcode.creator.R.id.button_vibrate)
private val androidx.fragment.app.Fragment.button_privacy_options: com.qrbarcode.creator.feature.common.view.SettingsButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_privacy_options)
