package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.apppi.QueryStringBuilder
import com.example.apppi.R
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Fragment3 : Fragment() {
// Youtube API Interaction Fragment (Auth Token)
// Order: 1.  GET https://www.googleapis.com/youtube/v3/search params: part=snippet, q=@..., type=channel, key=...
// 2. GET https://www.googleapis.com/youtube/v3/channels params: part=statistics, id=oldQuery[items[statistics]].subcriberCount, key=...

    private lateinit var ytFetchBut: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        ytFetchBut = view.findViewById(R.id.ytFetch)
        ytFetchBut.setOnClickListener{
            val myBuilder = CronetEngine.Builder(context)
            val cronetEngine: CronetEngine = myBuilder.build()
            val executor: Executor = Executors.newSingleThreadExecutor()
            //val apiUrl = "https://www.googleapis.com/youtube/v3/search"

            val channelName : String = "SB737"

            val queries : Map<String, String> = mapOf("part" to "snippet", "q" to "@$channelName", "type" to "channel", "key" to "123")

            val apiUrl = QueryStringBuilder.newInstance().buildQueryString("https://www.googleapis.com/youtube/v3/search", queries)

            Log.i("testCalls101",apiUrl)

            val requestBuilder = cronetEngine.newUrlRequestBuilder(
                apiUrl,
                MyUrlRequestCallback(),
                executor
            )

            requestBuilder.addHeader("Content-Type","application/json")


            val request: UrlRequest = requestBuilder.build()
            //request.start()
        }

        return view
    }

    companion object {
        fun newInstance() = Fragment3()
    }
}