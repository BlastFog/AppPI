package com.example.apppi.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apppi.CronetRequestBuilder
import com.example.apppi.MainActivity
import com.example.apppi.QueryStringBuilder
import com.example.apppi.R
import com.example.apppi.data.DbManager
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val TAG = "MyUrlRequestCallback"
private const val BASEURL = "https://www.googleapis.com/youtube/v3/"

class Fragment3 : Fragment() {
// Youtube API Interaction Fragment (Auth Token)
// Order: 1.  GET https://www.googleapis.com/youtube/v3/search params: part=snippet, q=@..., type=channel, key=...
// 2. GET https://www.googleapis.com/youtube/v3/channels params: part=statistics, id=oldQuery[items[statistics]].subcriberCount, key=...

    private lateinit var ytFetchBut: Button
    private lateinit var ytLinkBut: ImageButton

    lateinit var subField : TextView
    lateinit var viewField : TextView
    lateinit var videoField : TextView

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

        ytLinkBut = view.findViewById(R.id.ytLinkBut)
        ytLinkBut.setOnClickListener{
            val visitYT = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
            startActivity(visitYT)
        }

        subField = view.findViewById(R.id.ytSubField)
        viewField = view.findViewById(R.id.ytViewCountField)
        videoField = view.findViewById(R.id.ytVideoCountField)

        ytFetchBut = view.findViewById(R.id.ytFetch)


        val myBuilder = CronetEngine.Builder(context)
        val cronetEngine: CronetEngine = myBuilder.build()
        val input : EditText = view.findViewById(R.id.ytChannelNameTextField)

        ytFetchBut.setOnClickListener{
            val channelName : String = input.text.toString()
            val key = DbManager.getInstance(requireContext()).getApiKey("yt")

            if(key.isNotEmpty()) {
                val queries: Map<String, String> = mapOf(
                    "part" to "snippet",
                    "q" to "@$channelName",
                    "type" to "channel",
                    "key" to "$key"
                )
                CronetRequestBuilder.newInstance()
                    .buildRequest(cronetEngine, BASEURL + "search", queries, "YT_id", this)
            }else{
                (activity as MainActivity).ytKeyEmptyToast()
            }
        }

        var myViewModel = ViewModelProvider(this).get(YTViewModel::class.java)

        myViewModel.channelID.observe(context as LifecycleOwner, Observer { channelID ->
            foundID(channelID, cronetEngine)
        })

        return view
    }

    private fun foundID(channelID : String, cronetEngine : CronetEngine) {
        val queries : Map<String, String> = mapOf("part" to "statistics", "id" to "$channelID", "key" to "${DbManager.getInstance(requireContext()).getApiKey("yt")}")
        CronetRequestBuilder.newInstance().buildRequest(cronetEngine,BASEURL +"channels",queries, "YT_stats",this)

        var myViewModel = ViewModelProvider(this).get(YTViewModel::class.java)

        myViewModel.attributes.observe(context as LifecycleOwner, Observer { att ->
            //Log.v("aaaaaaaaaaaaaaaaaaaa", "$att")
            subField.text = formatNumberWithCommas(att[0])
            viewField.text = formatNumberWithCommas(att[1])
            videoField.text = formatNumberWithCommas(att[2])
        })


    }

    private fun formatNumberWithCommas(number: String): String {
        val num = number.toLong()
        return  "%,d".format(num)
    }

    companion object {
        fun newInstance() = Fragment3()
    }

}