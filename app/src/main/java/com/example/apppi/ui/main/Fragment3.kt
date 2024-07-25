package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apppi.CronetRequestBuilder
import com.example.apppi.QueryStringBuilder
import com.example.apppi.R
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import org.json.JSONObject
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val TAG = "MyUrlRequestCallback"
private const val BASEURL = "https://www.googleapis.com/youtube/v3/"
private const val KEY = "123"

class Fragment3 : Fragment() {
// Youtube API Interaction Fragment (Auth Token)
// Order: 1.  GET https://www.googleapis.com/youtube/v3/search params: part=snippet, q=@..., type=channel, key=...
// 2. GET https://www.googleapis.com/youtube/v3/channels params: part=statistics, id=oldQuery[items[statistics]].subcriberCount, key=...

    private lateinit var ytFetchBut: Button
    lateinit var jsonResponse : JSONObject
    lateinit var subField : TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        toolbar = view.findViewById(R.id.toolbar3)
        toolbar.title = "Youtube API"


        ytFetchBut = view.findViewById(R.id.ytFetch)
        ytFetchBut.setOnClickListener{
            val myBuilder = CronetEngine.Builder(context)
            val cronetEngine: CronetEngine = myBuilder.build()
            val input : EditText = view.findViewById(R.id.ytChannelNameTextField)
            val channelName : String = input.text.toString()
            val queries : Map<String, String> = mapOf("part" to "snippet", "q" to "@$channelName", "type" to "channel", "key" to "$KEY")

            CronetRequestBuilder.newInstance().buildRequest(cronetEngine,BASEURL +"search",queries,"YT_id",this)

            var myViewModel = ViewModelProvider(this).get(YTViewModel::class.java)
            subField = view.findViewById(R.id.textViewYTData)
            myViewModel.channelID.observe(context as LifecycleOwner, Observer {
                channelID -> foundID(channelID, cronetEngine, subField)
            })
        }

        return view
    }

    private fun foundID(channelID : String, cronetEngine : CronetEngine, subField : TextView) {
        Log.i(TAG,"ID: $channelID")
        val queries : Map<String, String> = mapOf("part" to "statistics", "id" to "$channelID", "key" to "$KEY")

        CronetRequestBuilder.newInstance().buildRequest(cronetEngine,BASEURL +"channels",queries, "YT_stats",this)

        var myViewModel = ViewModelProvider(this).get(YTViewModel::class.java)
        myViewModel.subs.observe(context as LifecycleOwner, Observer { subs ->
            Log.i("MyUrlRequestCallback","Subs: $subs")
            subField.text = subs.toString()
        })
    }

    companion object {
        fun newInstance() = Fragment3()
    }

}