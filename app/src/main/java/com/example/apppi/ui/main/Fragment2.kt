package com.example.apppi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.apppi.MainActivity
import com.example.apppi.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.chromium.net.CronetEngine
import org.chromium.net.UrlRequest
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Fragment2 : Fragment() {


    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("test123", "Start")

        val myBuilder = CronetEngine.Builder(context)
        val cronetEngine: CronetEngine = myBuilder.build()

        val executor: Executor = Executors.newSingleThreadExecutor()

        //val apiUrl = "https://api.example.com/data?api_key=YOUR_API_KEY&param1=value1&param2=value2"
        val apiUrl = "https://emojihub.yurace.pro/api/random"



        val requestBuilder = cronetEngine.newUrlRequestBuilder(
            apiUrl,
            MyUrlRequestCallback("RandEmoji",this),
            executor
        )

        requestBuilder.addHeader("Content-Type","application/json")

        Log.i("MyUrlRequestCallback","FIRST CALL")

        val request: UrlRequest = requestBuilder.build()

        request.start()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_2, container, false)

        toolbar = view.findViewById(R.id.toolbar2)
        toolbar.title = "Fragment Title"

        return view
    }

    companion object {
        fun newInstance() = Fragment2()
    }
}