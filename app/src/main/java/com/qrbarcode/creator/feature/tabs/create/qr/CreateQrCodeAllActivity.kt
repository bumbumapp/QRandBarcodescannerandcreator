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
import kotlinx.android.synthetic.main.activity_create_qr_code_all.*

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