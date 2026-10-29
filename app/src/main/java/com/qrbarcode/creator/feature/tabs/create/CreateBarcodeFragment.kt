package com.qrbarcode.creator.feature.tabs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.extension.clipboardManager
import com.qrbarcode.creator.extension.orZero
import com.qrbarcode.creator.feature.tabs.create.barcode.CreateBarcodeAllActivity
import com.qrbarcode.creator.feature.tabs.create.qr.CreateQrCodeAllActivity
import com.qrbarcode.creator.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat
import com.qrbarcode.creator.AdsLoader

class CreateBarcodeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_barcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
        handleButtonsClicked()
        AdsLoader.displayInterstitial(requireContext());
    }

    private fun supportEdgeToEdge() {
        app_bar_layout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonsClicked() {
        // QR code
        button_clipboard.setOnClickListener { CreateBarcodeActivity.start(requireActivity(), BarcodeFormat.QR_CODE, BarcodeSchema.OTHER, getClipboardContent())  }
        button_text.setOnClickListener { AdsLoader.showAds(requireContext()){startBarcode(BarcodeSchema.OTHER)} }
        button_url.setOnClickListener { AdsLoader.showAds(requireContext()){startBarcode(BarcodeSchema.URL)}}
        button_wifi.setOnClickListener { AdsLoader.showAds(requireContext()){startBarcode(BarcodeSchema.WIFI)}}
        button_location.setOnClickListener {AdsLoader.showAds(requireContext()){startBarcode(BarcodeSchema.GEO)}}
        button_contact_vcard.setOnClickListener { AdsLoader.showAds(requireContext()){startBarcode(BarcodeSchema.VCARD)} }
        button_show_all_qr_code.setOnClickListener { CreateQrCodeAllActivity.start(requireActivity()) }

        // Barcode
        button_create_barcode.setOnClickListener { CreateBarcodeAllActivity.start(requireActivity()) }
    }
    private fun startBarcode(string: BarcodeSchema){
        CreateBarcodeActivity.start(requireActivity(),BarcodeFormat.QR_CODE,string)
    }
    private fun getClipboardContent(): String {
        val clip = requireActivity().clipboardManager?.primaryClip ?: return ""
        return when (clip.itemCount.orZero()) {
            0 -> ""
            else -> clip.getItemAt(0).text.toString()
        }
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val androidx.fragment.app.Fragment.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val android.app.Dialog.app_bar_layout: com.google.android.material.appbar.AppBarLayout get() = findViewById(com.qrbarcode.creator.R.id.app_bar_layout)
private val android.app.Activity.button_clipboard: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_clipboard)
private val androidx.fragment.app.Fragment.button_clipboard: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_clipboard)
private val android.app.Dialog.button_clipboard: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_clipboard)
private val android.app.Activity.button_contact_vcard: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val androidx.fragment.app.Fragment.button_contact_vcard: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val android.app.Dialog.button_contact_vcard: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val android.app.Activity.button_create_barcode: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_create_barcode)
private val androidx.fragment.app.Fragment.button_create_barcode: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_create_barcode)
private val android.app.Dialog.button_create_barcode: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_create_barcode)
private val android.app.Activity.button_location: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_location)
private val androidx.fragment.app.Fragment.button_location: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_location)
private val android.app.Dialog.button_location: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_location)
private val android.app.Activity.button_show_all_qr_code: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_show_all_qr_code)
private val androidx.fragment.app.Fragment.button_show_all_qr_code: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_show_all_qr_code)
private val android.app.Dialog.button_show_all_qr_code: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_show_all_qr_code)
private val android.app.Activity.button_text: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_text)
private val androidx.fragment.app.Fragment.button_text: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_text)
private val android.app.Dialog.button_text: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_text)
private val android.app.Activity.button_url: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_url)
private val androidx.fragment.app.Fragment.button_url: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_url)
private val android.app.Dialog.button_url: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_url)
private val android.app.Activity.button_wifi: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_wifi)
private val androidx.fragment.app.Fragment.button_wifi: android.view.View get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_wifi)
private val android.app.Dialog.button_wifi: android.view.View get() = findViewById(com.qrbarcode.creator.R.id.button_wifi)
