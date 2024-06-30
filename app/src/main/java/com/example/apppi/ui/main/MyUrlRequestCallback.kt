package com.example.apppi.ui.main

import android.util.Log
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

private const val TAG = "MyUrlRequestCallback"

class MyUrlRequestCallback : UrlRequest.Callback() {
    private val responseBody = StringBuilder()
    override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
        Log.i(TAG, "onRedirectReceived method called.")
        request?.followRedirect()
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "onResponseStarted method called.")
        request?.read(ByteBuffer.allocateDirect(102400))
    }

    override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
        Log.i(TAG, "onReadCompleted method called.")
        Log.i(TAG, "Header data: $info")            // status code etc.

        byteBuffer?.let {
            it.flip()
            val bytes = ByteArray(it.remaining())
            it.get(bytes)
            responseBody.append(String(bytes, StandardCharsets.UTF_8))
        }
        request?.read(ByteBuffer.allocateDirect(102400))
    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "onSucceeded method called.")
        parseJsonResponse(responseBody.toString())
    }

    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException?) {
        Log.e(TAG, "Request failed: ${error?.message}")
    }

    private fun parseJsonResponse(response: String) {
        try {
            val jsonObject = JSONObject(response)
            val data = jsonObject.getString("name")
            Log.i(TAG, "Extracted jsonObject: $jsonObject")
            Log.i(TAG, "Extracted data: $data")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse JSON response: ${e.message}")
        }
    }
}