package com.qrbarcode.creator.feature.tabs.create.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.qrbarcode.creator.R
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.feature.BaseActivity
import com.qrbarcode.creator.feature.tabs.create.CreateBarcodeActivity
import com.qrbarcode.creator.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat
import com.qrbarcode.creator.AdsLoader

class CreateQrCodeAllActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, CreateQrCodeAllActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr_code_all)
        supportEdgeToEdge()
        handleToolbarBackClicked()
        handleButtonsClicked()
        AdsLoader.displayInterstitial(this)
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun handleToolbarBackClicked() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleButtonsClicked() {
        button_text.setOnClickListener { AdsLoader.showAds(this){startBarcode(BarcodeSchema.OTHER)} }
        button_url.setOnClickListener { AdsLoader.showAds(this){startBarcode(BarcodeSchema.URL)} }
        button_wifi.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.WIFI)} }
        button_location.setOnClickListener { AdsLoader.showAds(this){startBarcode(BarcodeSchema.GEO)} }
        button_otp.setOnClickListener {  AdsLoader.showAds(this){startBarcode( BarcodeSchema.OTP_AUTH)} }
        button_contact_vcard.setOnClickListener {  AdsLoader.showAds(this){startBarcode( BarcodeSchema.VCARD)}}
        button_contact_mecard.setOnClickListener {  AdsLoader.showAds(this){startBarcode( BarcodeSchema.MECARD)} }
        button_event.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.VEVENT)} }
        button_phone.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.PHONE)} }
        button_email.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.EMAIL)} }
        button_sms.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.SMS)}}
        button_mms.setOnClickListener {  AdsLoader.showAds(this){startBarcode( BarcodeSchema.MMS)} }
        button_cryptocurrency.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.CRYPTOCURRENCY)}}
        button_bookmark.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.BOOKMARK)} }
        button_app.setOnClickListener {  AdsLoader.showAds(this){startBarcode(BarcodeSchema.APP)} }
    }
    private fun startBarcode(string: BarcodeSchema){
        CreateBarcodeActivity.start(this,BarcodeFormat.QR_CODE,string)
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_app: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_app)
private val androidx.fragment.app.Fragment.button_app: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_app)
private val android.app.Dialog.button_app: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_app)
private val android.app.Activity.button_bookmark: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_bookmark)
private val androidx.fragment.app.Fragment.button_bookmark: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_bookmark)
private val android.app.Dialog.button_bookmark: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_bookmark)
private val android.app.Activity.button_contact_mecard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_contact_mecard)
private val androidx.fragment.app.Fragment.button_contact_mecard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_contact_mecard)
private val android.app.Dialog.button_contact_mecard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_contact_mecard)
private val android.app.Activity.button_contact_vcard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val androidx.fragment.app.Fragment.button_contact_vcard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val android.app.Dialog.button_contact_vcard: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_contact_vcard)
private val android.app.Activity.button_cryptocurrency: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_cryptocurrency)
private val androidx.fragment.app.Fragment.button_cryptocurrency: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_cryptocurrency)
private val android.app.Dialog.button_cryptocurrency: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_cryptocurrency)
private val android.app.Activity.button_email: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_email)
private val androidx.fragment.app.Fragment.button_email: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_email)
private val android.app.Dialog.button_email: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_email)
private val android.app.Activity.button_event: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_event)
private val androidx.fragment.app.Fragment.button_event: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_event)
private val android.app.Dialog.button_event: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_event)
private val android.app.Activity.button_location: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_location)
private val androidx.fragment.app.Fragment.button_location: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_location)
private val android.app.Dialog.button_location: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_location)
private val android.app.Activity.button_mms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_mms)
private val androidx.fragment.app.Fragment.button_mms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_mms)
private val android.app.Dialog.button_mms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_mms)
private val android.app.Activity.button_otp: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_otp)
private val androidx.fragment.app.Fragment.button_otp: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_otp)
private val android.app.Dialog.button_otp: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_otp)
private val android.app.Activity.button_phone: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_phone)
private val androidx.fragment.app.Fragment.button_phone: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_phone)
private val android.app.Dialog.button_phone: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_phone)
private val android.app.Activity.button_sms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_sms)
private val androidx.fragment.app.Fragment.button_sms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_sms)
private val android.app.Dialog.button_sms: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_sms)
private val android.app.Activity.button_text: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_text)
private val androidx.fragment.app.Fragment.button_text: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_text)
private val android.app.Dialog.button_text: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_text)
private val android.app.Activity.button_url: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_url)
private val androidx.fragment.app.Fragment.button_url: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_url)
private val android.app.Dialog.button_url: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_url)
private val android.app.Activity.button_wifi: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_wifi)
private val androidx.fragment.app.Fragment.button_wifi: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_wifi)
private val android.app.Dialog.button_wifi: com.qrbarcode.creator.feature.common.view.IconButtonWithDelimiter get() = findViewById(com.qrbarcode.creator.R.id.button_wifi)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
