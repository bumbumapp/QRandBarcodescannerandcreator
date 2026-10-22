package com.qrbarcode.creator.feature.tabs.settings.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.qrbarcode.creator.R
import com.qrbarcode.creator.di.settings
import com.qrbarcode.creator.extension.applySystemWindowInsets
import com.qrbarcode.creator.feature.BaseActivity

class ChooseCameraActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChooseCameraActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_camera)
        supportEdgeToEdge()
        handleToolbarBackClicked()
    }

    override fun onResume() {
        super.onResume()
        showSelectedCamera()
        handleBackCameraButtonChecked()
        handleFrontCameraButtonChecked()
    }

    private fun showSelectedCamera() {
        val isBackCamera = settings.isBackCamera
        button_back_camera.isChecked = isBackCamera
        button_front_camera.isChecked = isBackCamera.not()
    }

    private fun supportEdgeToEdge() {
        root_view.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun handleToolbarBackClicked() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleBackCameraButtonChecked() {
        button_back_camera.setCheckedChangedListener { isChecked ->
            if (isChecked) {
                button_front_camera.isChecked = false
            }
            settings.isBackCamera = isChecked
        }
    }

    private fun handleFrontCameraButtonChecked() {
        button_front_camera.setCheckedChangedListener { isChecked ->
            if (isChecked) {
                button_back_camera.isChecked = false
            }
            settings.isBackCamera = isChecked.not()
        }
    }
}

// Former kotlinx.android.synthetic accessors (plugin removed in Kotlin 2).
private val android.app.Activity.button_back_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_back_camera)
private val androidx.fragment.app.Fragment.button_back_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_back_camera)
private val android.app.Dialog.button_back_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_back_camera)
private val android.app.Activity.button_front_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_front_camera)
private val androidx.fragment.app.Fragment.button_front_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = requireView().findViewById(com.qrbarcode.creator.R.id.button_front_camera)
private val android.app.Dialog.button_front_camera: com.qrbarcode.creator.feature.common.view.SettingsRadioButton get() = findViewById(com.qrbarcode.creator.R.id.button_front_camera)
private val android.app.Activity.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val androidx.fragment.app.Fragment.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = requireView().findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Dialog.root_view: androidx.coordinatorlayout.widget.CoordinatorLayout get() = findViewById(com.qrbarcode.creator.R.id.root_view)
private val android.app.Activity.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
private val androidx.fragment.app.Fragment.toolbar: androidx.appcompat.widget.Toolbar get() = requireView().findViewById(com.qrbarcode.creator.R.id.toolbar)
private val android.app.Dialog.toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(com.qrbarcode.creator.R.id.toolbar)
