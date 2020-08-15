package com.gopi.keyencebarcodescanner

interface ScanListener {
    fun onSuccessEvent(barcodeData: String)

    fun onFailureEvent(error: String)
}