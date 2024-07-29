package com.example.apppi.ui.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import org.json.JSONArray
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

private const val TAG = "MyUrlRequestCallback"

class MyUrlRequestCallback(private val apiName : String = "", private val fragmentReference : Fragment) : UrlRequest.Callback() {
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

        if(info?.httpStatusCode!! >= 300){
            // TODO: FrontEnd Toast for error
        }else {
            byteBuffer?.let {
                it.flip()
                val bytes = ByteArray(it.remaining())
                it.get(bytes)
                responseBody.append(String(bytes, StandardCharsets.UTF_8))
            }
            request?.read(ByteBuffer.allocateDirect(102400))
        }
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
            //val data = jsonObject.getString("name")
            Log.i(TAG, "Extracted jsonObject: $jsonObject")
            //Log.i(TAG, "Extracted data: $data")

            when(apiName){
                "YT_id" -> {
                    try {
                        var viewModel: YTViewModel = ViewModelProvider(fragmentReference).get(YTViewModel::class.java)

                        val channelID = ((jsonObject.getJSONArray("items")[0] as JSONObject).get("id") as JSONObject).get("channelId")

                        viewModel.setID("$channelID")

                    }catch (e : Exception) {
                        Log.e(TAG, "$e.message")
                    }
                }
                "YT_stats" -> {
                    var viewModel: YTViewModel = ViewModelProvider(fragmentReference).get(YTViewModel::class.java)
                    val channelSubs = ((jsonObject.getJSONArray("items")[0] as JSONObject).get("statistics") as JSONObject).get("subscriberCount") as String
                    viewModel.setSubs(channelSubs.toInt())
                }
                CurrencyExchangeFragment.API_NAME -> {
                    Log.d(TAG, "Currency Exchange API Response: $jsonObject")
                    val viewModel: CurrencyViewModel = ViewModelProvider(fragmentReference).get(CurrencyViewModel::class.java)
                    viewModel.setRates(jsonObject.toString())
                }
                "customRawCall" -> {
                    var viewModel: CustomPresetViewModel = ViewModelProvider(fragmentReference).get(CustomPresetViewModel::class.java)
                    viewModel.setJsonObject(jsonObject.toString())
                }
                else -> {
                    if(apiName.startsWith("customNestedCall")){
                        var nestedAttribute = apiName.substringAfter(':')
                        var viewModel: CustomPresetViewModel = ViewModelProvider(fragmentReference).get(CustomPresetViewModel::class.java)
                        viewModel.setJsonAttribute(extractJsonAttribute(jsonObject, nestedAttribute).toString())
                    }
                    else {
                        Log.e(TAG, "API name not found")
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse JSON response: ${e.message}")
        }
    }


    fun extractJsonAttribute(jsonObject: JSONObject, att : String) : Any?{
        Log.v("asdasdasdasdasdasd","OBJ: $jsonObject")
        val c = when(jsonObject){
            is JSONObject -> jsonObject.get(att)
            is JSONArray -> jsonObject.length()/*jsonObject.get(att.toInt())*//*extractJsonAttribute(jsonObject.getJSONArray(), att)*///{
                //for(i in 0 until jsonObject.length())
                //    extractJsonAttribute(jsonObject.getJSONObject(i),att)
            //}
            else -> throw Exception("Not Found")
        }
        Log.v("asdasdasdasdasdasd","attribute: $c")
        return c
    }
}