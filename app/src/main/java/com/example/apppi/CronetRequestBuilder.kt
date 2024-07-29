package com.example.apppi

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.apppi.ui.main.MyUrlRequestCallback
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CronetRequestBuilder() {
    fun buildRequest(cronetEngine : CronetEngine, apiURL: String, queries: Map<String, String>, apiName : String, fragmentReference : Fragment){
        val executor: Executor = Executors.newSingleThreadExecutor()
        val apiUrl = QueryStringBuilder.newInstance().buildQueryString(apiURL, queries)
        Log.i("MyUrlRequestCallback",apiUrl)
        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            apiUrl,
            MyUrlRequestCallback(apiName,fragmentReference),
            executor
        )
        requestBuilder.addHeader("Content-Type","application/json")
        val request: UrlRequest = requestBuilder.build()
        request.start()
    }


    companion object {
        fun newInstance() = CronetRequestBuilder()
    }
}