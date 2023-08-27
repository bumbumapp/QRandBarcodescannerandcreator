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
import kotlinx.android.synthetic.main.fragment_create_barcode.*

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