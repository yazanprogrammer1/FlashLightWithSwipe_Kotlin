package com.example.flashlightwithswipe_kotlin

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import com.example.flashlightwithswipe_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //... Code Flash Light With Swipe

        //srtUp Camera Service
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        // App Title Design
        setUpAppName()

        // init Views
        binding.apply {
            // check if the device has a flashlight
            var hasFlashLight = applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
            if (!hasFlashLight) {
                // Show Error Message if device not have a flashLight
                light.isEnabled = false
                Toast.makeText(
                    applicationContext,
                    "This Device Not Have FlashLight",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Animation Listener
            LayoutMain.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == motionLayout!!.endState) {
                        try {
                            val cameraId = cameraManager.cameraIdList[0]
                            cameraManager.setTorchMode(cameraId, true)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            e.printStackTrace()
                        }
                    } else {
                        try {
                            val cameraId = cameraManager.cameraIdList[0]
                            cameraManager.setTorchMode(cameraId, false)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setUpAppName() {

        val spannableString = SpannableString("FlashLight")
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.yellow))
        spannableString.setSpan(colorSpan, 5, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.title.text = spannableString
    }
}