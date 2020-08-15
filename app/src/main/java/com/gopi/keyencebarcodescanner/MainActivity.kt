package com.gopi.keyencebarcodescanner

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ScanListener {

    private var tag: String = MainActivity::class.java.simpleName
    private lateinit var scanBarcode: ScanBarcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView(){

        //scan button action
        scanBtn.setOnClickListener {
            callScanner()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::scanBarcode.isInitialized) {
            scanBarcode.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::scanBarcode.isInitialized) {
            scanBarcode.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::scanBarcode.isInitialized) {
            scanBarcode.onDestroy()
        }
    }

    private fun callScanner(){
        progressBar.visibility = View.VISIBLE
        //method to call scanner
        scanBarcode = ScanBarcode(this, this)
        scanBarcode.triggerAction()
    }

    override fun onSuccessEvent(barcodeData: String) {
        Log.v(tag, "onSuccessEvent: $barcodeData")

        progressBar.visibility = View.GONE

        tvScannedItem.text = ""
        tvScannedItem.text = barcodeData
    }

    override fun onFailureEvent(error: String) {
        Log.v(tag, "onFailureEvent: $error")

        progressBar.visibility = View.GONE

        tvScannedItem.text = ""
        tvScannedItem.text = error
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        Log.v(tag, "dispatchKeyEvent()")
        val keyCode = event.keyCode
        Log.v(tag, "keyCode: $keyCode")
        Log.v(tag, "action: ${event.action}")

        return when (keyCode) {
            KeyEvent.KEYCODE_F1 -> {
                if(event.action == KeyEvent.ACTION_DOWN) {
                    Log.v(tag, "KEYCODE_F1 PRESSED - ACTION_DOWN")
                    //to handle key ACTION_DOWN event
                    callScanner()
                } else if(event.action == KeyEvent.ACTION_UP) {
                    Log.v(tag, "KEYCODE_F1 PRESSED - ACTION_UP")
                    //to handle key ACTION_UP event
                }
                true
            }

            else -> {
                super.dispatchKeyEvent(event)
            }
        }
    }
}
